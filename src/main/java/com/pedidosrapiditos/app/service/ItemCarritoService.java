package com.pedidosrapiditos.app.service;

import com.pedidosrapiditos.app.domain.ItemCarrito;
import com.pedidosrapiditos.app.repository.ItemCarritoRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ItemCarrito}.
 */
@Service
@Transactional
public class ItemCarritoService {

    private final Logger log = LoggerFactory.getLogger(ItemCarritoService.class);

    private final ItemCarritoRepository itemCarritoRepository;

    public ItemCarritoService(ItemCarritoRepository itemCarritoRepository) {
        this.itemCarritoRepository = itemCarritoRepository;
    }

    /**
     * Save a itemCarrito.
     *
     * @param itemCarrito the entity to save.
     * @return the persisted entity.
     */
    public ItemCarrito save(ItemCarrito itemCarrito) {
        log.debug("Request to save ItemCarrito : {}", itemCarrito);
        return itemCarritoRepository.save(itemCarrito);
    }

    /**
     * Partially update a itemCarrito.
     *
     * @param itemCarrito the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ItemCarrito> partialUpdate(ItemCarrito itemCarrito) {
        log.debug("Request to partially update ItemCarrito : {}", itemCarrito);

        return itemCarritoRepository
            .findById(itemCarrito.getId())
            .map(
                existingItemCarrito -> {
                    if (itemCarrito.getCantidad() != null) {
                        existingItemCarrito.setCantidad(itemCarrito.getCantidad());
                    }
                    if (itemCarrito.getPrecioTotal() != null) {
                        existingItemCarrito.setPrecioTotal(itemCarrito.getPrecioTotal());
                    }

                    return existingItemCarrito;
                }
            )
            .map(itemCarritoRepository::save);
    }

    /**
     * Get all the itemCarritos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ItemCarrito> findAll() {
        log.debug("Request to get all ItemCarritos");
        return itemCarritoRepository.findAll();
    }

    /**
     * Get one itemCarrito by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ItemCarrito> findOne(Long id) {
        log.debug("Request to get ItemCarrito : {}", id);
        return itemCarritoRepository.findById(id);
    }

    /**
     * Delete the itemCarrito by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ItemCarrito : {}", id);
        itemCarritoRepository.deleteById(id);
    }
}
