package com.pedidosrapiditos.app.service;

import com.pedidosrapiditos.app.domain.RestauranteCategoria;
import com.pedidosrapiditos.app.repository.RestauranteCategoriaRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RestauranteCategoria}.
 */
@Service
@Transactional
public class RestauranteCategoriaService {

    private final Logger log = LoggerFactory.getLogger(RestauranteCategoriaService.class);

    private final RestauranteCategoriaRepository restauranteCategoriaRepository;

    public RestauranteCategoriaService(RestauranteCategoriaRepository restauranteCategoriaRepository) {
        this.restauranteCategoriaRepository = restauranteCategoriaRepository;
    }

    /**
     * Save a restauranteCategoria.
     *
     * @param restauranteCategoria the entity to save.
     * @return the persisted entity.
     */
    public RestauranteCategoria save(RestauranteCategoria restauranteCategoria) {
        log.debug("Request to save RestauranteCategoria : {}", restauranteCategoria);
        return restauranteCategoriaRepository.save(restauranteCategoria);
    }

    /**
     * Partially update a restauranteCategoria.
     *
     * @param restauranteCategoria the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RestauranteCategoria> partialUpdate(RestauranteCategoria restauranteCategoria) {
        log.debug("Request to partially update RestauranteCategoria : {}", restauranteCategoria);

        return restauranteCategoriaRepository
            .findById(restauranteCategoria.getId())
            .map(
                existingRestauranteCategoria -> {
                    if (restauranteCategoria.getNombre() != null) {
                        existingRestauranteCategoria.setNombre(restauranteCategoria.getNombre());
                    }
                    if (restauranteCategoria.getDescription() != null) {
                        existingRestauranteCategoria.setDescription(restauranteCategoria.getDescription());
                    }

                    return existingRestauranteCategoria;
                }
            )
            .map(restauranteCategoriaRepository::save);
    }

    /**
     * Get all the restauranteCategorias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RestauranteCategoria> findAll(Pageable pageable) {
        log.debug("Request to get all RestauranteCategorias");
        return restauranteCategoriaRepository.findAll(pageable);
    }

    /**
     * Get one restauranteCategoria by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RestauranteCategoria> findOne(Long id) {
        log.debug("Request to get RestauranteCategoria : {}", id);
        return restauranteCategoriaRepository.findById(id);
    }

    /**
     * Delete the restauranteCategoria by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RestauranteCategoria : {}", id);
        restauranteCategoriaRepository.deleteById(id);
    }
}
