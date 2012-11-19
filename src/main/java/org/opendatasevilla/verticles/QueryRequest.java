package org.opendatasevilla.verticles;


public class QueryRequest {

    private int offset;

    private int limit;

    private String query;

    private String[] properties;

    private Double centerLat;

    private Double centerLng;

    private Double radiusKm;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String[] getProperties() {
        return properties;
    }

    public void setProperties(String[] properties) {
        this.properties = properties;
    }

    public Double getCenterLat() {
        return centerLat;
    }

    public void setCenterLat(Double centerLat) {
        this.centerLat = centerLat;
    }

    public Double getCenterLng() {
        return centerLng;
    }

    public void setCenterLng(Double centerLng) {
        this.centerLng = centerLng;
    }

    public Double getRadiusKm() {
        return radiusKm;
    }

    public void setRadiusKm(Double radiusKm) {
        this.radiusKm = radiusKm;
    }

    public void validate() {
        if (query != null && centerLat != null) {
            throw new IllegalArgumentException("Use either (query) or (centerLat, centerLng and radiusKm) params but not combined");
        }
    }
}
