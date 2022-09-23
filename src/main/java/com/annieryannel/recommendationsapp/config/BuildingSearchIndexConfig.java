package com.annieryannel.recommendationsapp.config;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import com.annieryannel.recommendationsapp.models.Review;
import lombok.SneakyThrows;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class BuildingSearchIndexConfig implements ApplicationListener<ApplicationReadyEvent> {

    @PersistenceContext
    private EntityManager entityManager;

    @SneakyThrows
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        initializeHibernateSearch();
    }

    public void initializeHibernateSearch() throws InterruptedException {
        SearchSession searchSession = Search.session(entityManager);
        MassIndexer indexer = searchSession.massIndexer(Review.class);
        indexer.startAndWait();
    }

}
