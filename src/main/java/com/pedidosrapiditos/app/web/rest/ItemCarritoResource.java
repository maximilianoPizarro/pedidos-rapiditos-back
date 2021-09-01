package com.pedidosrapiditos.app.web.rest;

import com.pedidosrapiditos.app.domain.ItemCarrito;
import com.pedidosrapiditos.app.repository.ItemCarritoRepository;
import com.pedidosrapiditos.app.service.ItemCarritoService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.pedidosrapiditos.app.domain.ItemCarrito}.
 */
@RestController
@RequestMapping("/api")
public class ItemCarritoResource {

    private final Logger log = LoggerFactory.getLogger(ItemCarritoResource.class);

    private static final String ENTITY_NAME = "pedidosrapiditosItemCarrito";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ItemCarritoService itemCarritoService;

    private final ItemCarritoRepository itemCarritoRepository;

    public ItemCarritoResource(ItemCarritoService itemCarritoService, ItemCarritoRepository itemCarritoRepository) {
        this.itemCarritoService = itemCarritoService;
        this.itemCarritoRepository = itemCarritoRepository;
    }

    /**
     * {@code POST  /item-carritos} : Create a new itemCarrito.
     *
     * @param itemCarrito the itemCarrito to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new itemCarrito, or with status {@code 400 (Bad Request)} if the itemCarrito has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/item-carritos")
    public ResponseEntity<ItemCarrito> createItemCarrito(@Valid @RequestBody ItemCarrito itemCarrito) throws URISyntaxException {
        log.debug("REST request to save ItemCarrito : {}", itemCarrito);
        if (itemCarrito.getId() != null) {
            throw new BadRequestAlertException("A new itemCarrito cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemCarrito result = itemCarritoService.save(itemCarrito);
        return ResponseEntity
            .created(new URI("/api/item-carritos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /item-carritos/:id} : Updates an existing itemCarrito.
     *
     * @param id the id of the itemCarrito to save.
     * @param itemCarrito the itemCarrito to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemCarrito,
     * or with status {@code 400 (Bad Request)} if the itemCarrito is not valid,
     * or with status {@code 500 (Internal Server Error)} if the itemCarrito couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/item-carritos/{id}")
    public ResponseEntity<ItemCarrito> updateItemCarrito(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ItemCarrito itemCarrito
    ) throws URISyntaxException {
        log.debug("REST request to update ItemCarrito : {}, {}", id, itemCarrito);
        if (itemCarrito.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, itemCarrito.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!itemCarritoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ItemCarrito result = itemCarritoService.save(itemCarrito);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, itemCarrito.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /item-carritos/:id} : Partial updates given fields of an existing itemCarrito, field will ignore if it is null
     *
     * @param id the id of the itemCarrito to save.
     * @param itemCarrito the itemCarrito to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemCarrito,
     * or with status {@code 400 (Bad Request)} if the itemCarrito is not valid,
     * or with status {@code 404 (Not Found)} if the itemCarrito is not found,
     * or with status {@code 500 (Internal Server Error)} if the itemCarrito couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/item-carritos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ItemCarrito> partialUpdateItemCarrito(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ItemCarrito itemCarrito
    ) throws URISyntaxException {
        log.debug("REST request to partial update ItemCarrito partially : {}, {}", id, itemCarrito);
        if (itemCarrito.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, itemCarrito.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!itemCarritoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ItemCarrito> result = itemCarritoService.partialUpdate(itemCarrito);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, itemCarrito.getId().toString())
        );
    }

    /**
     * {@code GET  /item-carritos} : get all the itemCarritos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of itemCarritos in body.
     */
    @GetMapping("/item-carritos")
    public List<ItemCarrito> getAllItemCarritos() {
        log.debug("REST request to get all ItemCarritos");
        return itemCarritoService.findAll();
    }

    /**
     * {@code GET  /item-carritos/:id} : get the "id" itemCarrito.
     *
     * @param id the id of the itemCarrito to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the itemCarrito, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/item-carritos/{id}")
    public ResponseEntity<ItemCarrito> getItemCarrito(@PathVariable Long id) {
        log.debug("REST request to get ItemCarrito : {}", id);
        Optional<ItemCarrito> itemCarrito = itemCarritoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itemCarrito);
    }

    /**
     * {@code DELETE  /item-carritos/:id} : delete the "id" itemCarrito.
     *
     * @param id the id of the itemCarrito to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/item-carritos/{id}")
    public ResponseEntity<Void> deleteItemCarrito(@PathVariable Long id) {
        log.debug("REST request to delete ItemCarrito : {}", id);
        itemCarritoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
