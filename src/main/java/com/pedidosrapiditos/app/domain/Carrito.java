package com.pedidosrapiditos.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pedidosrapiditos.app.domain.enumeration.MetodoDePago;
import com.pedidosrapiditos.app.domain.enumeration.OrdenStatus;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Carrito.
 */
@Entity
@Table(name = "carrito")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Carrito implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private Instant fecha;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrdenStatus status;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "precio_total", precision = 21, scale = 2, nullable = false)
    private BigDecimal precioTotal;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_de_pago", nullable = false)
    private MetodoDePago metodoDePago;

    @Column(name = "referencia")
    private String referencia;

    @OneToMany(mappedBy = "carrito")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "producto", "carrito" }, allowSetters = true)
    private Set<ItemCarrito> items = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "categoria" }, allowSetters = true)
    private Restaurante restaurante;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "carritos" }, allowSetters = true)
    private Usuario usuario;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Carrito id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getFecha() {
        return this.fecha;
    }

    public Carrito fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public OrdenStatus getStatus() {
        return this.status;
    }

    public Carrito status(OrdenStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(OrdenStatus status) {
        this.status = status;
    }

    public BigDecimal getPrecioTotal() {
        return this.precioTotal;
    }

    public Carrito precioTotal(BigDecimal precioTotal) {
        this.precioTotal = precioTotal;
        return this;
    }

    public void setPrecioTotal(BigDecimal precioTotal) {
        this.precioTotal = precioTotal;
    }

    public MetodoDePago getMetodoDePago() {
        return this.metodoDePago;
    }

    public Carrito metodoDePago(MetodoDePago metodoDePago) {
        this.metodoDePago = metodoDePago;
        return this;
    }

    public void setMetodoDePago(MetodoDePago metodoDePago) {
        this.metodoDePago = metodoDePago;
    }

    public String getReferencia() {
        return this.referencia;
    }

    public Carrito referencia(String referencia) {
        this.referencia = referencia;
        return this;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Set<ItemCarrito> getItems() {
        return this.items;
    }

    public Carrito items(Set<ItemCarrito> itemCarritos) {
        this.setItems(itemCarritos);
        return this;
    }

    public Carrito addItem(ItemCarrito itemCarrito) {
        this.items.add(itemCarrito);
        itemCarrito.setCarrito(this);
        return this;
    }

    public Carrito removeItem(ItemCarrito itemCarrito) {
        this.items.remove(itemCarrito);
        itemCarrito.setCarrito(null);
        return this;
    }

    public void setItems(Set<ItemCarrito> itemCarritos) {
        if (this.items != null) {
            this.items.forEach(i -> i.setCarrito(null));
        }
        if (itemCarritos != null) {
            itemCarritos.forEach(i -> i.setCarrito(this));
        }
        this.items = itemCarritos;
    }

    public Restaurante getRestaurante() {
        return this.restaurante;
    }

    public Carrito restaurante(Restaurante restaurante) {
        this.setRestaurante(restaurante);
        return this;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public Carrito usuario(Usuario usuario) {
        this.setUsuario(usuario);
        return this;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Carrito)) {
            return false;
        }
        return id != null && id.equals(((Carrito) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Carrito{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", status='" + getStatus() + "'" +
            ", precioTotal=" + getPrecioTotal() +
            ", metodoDePago='" + getMetodoDePago() + "'" +
            ", referencia='" + getReferencia() + "'" +
            "}";
    }
}
