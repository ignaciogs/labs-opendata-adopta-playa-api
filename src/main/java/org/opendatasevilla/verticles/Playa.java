package org.opendatasevilla.verticles;

import org.hibernate.search.annotations.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Indexed
@Spatial
public class Playa {

    @Id
    private String id;

    private String comunidad;

    private String provincia;

    private String municipio;

    private String nombre;

    private Integer puntoMuestreo;

    private String adoptadaPor;

    @Longitude
    private Double longitude;

    @Latitude
    private Double latitude;

    private Date fechaToma;

    private Integer escherichiaColi;

    private Integer enterococo;

    private String observaciones;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComunidad() {
        return comunidad;
    }

    public void setComunidad(String comunidad) {
        this.comunidad = comunidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getPuntoMuestreo() {
        return puntoMuestreo;
    }

    public void setPuntoMuestreo(Integer puntoMuestreo) {
        this.puntoMuestreo = puntoMuestreo;
    }

    public String getAdoptadaPor() {
        return adoptadaPor;
    }

    public void setAdoptadaPor(String adoptadaPor) {
        this.adoptadaPor = adoptadaPor;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Date getFechaToma() {
        return fechaToma;
    }

    public void setFechaToma(Date fechaToma) {
        this.fechaToma = fechaToma;
    }

    public Integer getEscherichiaColi() {
        return escherichiaColi;
    }

    public void setEscherichiaColi(Integer escherichiaColi) {
        this.escherichiaColi = escherichiaColi;
    }

    public Integer getEnterococo() {
        return enterococo;
    }

    public void setEnterococo(Integer enterococo) {
        this.enterococo = enterococo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

}
