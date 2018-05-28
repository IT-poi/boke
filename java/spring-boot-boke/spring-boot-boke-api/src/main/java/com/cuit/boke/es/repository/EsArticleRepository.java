package com.cuit.boke.es.repository;

import com.cuit.boke.article.beans.entry.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsArticleRepository extends ElasticsearchRepository<Article, Integer> {

}