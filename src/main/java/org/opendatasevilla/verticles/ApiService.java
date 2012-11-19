package org.opendatasevilla.verticles;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.dsl.Unit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/api")
@SuppressWarnings("unchecked")
public class ApiService {

    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger logger = LoggerFactory.getLogger(ApiService.class);

    /**
     * WARNING!!!
     * for testing only, subject to sql injection, once your queries are ready you should un-map this method and create new methods
     * that you'd like to expose with more appropriate functionality
     */
    @RequestMapping(value = "/playas/query", method = RequestMethod.POST)
    @ResponseBody
    public ListResponse<?> queryStatementUnstructured(@RequestBody QueryRequest queryRequest) {
        queryRequest.validate();
        Query query = null;
        if (queryRequest.getQuery() == null && queryRequest.getCenterLat() != null && queryRequest.getCenterLng() != null && queryRequest.getRadiusKm() != null) {

            FullTextEntityManager fullTextSession = Search.getFullTextEntityManager(entityManager);
            final QueryBuilder builder = fullTextSession.getSearchFactory()
                    .buildQueryBuilder().forEntity(Playa.class).get();

            org.apache.lucene.search.Query luceneQuery = builder.spatial()
                    .onCoordinates(Playa.class.getName())
                    .within(queryRequest.getRadiusKm(), Unit.KM)
                    .ofLatitude(queryRequest.getCenterLat())
                    .andLongitude(queryRequest.getCenterLng())
                    .createQuery();
            query = fullTextSession.createFullTextQuery(luceneQuery, Playa.class);
        } else {
            query = entityManager.createQuery(queryRequest.getQuery());
        }
        return toResponse(query, queryRequest);
    }


    private <T> ListResponse<T> toResponse(Query query, QueryRequest queryRequest) {
        ListResponse<T> response;

        List<T> results = query
                .setFirstResult(queryRequest.getOffset())
                .setMaxResults(queryRequest.getLimit())
                .getResultList();
        if (results.size() > 0 && !Playa.class.isAssignableFrom(results.get(0).getClass())) {
            response = toUnstructuredResponse(results, queryRequest.getProperties());
        } else {
            response = new ListResponse<T>(results);
        }
        return response;
    }


    private <T> ListResponse<T> toUnstructuredResponse(List<T> results, String[] properties) {
        List<UnstructuredResponse<String, Object>> unstructuredResults = new ArrayList<UnstructuredResponse<String, Object>>(results.size());
        for (T record : results) {
            Object[] row;
            if (!record.getClass().isArray()) {
                row = new Object[]{record};
            } else {
                row = (Object[]) record;
            }
            unstructuredResults.add(new UnstructuredResponse<String, Object>(properties, row));
        }
        return (ListResponse<T>) new ListResponse<UnstructuredResponse<String, Object>>(unstructuredResults);
    }

}