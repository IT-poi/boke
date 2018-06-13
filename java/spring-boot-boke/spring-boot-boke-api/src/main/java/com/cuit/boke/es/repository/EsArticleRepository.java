package com.cuit.boke.es.repository;

import com.cuit.boke.article.beans.entry.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsArticleRepository extends ElasticsearchRepository<Article, Integer> {

    /**
     * 根据ID删除ES中的文章
     * @param id 文章id
     * @return 删除的条数
     */
    Long deleteById(Integer id);

}