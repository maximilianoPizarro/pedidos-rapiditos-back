package com.pedidosrapiditos.app.repository;

import com.pedidosrapiditos.app.domain.RestauranteCategoria;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RestauranteCategoria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RestauranteCategoriaRepository extends JpaRepository<RestauranteCategoria, Long> {}
