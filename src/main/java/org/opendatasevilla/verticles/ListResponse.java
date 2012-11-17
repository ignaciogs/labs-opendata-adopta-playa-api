package org.opendatasevilla.verticles;

import java.util.List;

/**
 *
 */
public class ListResponse<T> {

    private List<T> results;

    public ListResponse(List<T> results) {
        this.results = results;
    }

    public List<T> getResults() {
        return results;
    }
}
