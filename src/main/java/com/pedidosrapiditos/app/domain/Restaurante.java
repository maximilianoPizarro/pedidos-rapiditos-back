package com.pedidosrapiditos.app.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Restaurante.
 */
@Entity
@Table(name = "restaurante")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Restaurante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "activo")
    private Boolean activo;

    @DecimalMin(value = "0")
    @DecimalMax(value = "5")
    @Column(name = "calificacion")
    private Float calificacion;

    @DecimalMin(value = "-90")
    @DecimalMax(value = "90")
    @Column(name = "latitud", precision = 21, scale = 2)
    private BigDecimal latitud;

    @DecimalMin(value = "-180")
    @DecimalMax(value = "180")
    @Column(name = "longitud", precision = 21, scale = 2)
    private BigDecimal longitud;

    @ManyToOne(optional = false)
    @NotNull
    private RestauranteCategoria categoria;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Restaurante id(Long id) {
        this.id = id;
        return this;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Restaurante nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Restaurante descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getActivo() {
        return this.activo;
    }

    public Restaurante activo(Boolean activo) {
        this.activo = activo;
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Float getCalificacion() {
        return this.calificacion;
    }

    public Restaurante calificacion(Float calificacion) {
        this.calificacion = calificacion;
        return this;
    }

    public void setCalificacion(Float calificacion) {
        this.calificacion = calificacion;
    }

    public BigDecimal getLatitud() {
        return this.latitud;
    }

    public Restaurante latitud(BigDecimal latitud) {
        this.latitud = latitud;
        return this;
    }

    public void setLatitud(BigDecimal latitud) {
        this.latitud = latitud;
    }

    public BigDecimal getLongitud() {
        return this.longitud;
    }

    public Restaurante longitud(BigDecimal longitud) {
        this.longitud = longitud;
        return this;
    }

    public void setLongitud(BigDecimal longitud) {
        this.longitud = longitud;
    }

    public RestauranteCategoria getCategoria() {
        return this.categoria;
    }

    public Restaurante categoria(RestauranteCategoria restauranteCategoria) {
        this.setCategoria(restauranteCategoria);
        return this;
    }

    public void setCategoria(RestauranteCategoria restauranteCategoria) {
        this.categoria = restauranteCategoria;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Restaurante)) {
            return false;
        }
        return id != null && id.equals(((Restaurante) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Restaurante{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", activo='" + getActivo() + "'" +
            ", calificacion=" + getCalificacion() +
            ", latitud=" + getLatitud() +
            ", longitud=" + getLongitud() +
            "}";
    }
}
