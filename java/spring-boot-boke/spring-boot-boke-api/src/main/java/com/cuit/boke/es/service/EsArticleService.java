
package com.cuit.boke.es.service;

import com.cuit.boke.article.beans.entry.Article;
import com.yinjk.web.core.vo.PageVO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 文章管理 ES 业务接口类
 *
 */
public interface EsArticleService {

    /**
     * 新增 ES 文章信息
     *
     * @param article
     * @return
     */
    Integer saveArticle(Article article);

    /**
     * 搜索词搜索，分页返回文章信息
     *
     * @param pageNumber 当前页码
     * @param pageSize 每页大小
     * @param searchContent 搜索内容
     * @return
     */
    PageVO<Article> searchArticle(Integer pageNumber, Integer pageSize, String searchContent, boolean all);
}