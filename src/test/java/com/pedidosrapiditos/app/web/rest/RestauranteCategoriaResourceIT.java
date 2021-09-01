package com.pedidosrapiditos.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pedidosrapiditos.app.IntegrationTest;
import com.pedidosrapiditos.app.domain.RestauranteCategoria;
import com.pedidosrapiditos.app.repository.RestauranteCategoriaRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RestauranteCategoriaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RestauranteCategoriaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/restaurante-categorias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RestauranteCategoriaRepository restauranteCategoriaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRestauranteCategoriaMockMvc;

    private RestauranteCategoria restauranteCategoria;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RestauranteCategoria createEntity(EntityManager em) {
        RestauranteCategoria restauranteCategoria = new RestauranteCategoria().nombre(DEFAULT_NOMBRE).description(DEFAULT_DESCRIPTION);
        return restauranteCategoria;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RestauranteCategoria createUpdatedEntity(EntityManager em) {
        RestauranteCategoria restauranteCategoria = new RestauranteCategoria().nombre(UPDATED_NOMBRE).description(UPDATED_DESCRIPTION);
        return restauranteCategoria;
    }

    @BeforeEach
    public void initTest() {
        restauranteCategoria = createEntity(em);
    }

    @Test
    @Transactional
    void createRestauranteCategoria() throws Exception {
        int databaseSizeBeforeCreate = restauranteCategoriaRepository.findAll().size();
        // Create the RestauranteCategoria
        restRestauranteCategoriaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(restauranteCategoria))
            )
            .andExpect(status().isCreated());

        // Validate the RestauranteCategoria in the database
        List<RestauranteCategoria> restauranteCategoriaList = restauranteCategoriaRepository.findAll();
        assertThat(restauranteCategoriaList).hasSize(databaseSizeBeforeCreate + 1);
        RestauranteCategoria testRestauranteCategoria = restauranteCategoriaList.get(restauranteCategoriaList.size() - 1);
        assertThat(testRestauranteCategoria.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testRestauranteCategoria.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createRestauranteCategoriaWithExistingId() throws Exception {
        // Create the RestauranteCategoria with an existing ID
        restauranteCategoria.setId(1L);

        int databaseSizeBeforeCreate = restauranteCategoriaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRestauranteCategoriaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(restauranteCategoria))
            )
            .andExpect(status().isBadRequest());

        // Validate the RestauranteCategoria in the database
        List<RestauranteCategoria> restauranteCategoriaList = restauranteCategoriaRepository.findAll();
        assertThat(restauranteCategoriaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = restauranteCategoriaRepository.findAll().size();
        // set the field null
        restauranteCategoria.setNombre(null);

        // Create the RestauranteCategoria, which fails.

        restRestauranteCategoriaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(restauranteCategoria))
            )
            .andExpect(status().isBadRequest());

        List<RestauranteCategoria> restauranteCategoriaList = restauranteCategoriaRepository.findAll();
        assertThat(restauranteCategoriaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRestauranteCategorias() throws Exception {
        // Initialize the database
        restauranteCategoriaRepository.saveAndFlush(restauranteCategoria);

        // Get all the restauranteCategoriaList
        restRestauranteCategoriaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(restauranteCategoria.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getRestauranteCategoria() throws Exception {
        // Initialize the database
        restauranteCategoriaRepository.saveAndFlush(restauranteCategoria);

        // Get the restauranteCategoria
        restRestauranteCategoriaMockMvc
            .perform(get(ENTITY_API_URL_ID, restauranteCategoria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(restauranteCategoria.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingRestauranteCategoria() throws Exception {
        // Get the restauranteCategoria
        restRestauranteCategoriaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRestauranteCategoria() throws Exception {
        // Initialize the database
        restauranteCategoriaRepository.saveAndFlush(restauranteCategoria);

        int databaseSizeBeforeUpdate = restauranteCategoriaRepository.findAll().size();

        // Update the restauranteCategoria
        RestauranteCategoria updatedRestauranteCategoria = restauranteCategoriaRepository.findById(restauranteCategoria.getId()).get();
        // Disconnect from session so that the updates on updatedRestauranteCategoria are not directly saved in db
        em.detach(updatedRestauranteCategoria);
        updatedRestauranteCategoria.nombre(UPDATED_NOMBRE).description(UPDATED_DESCRIPTION);

        restRestauranteCategoriaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRestauranteCategoria.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRestauranteCategoria))
            )
            .andExpect(status().isOk());

        // Validate the RestauranteCategoria in the database
        List<RestauranteCategoria> restauranteCategoriaList = restauranteCategoriaRepository.findAll();
        assertThat(restauranteCategoriaList).hasSize(databaseSizeBeforeUpdate);
        RestauranteCategoria testRestauranteCategoria = restauranteCategoriaList.get(restauranteCategoriaList.size() - 1);
        assertThat(testRestauranteCategoria.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testRestauranteCategoria.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingRestauranteCategoria() throws Exception {
        int databaseSizeBeforeUpdate = restauranteCategoriaRepository.findAll().size();
        restauranteCategoria.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRestauranteCategoriaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, restauranteCategoria.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(restauranteCategoria))
            )
            .andExpect(status().isBadRequest());

        // Validate the RestauranteCategoria in the database
        List<RestauranteCategoria> restauranteCategoriaList = restauranteCategoriaRepository.findAll();
        assertThat(restauranteCategoriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRestauranteCategoria() throws Exception {
        int databaseSizeBeforeUpdate = restauranteCategoriaRepository.findAll().size();
        restauranteCategoria.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRestauranteCategoriaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(restauranteCategoria))
            )
            .andExpect(status().isBadRequest());

        // Validate the RestauranteCategoria in the database
        List<RestauranteCategoria> restauranteCategoriaList = restauranteCategoriaRepository.findAll();
        assertThat(restauranteCategoriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRestauranteCategoria() throws Exception {
        int databaseSizeBeforeUpdate = restauranteCategoriaRepository.findAll().size();
        restauranteCategoria.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRestauranteCategoriaMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(restauranteCategoria))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RestauranteCategoria in the database
        List<RestauranteCategoria> restauranteCategoriaList = restauranteCategoriaRepository.findAll();
        assertThat(restauranteCategoriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRestauranteCategoriaWithPatch() throws Exception {
        // Initialize the database
        restauranteCategoriaRepository.saveAndFlush(restauranteCategoria);

        int databaseSizeBeforeUpdate = restauranteCategoriaRepository.findAll().size();

        // Update the restauranteCategoria using partial update
        RestauranteCategoria partialUpdatedRestauranteCategoria = new RestauranteCategoria();
        partialUpdatedRestauranteCategoria.setId(restauranteCategoria.getId());

        partialUpdatedRestauranteCategoria.description(UPDATED_DESCRIPTION);

        restRestauranteCategoriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRestauranteCategoria.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRestauranteCategoria))
            )
            .andExpect(status().isOk());

        // Validate the RestauranteCategoria in the database
        List<RestauranteCategoria> restauranteCategoriaList = restauranteCategoriaRepository.findAll();
        assertThat(restauranteCategoriaList).hasSize(databaseSizeBeforeUpdate);
        RestauranteCategoria testRestauranteCategoria = restauranteCategoriaList.get(restauranteCategoriaList.size() - 1);
        assertThat(testRestauranteCategoria.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testRestauranteCategoria.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateRestauranteCategoriaWithPatch() throws Exception {
        // Initialize the database
        restauranteCategoriaRepository.saveAndFlush(restauranteCategoria);

        int databaseSizeBeforeUpdate = restauranteCategoriaRepository.findAll().size();

        // Update the restauranteCategoria using partial update
        RestauranteCategoria partialUpdatedRestauranteCategoria = new RestauranteCategoria();
        partialUpdatedRestauranteCategoria.setId(restauranteCategoria.getId());

        partialUpdatedRestauranteCategoria.nombre(UPDATED_NOMBRE).description(UPDATED_DESCRIPTION);

        restRestauranteCategoriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRestauranteCategoria.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRestauranteCategoria))
            )
            .andExpect(status().isOk());

        // Validate the RestauranteCategoria in the database
        List<RestauranteCategoria> restauranteCategoriaList = restauranteCategoriaRepository.findAll();
        assertThat(restauranteCategoriaList).hasSize(databaseSizeBeforeUpdate);
        RestauranteCategoria testRestauranteCategoria = restauranteCategoriaList.get(restauranteCategoriaList.size() - 1);
        assertThat(testRestauranteCategoria.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testRestauranteCategoria.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingRestauranteCategoria() throws Exception {
        int databaseSizeBeforeUpdate = restauranteCategoriaRepository.findAll().size();
        restauranteCategoria.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRestauranteCategoriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, restauranteCategoria.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(restauranteCategoria))
            )
            .andExpect(status().isBadRequest());

        // Validate the RestauranteCategoria in the database
        List<RestauranteCategoria> restauranteCategoriaList = restauranteCategoriaRepository.findAll();
        assertThat(restauranteCategoriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRestauranteCategoria() throws Exception {
        int databaseSizeBeforeUpdate = restauranteCategoriaRepository.findAll().size();
        restauranteCategoria.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRestauranteCategoriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(restauranteCategoria))
            )
            .andExpect(status().isBadRequest());

        // Validate the RestauranteCategoria in the database
        List<RestauranteCategoria> restauranteCategoriaList = restauranteCategoriaRepository.findAll();
        assertThat(restauranteCategoriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRestauranteCategoria() throws Exception {
        int databaseSizeBeforeUpdate = restauranteCategoriaRepository.findAll().size();
        restauranteCategoria.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRestauranteCategoriaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(restauranteCategoria))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RestauranteCategoria in the database
        List<RestauranteCategoria> restauranteCategoriaList = restauranteCategoriaRepository.findAll();
        assertThat(restauranteCategoriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRestauranteCategoria() throws Exception {
        // Initialize the database
        restauranteCategoriaRepository.saveAndFlush(restauranteCategoria);

        int databaseSizeBeforeDelete = restauranteCategoriaRepository.findAll().size();

        // Delete the restauranteCategoria
        restRestauranteCategoriaMockMvc
            .perform(delete(ENTITY_API_URL_ID, restauranteCategoria.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RestauranteCategoria> restauranteCategoriaList = restauranteCategoriaRepository.findAll();
        assertThat(restauranteCategoriaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
