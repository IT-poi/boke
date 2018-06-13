package com.cuit.boke.es.service.impl;

import com.cuit.boke.article.beans.entry.Article;
import com.cuit.boke.constant.SysConstants;
import com.cuit.boke.es.repository.EsArticleRepository;
import com.cuit.boke.es.service.EsArticleService;
import com.yinjk.web.core.vo.PageVO;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

/**
 * 城市 ES 业务逻辑实现类
 * <p>
 * Created by bysocket on 20/06/2017.
 */
@Service
public class EsArticleServiceImpl implements EsArticleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EsArticleServiceImpl.class);

    /* 分页参数 */
    Integer PAGE_SIZE = 12;          // 每页数量
    Integer DEFAULT_PAGE_NUMBER = 0; // 默认当前页码


    @Autowired
    private EsArticleRepository articleRepository; // ES 操作类

    public Integer saveArticle(Article article) {
        Article articleResult = articleRepository.save(article);
        return articleResult.getId();
    }

    @Override
    public PageVO<Article> searchArticle(Integer pageNumber, Integer pageSize, String searchContent, boolean all) {

        // 校验分页参数
        if (pageSize == null || pageSize <= 0) {
            pageSize = PAGE_SIZE;
        }

        if (pageNumber == null || pageNumber < DEFAULT_PAGE_NUMBER) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        // 构建搜索查询
        SearchQuery searchQuery = getArticleSearchQuery(pageNumber,pageSize,searchContent, all);

        Page<Article> cityPage = articleRepository.search(searchQuery);
        PageVO<Article> pageVO = new PageVO<>();
        pageVO.setPageNum(pageNumber + 1);
        pageVO.setPageSize(pageSize);
        pageVO.setData(cityPage.getContent());
        pageVO.setTotal(cityPage.getTotalElements());
        return pageVO;
    }

    /**
     * 根据搜索词构造搜索查询语句
     *
     * 代码流程：
     *      - 权重分查询
     *      - 短语匹配
     *      - 设置权重分最小值
     *      - 设置分页参数
     *
     * @param pageNumber 当前页码
     * @param pageSize 每页大小
     * @param searchContent 搜索内容
     * @return
     */
    private SearchQuery getArticleSearchQuery(Integer pageNumber, Integer pageSize, String searchContent, boolean all) {
        // 短语匹配到的搜索词，求和模式累加权重分
        // 权重分查询 https://www.elastic.co/guide/cn/elasticsearch/guide/current/function-score-query.html
        //   - 短语匹配 https://www.elastic.co/guide/cn/elasticsearch/guide/current/phrase-matching.html
        //   - 字段对应权重分设置，可以优化成 enum
        //   - 由于无相关性的分值默认为 1 ，设置权重分最小值为 10
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery()
                .add(QueryBuilders.matchPhraseQuery("title", searchContent),
                    ScoreFunctionBuilders.weightFactorFunction(1000))
                .add(QueryBuilders.matchPhraseQuery("labelNames", searchContent),
                    ScoreFunctionBuilders.weightFactorFunction(500))
                .add(QueryBuilders.matchPhraseQuery("categoryName", searchContent),
                    ScoreFunctionBuilders.weightFactorFunction(500))
                .add(QueryBuilders.matchPhraseQuery("brief", searchContent),
                    ScoreFunctionBuilders.weightFactorFunction(100))
                .add(QueryBuilders.matchPhraseQuery("stringContent", searchContent),
                    ScoreFunctionBuilders.weightFactorFunction(20))
                .scoreMode(SysConstants.ES_SCORE_MODE_SUM).setMinScore(SysConstants.ES_MIN_SCORE);
//        if (!all) { //不是查询所有 只查询已发布的电站
//            functionScoreQueryBuilder.add(QueryBuilders.termQuery("status", 1),
//                    ScoreFunctionBuilders.weightFactorFunction(100000));
//        }

        // 分页参数
        Pageable pageable = new PageRequest(pageNumber, pageSize);
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(functionScoreQueryBuilder);
        if (!all) {
            nativeSearchQueryBuilder.withFilter(QueryBuilders.termQuery("status", 1));
        }
        return nativeSearchQueryBuilder.build();
    }


    @Override
    public void deleteById(Integer articleId) {
        articleRepository.delete(articleId);
    }
}
