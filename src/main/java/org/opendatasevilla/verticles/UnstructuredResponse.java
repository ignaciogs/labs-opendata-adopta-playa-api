package org.opendatasevilla.verticles;

import java.util.TreeMap;


public class UnstructuredResponse<K, V> extends TreeMap<K, V> {

    public UnstructuredResponse(K[] properties, V[] values) {
        for (int i = 0; i < properties.length; i++) {
            put(properties[i], values[i]);
        }
    }

}
