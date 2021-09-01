package com.pedidosrapiditos.app.repository;

import com.pedidosrapiditos.app.domain.ItemCarrito;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ItemCarrito entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemCarritoRepository extends JpaRepository<ItemCarrito, Long> {}
