package com.pedidosrapiditos.app.web.rest;

import com.pedidosrapiditos.app.domain.Restaurante;
import com.pedidosrapiditos.app.repository.RestauranteRepository;
import com.pedidosrapiditos.app.service.RestauranteService;
import com.pedidosrapiditos.app.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.pedidosrapiditos.app.domain.Restaurante}.
 */
@RestController
@RequestMapping("/api")
public class RestauranteResource {

    private final Logger log = LoggerFactory.getLogger(RestauranteResource.class);

    private static final String ENTITY_NAME = "pedidosrapiditosRestaurante";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RestauranteService restauranteService;

    private final RestauranteRepository restauranteRepository;

    public RestauranteResource(RestauranteService restauranteService, RestauranteRepository restauranteRepository) {
        this.restauranteService = restauranteService;
        this.restauranteRepository = restauranteRepository;
    }

    /**
     * {@code POST  /restaurantes} : Create a new restaurante.
     *
     * @param restaurante the restaurante to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new restaurante, or with status {@code 400 (Bad Request)} if the restaurante has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/restaurantes")
    public ResponseEntity<Restaurante> createRestaurante(@Valid @RequestBody Restaurante restaurante) throws URISyntaxException {
        log.debug("REST request to save Restaurante : {}", restaurante);
        if (restaurante.getId() != null) {
            throw new BadRequestAlertException("A new restaurante cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Restaurante result = restauranteService.save(restaurante);
        return ResponseEntity
            .created(new URI("/api/restaurantes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /restaurantes/:id} : Updates an existing restaurante.
     *
     * @param id the id of the restaurante to save.
     * @param restaurante the restaurante to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated restaurante,
     * or with status {@code 400 (Bad Request)} if the restaurante is not valid,
     * or with status {@code 500 (Internal Server Error)} if the restaurante couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/restaurantes/{id}")
    public ResponseEntity<Restaurante> updateRestaurante(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Restaurante restaurante
    ) throws URISyntaxException {
        log.debug("REST request to update Restaurante : {}, {}", id, restaurante);
        if (restaurante.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, restaurante.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!restauranteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Restaurante result = restauranteService.save(restaurante);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, restaurante.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /restaurantes/:id} : Partial updates given fields of an existing restaurante, field will ignore if it is null
     *
     * @param id the id of the restaurante to save.
     * @param restaurante the restaurante to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated restaurante,
     * or with status {@code 400 (Bad Request)} if the restaurante is not valid,
     * or with status {@code 404 (Not Found)} if the restaurante is not found,
     * or with status {@code 500 (Internal Server Error)} if the restaurante couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/restaurantes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Restaurante> partialUpdateRestaurante(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Restaurante restaurante
    ) throws URISyntaxException {
        log.debug("REST request to partial update Restaurante partially : {}, {}", id, restaurante);
        if (restaurante.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, restaurante.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!restauranteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Restaurante> result = restauranteService.partialUpdate(restaurante);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, restaurante.getId().toString())
        );
    }

    /**
     * {@code GET  /restaurantes} : get all the restaurantes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of restaurantes in body.
     */
    @GetMapping("/restaurantes")
    public ResponseEntity<List<Restaurante>> getAllRestaurantes(Pageable pageable) {
        log.debug("REST request to get a page of Restaurantes");
        Page<Restaurante> page = restauranteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /restaurantes/:id} : get the "id" restaurante.
     *
     * @param id the id of the restaurante to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the restaurante, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/restaurantes/{id}")
    public ResponseEntity<Restaurante> getRestaurante(@PathVariable Long id) {
        log.debug("REST request to get Restaurante : {}", id);
        Optional<Restaurante> restaurante = restauranteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(restaurante);
    }

    /**
     * {@code DELETE  /restaurantes/:id} : delete the "id" restaurante.
     *
     * @param id the id of the restaurante to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/restaurantes/{id}")
    public ResponseEntity<Void> deleteRestaurante(@PathVariable Long id) {
        log.debug("REST request to delete Restaurante : {}", id);
        restauranteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
