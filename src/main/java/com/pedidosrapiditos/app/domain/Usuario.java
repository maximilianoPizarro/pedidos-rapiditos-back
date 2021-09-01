package com.pedidosrapiditos.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pedidosrapiditos.app.domain.enumeration.RolUsuario;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Usuario.
 */
@Entity
@Table(name = "usuario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "username", nullable = false)
    private String username;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private RolUsuario rol;

    @NotNull
    @Column(name = "telefono", nullable = false)
    private String telefono;

    @NotNull
    @Column(name = "direccion_1", nullable = false)
    private String direccion1;

    @NotNull
    @Column(name = "ciudad", nullable = false)
    private String ciudad;

    @NotNull
    @Column(name = "pais", nullable = false)
    private String pais;

    @DecimalMin(value = "-90")
    @DecimalMax(value = "90")
    @Column(name = "latitud", precision = 21, scale = 2)
    private BigDecimal latitud;

    @DecimalMin(value = "-180")
    @DecimalMax(value = "180")
    @Column(name = "longitud", precision = 21, scale = 2)
    private BigDecimal longitud;

    @OneToMany(mappedBy = "usuario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "items", "restaurante", "usuario" }, allowSetters = true)
    private Set<Carrito> carritos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario id(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return this.username;
    }

    public Usuario username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public Usuario password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RolUsuario getRol() {
        return this.rol;
    }

    public Usuario rol(RolUsuario rol) {
        this.rol = rol;
        return this;
    }

    public void setRol(RolUsuario rol) {
        this.rol = rol;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public Usuario telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion1() {
        return this.direccion1;
    }

    public Usuario direccion1(String direccion1) {
        this.direccion1 = direccion1;
        return this;
    }

    public void setDireccion1(String direccion1) {
        this.direccion1 = direccion1;
    }

    public String getCiudad() {
        return this.ciudad;
    }

    public Usuario ciudad(String ciudad) {
        this.ciudad = ciudad;
        return this;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return this.pais;
    }

    public Usuario pais(String pais) {
        this.pais = pais;
        return this;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public BigDecimal getLatitud() {
        return this.latitud;
    }

    public Usuario latitud(BigDecimal latitud) {
        this.latitud = latitud;
        return this;
    }

    public void setLatitud(BigDecimal latitud) {
        this.latitud = latitud;
    }

    public BigDecimal getLongitud() {
        return this.longitud;
    }

    public Usuario longitud(BigDecimal longitud) {
        this.longitud = longitud;
        return this;
    }

    public void setLongitud(BigDecimal longitud) {
        this.longitud = longitud;
    }

    public Set<Carrito> getCarritos() {
        return this.carritos;
    }

    public Usuario carritos(Set<Carrito> carritos) {
        this.setCarritos(carritos);
        return this;
    }

    public Usuario addCarrito(Carrito carrito) {
        this.carritos.add(carrito);
        carrito.setUsuario(this);
        return this;
    }

    public Usuario removeCarrito(Carrito carrito) {
        this.carritos.remove(carrito);
        carrito.setUsuario(null);
        return this;
    }

    public void setCarritos(Set<Carrito> carritos) {
        if (this.carritos != null) {
            this.carritos.forEach(i -> i.setUsuario(null));
        }
        if (carritos != null) {
            carritos.forEach(i -> i.setUsuario(this));
        }
        this.carritos = carritos;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Usuario)) {
            return false;
        }
        return id != null && id.equals(((Usuario) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Usuario{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", rol='" + getRol() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", direccion1='" + getDireccion1() + "'" +
            ", ciudad='" + getCiudad() + "'" +
            ", pais='" + getPais() + "'" +
            ", latitud=" + getLatitud() +
            ", longitud=" + getLongitud() +
            "}";
    }
}
