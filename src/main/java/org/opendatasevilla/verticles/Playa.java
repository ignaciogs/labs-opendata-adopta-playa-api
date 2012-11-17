package org.opendatasevilla.verticles;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.util.Date;

@Entity
@NamedQueries({
        @NamedQuery(name = "list", query = "select p from Playa p"),
        @NamedQuery(name = "comunidad_count", query = "select p.comunidad, count(p) from Playa p group by p.comunidad order by count(p) desc"),
        @NamedQuery(name = "adoptada_count", query = "select p.adoptadaPor, count(p) from Playa p group by p.adoptadaPor order by count(p) desc"),
        @NamedQuery(name = "ecoli", query = "select p.escherichiaColi, p.nombre from Playa p order by escherichiaColi desc")
})
public class Playa {

    @Id
    private String id;

    private String comunidad;

    private String provincia;

    private String municipio;

    private String nombre;

    private Integer puntoMuestreo;

    private String adoptadaPor;

    private Double utmX;

    private Double utmY;

    private Integer utmHuso;

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

    public Double getUtmX() {
        return utmX;
    }

    public void setUtmX(Double utmX) {
        this.utmX = utmX;
    }

    public Double getUtmY() {
        return utmY;
    }

    public void setUtmY(Double utmY) {
        this.utmY = utmY;
    }

    public Integer getUtmHuso() {
        return utmHuso;
    }

    public void setUtmHuso(Integer utmHuso) {
        this.utmHuso = utmHuso;
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
