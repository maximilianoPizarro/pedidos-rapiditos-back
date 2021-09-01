package com.pedidosrapiditos.app.web.rest;

import com.pedidosrapiditos.app.domain.RestauranteCategoria;
import com.pedidosrapiditos.app.repository.RestauranteCategoriaRepository;
import com.pedidosrapiditos.app.service.RestauranteCategoriaService;
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
 * REST controller for managing {@link com.pedidosrapiditos.app.domain.RestauranteCategoria}.
 */
@RestController
@RequestMapping("/api")
public class RestauranteCategoriaResource {

    private final Logger log = LoggerFactory.getLogger(RestauranteCategoriaResource.class);

    private static final String ENTITY_NAME = "restauranteCategoria";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RestauranteCategoriaService restauranteCategoriaService;

    private final RestauranteCategoriaRepository restauranteCategoriaRepository;

    public RestauranteCategoriaResource(
        RestauranteCategoriaService restauranteCategoriaService,
        RestauranteCategoriaRepository restauranteCategoriaRepository
    ) {
        this.restauranteCategoriaService = restauranteCategoriaService;
        this.restauranteCategoriaRepository = restauranteCategoriaRepository;
    }

    /**
     * {@code POST  /restaurante-categorias} : Create a new restauranteCategoria.
     *
     * @param restauranteCategoria the restauranteCategoria to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new restauranteCategoria, or with status {@code 400 (Bad Request)} if the restauranteCategoria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/restaurante-categorias")
    public ResponseEntity<RestauranteCategoria> createRestauranteCategoria(@Valid @RequestBody RestauranteCategoria restauranteCategoria)
        throws URISyntaxException {
        log.debug("REST request to save RestauranteCategoria : {}", restauranteCategoria);
        if (restauranteCategoria.getId() != null) {
            throw new BadRequestAlertException("A new restauranteCategoria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RestauranteCategoria result = restauranteCategoriaService.save(restauranteCategoria);
        return ResponseEntity
            .created(new URI("/api/restaurante-categorias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /restaurante-categorias/:id} : Updates an existing restauranteCategoria.
     *
     * @param id the id of the restauranteCategoria to save.
     * @param restauranteCategoria the restauranteCategoria to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated restauranteCategoria,
     * or with status {@code 400 (Bad Request)} if the restauranteCategoria is not valid,
     * or with status {@code 500 (Internal Server Error)} if the restauranteCategoria couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/restaurante-categorias/{id}")
    public ResponseEntity<RestauranteCategoria> updateRestauranteCategoria(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RestauranteCategoria restauranteCategoria
    ) throws URISyntaxException {
        log.debug("REST request to update RestauranteCategoria : {}, {}", id, restauranteCategoria);
        if (restauranteCategoria.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, restauranteCategoria.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!restauranteCategoriaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RestauranteCategoria result = restauranteCategoriaService.save(restauranteCategoria);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, restauranteCategoria.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /restaurante-categorias/:id} : Partial updates given fields of an existing restauranteCategoria, field will ignore if it is null
     *
     * @param id the id of the restauranteCategoria to save.
     * @param restauranteCategoria the restauranteCategoria to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated restauranteCategoria,
     * or with status {@code 400 (Bad Request)} if the restauranteCategoria is not valid,
     * or with status {@code 404 (Not Found)} if the restauranteCategoria is not found,
     * or with status {@code 500 (Internal Server Error)} if the restauranteCategoria couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/restaurante-categorias/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<RestauranteCategoria> partialUpdateRestauranteCategoria(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RestauranteCategoria restauranteCategoria
    ) throws URISyntaxException {
        log.debug("REST request to partial update RestauranteCategoria partially : {}, {}", id, restauranteCategoria);
        if (restauranteCategoria.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, restauranteCategoria.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!restauranteCategoriaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RestauranteCategoria> result = restauranteCategoriaService.partialUpdate(restauranteCategoria);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, restauranteCategoria.getId().toString())
        );
    }

    /**
     * {@code GET  /restaurante-categorias} : get all the restauranteCategorias.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of restauranteCategorias in body.
     */
    @GetMapping("/restaurante-categorias")
    public ResponseEntity<List<RestauranteCategoria>> getAllRestauranteCategorias(Pageable pageable) {
        log.debug("REST request to get a page of RestauranteCategorias");
        Page<RestauranteCategoria> page = restauranteCategoriaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /restaurante-categorias/:id} : get the "id" restauranteCategoria.
     *
     * @param id the id of the restauranteCategoria to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the restauranteCategoria, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/restaurante-categorias/{id}")
    public ResponseEntity<RestauranteCategoria> getRestauranteCategoria(@PathVariable Long id) {
        log.debug("REST request to get RestauranteCategoria : {}", id);
        Optional<RestauranteCategoria> restauranteCategoria = restauranteCategoriaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(restauranteCategoria);
    }

    /**
     * {@code DELETE  /restaurante-categorias/:id} : delete the "id" restauranteCategoria.
     *
     * @param id the id of the restauranteCategoria to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/restaurante-categorias/{id}")
    public ResponseEntity<Void> deleteRestauranteCategoria(@PathVariable Long id) {
        log.debug("REST request to delete RestauranteCategoria : {}", id);
        restauranteCategoriaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
