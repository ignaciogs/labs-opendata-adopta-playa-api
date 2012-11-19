package org.opendatasevilla.verticles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/api")
public class ApiService {

    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger logger = LoggerFactory.getLogger(ApiService.class);

    @RequestMapping(value = "/playas/{queryName}/{offset}/{limit}", method = RequestMethod.GET)
    @ResponseBody
    public <T> ListResponse<T> query(@PathVariable("queryName") String queryName,
                                     @PathVariable("offset") int offset,
                                     @PathVariable("limit") int limit) {
        return doQuery(queryName, offset, limit);
    }

    @RequestMapping(value = "/playas/{queryName}/{properties}/{offset}/{limit}", method = RequestMethod.GET)
    @ResponseBody
    public ListResponse<UnstructuredResponse<String, Object>> queryUnstructured(@PathVariable("queryName") String queryName,
                                                 @PathVariable("properties") String properties,
                                     @PathVariable("offset") int offset,
                                     @PathVariable("limit") int limit) {
        return doQueryUnstructured(queryName, properties.split(","), offset, limit);
    }

    @SuppressWarnings("unchecked")
    private <T> ListResponse<T> doQuery(String queryName, int offset, int limit) {
        List<T> results = entityManager.createNamedQuery(queryName)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
        return new ListResponse<T>(results);
    }

    @SuppressWarnings("unchecked")
    private ListResponse<UnstructuredResponse<String, Object>> doQueryUnstructured(String queryName, String[] properties, int offset, int limit) {
        List<Object[]> results = entityManager.createNamedQuery(queryName)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
        List<UnstructuredResponse<String, Object>> unstructuredResults = new ArrayList<UnstructuredResponse<String, Object>>(results.size());
        for (Object[] record : results) {
            unstructuredResults.add(new UnstructuredResponse<String, Object>(properties, record));
        }
        return new ListResponse<UnstructuredResponse<String, Object>>(unstructuredResults);
    }

}