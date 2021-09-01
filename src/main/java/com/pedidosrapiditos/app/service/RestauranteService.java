package com.pedidosrapiditos.app.service;

import com.pedidosrapiditos.app.domain.Restaurante;
import com.pedidosrapiditos.app.repository.RestauranteRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Restaurante}.
 */
@Service
@Transactional
public class RestauranteService {

    private final Logger log = LoggerFactory.getLogger(RestauranteService.class);

    private final RestauranteRepository restauranteRepository;

    public RestauranteService(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    /**
     * Save a restaurante.
     *
     * @param restaurante the entity to save.
     * @return the persisted entity.
     */
    public Restaurante save(Restaurante restaurante) {
        log.debug("Request to save Restaurante : {}", restaurante);
        return restauranteRepository.save(restaurante);
    }

    /**
     * Partially update a restaurante.
     *
     * @param restaurante the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Restaurante> partialUpdate(Restaurante restaurante) {
        log.debug("Request to partially update Restaurante : {}", restaurante);

        return restauranteRepository
            .findById(restaurante.getId())
            .map(
                existingRestaurante -> {
                    if (restaurante.getNombre() != null) {
                        existingRestaurante.setNombre(restaurante.getNombre());
                    }
                    if (restaurante.getDescripcion() != null) {
                        existingRestaurante.setDescripcion(restaurante.getDescripcion());
                    }
                    if (restaurante.getActivo() != null) {
                        existingRestaurante.setActivo(restaurante.getActivo());
                    }
                    if (restaurante.getCalificacion() != null) {
                        existingRestaurante.setCalificacion(restaurante.getCalificacion());
                    }
                    if (restaurante.getLatitud() != null) {
                        existingRestaurante.setLatitud(restaurante.getLatitud());
                    }
                    if (restaurante.getLongitud() != null) {
                        existingRestaurante.setLongitud(restaurante.getLongitud());
                    }

                    return existingRestaurante;
                }
            )
            .map(restauranteRepository::save);
    }

    /**
     * Get all the restaurantes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Restaurante> findAll(Pageable pageable) {
        log.debug("Request to get all Restaurantes");
        return restauranteRepository.findAll(pageable);
    }

    /**
     * Get one restaurante by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Restaurante> findOne(Long id) {
        log.debug("Request to get Restaurante : {}", id);
        return restauranteRepository.findById(id);
    }

    /**
     * Delete the restaurante by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Restaurante : {}", id);
        restauranteRepository.deleteById(id);
    }
}
