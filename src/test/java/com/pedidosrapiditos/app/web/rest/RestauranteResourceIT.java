package com.pedidosrapiditos.app.web.rest;

import static com.pedidosrapiditos.app.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pedidosrapiditos.app.IntegrationTest;
import com.pedidosrapiditos.app.domain.Restaurante;
import com.pedidosrapiditos.app.domain.RestauranteCategoria;
import com.pedidosrapiditos.app.repository.RestauranteRepository;
import java.math.BigDecimal;
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
 * Integration tests for the {@link RestauranteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RestauranteResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    private static final Float DEFAULT_CALIFICACION = 0F;
    private static final Float UPDATED_CALIFICACION = 1F;

    private static final BigDecimal DEFAULT_LATITUD = new BigDecimal(-90);
    private static final BigDecimal UPDATED_LATITUD = new BigDecimal(-89);

    private static final BigDecimal DEFAULT_LONGITUD = new BigDecimal(-180);
    private static final BigDecimal UPDATED_LONGITUD = new BigDecimal(-179);

    private static final String ENTITY_API_URL = "/api/restaurantes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRestauranteMockMvc;

    private Restaurante restaurante;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Restaurante createEntity(EntityManager em) {
        Restaurante restaurante = new Restaurante()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .activo(DEFAULT_ACTIVO)
            .calificacion(DEFAULT_CALIFICACION)
            .latitud(DEFAULT_LATITUD)
            .longitud(DEFAULT_LONGITUD);
        // Add required entity
        RestauranteCategoria restauranteCategoria;
        if (TestUtil.findAll(em, RestauranteCategoria.class).isEmpty()) {
            restauranteCategoria = RestauranteCategoriaResourceIT.createEntity(em);
            em.persist(restauranteCategoria);
            em.flush();
        } else {
            restauranteCategoria = TestUtil.findAll(em, RestauranteCategoria.class).get(0);
        }
        restaurante.setCategoria(restauranteCategoria);
        return restaurante;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Restaurante createUpdatedEntity(EntityManager em) {
        Restaurante restaurante = new Restaurante()
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .activo(UPDATED_ACTIVO)
            .calificacion(UPDATED_CALIFICACION)
            .latitud(UPDATED_LATITUD)
            .longitud(UPDATED_LONGITUD);
        // Add required entity
        RestauranteCategoria restauranteCategoria;
        if (TestUtil.findAll(em, RestauranteCategoria.class).isEmpty()) {
            restauranteCategoria = RestauranteCategoriaResourceIT.createUpdatedEntity(em);
            em.persist(restauranteCategoria);
            em.flush();
        } else {
            restauranteCategoria = TestUtil.findAll(em, RestauranteCategoria.class).get(0);
        }
        restaurante.setCategoria(restauranteCategoria);
        return restaurante;
    }

    @BeforeEach
    public void initTest() {
        restaurante = createEntity(em);
    }

    @Test
    @Transactional
    void createRestaurante() throws Exception {
        int databaseSizeBeforeCreate = restauranteRepository.findAll().size();
        // Create the Restaurante
        restRestauranteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(restaurante)))
            .andExpect(status().isCreated());

        // Validate the Restaurante in the database
        List<Restaurante> restauranteList = restauranteRepository.findAll();
        assertThat(restauranteList).hasSize(databaseSizeBeforeCreate + 1);
        Restaurante testRestaurante = restauranteList.get(restauranteList.size() - 1);
        assertThat(testRestaurante.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testRestaurante.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testRestaurante.getActivo()).isEqualTo(DEFAULT_ACTIVO);
        assertThat(testRestaurante.getCalificacion()).isEqualTo(DEFAULT_CALIFICACION);
        assertThat(testRestaurante.getLatitud()).isEqualByComparingTo(DEFAULT_LATITUD);
        assertThat(testRestaurante.getLongitud()).isEqualByComparingTo(DEFAULT_LONGITUD);
    }

    @Test
    @Transactional
    void createRestauranteWithExistingId() throws Exception {
        // Create the Restaurante with an existing ID
        restaurante.setId(1L);

        int databaseSizeBeforeCreate = restauranteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRestauranteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(restaurante)))
            .andExpect(status().isBadRequest());

        // Validate the Restaurante in the database
        List<Restaurante> restauranteList = restauranteRepository.findAll();
        assertThat(restauranteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = restauranteRepository.findAll().size();
        // set the field null
        restaurante.setNombre(null);

        // Create the Restaurante, which fails.

        restRestauranteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(restaurante)))
            .andExpect(status().isBadRequest());

        List<Restaurante> restauranteList = restauranteRepository.findAll();
        assertThat(restauranteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRestaurantes() throws Exception {
        // Initialize the database
        restauranteRepository.saveAndFlush(restaurante);

        // Get all the restauranteList
        restRestauranteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(restaurante.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].calificacion").value(hasItem(DEFAULT_CALIFICACION.doubleValue())))
            .andExpect(jsonPath("$.[*].latitud").value(hasItem(sameNumber(DEFAULT_LATITUD))))
            .andExpect(jsonPath("$.[*].longitud").value(hasItem(sameNumber(DEFAULT_LONGITUD))));
    }

    @Test
    @Transactional
    void getRestaurante() throws Exception {
        // Initialize the database
        restauranteRepository.saveAndFlush(restaurante);

        // Get the restaurante
        restRestauranteMockMvc
            .perform(get(ENTITY_API_URL_ID, restaurante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(restaurante.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()))
            .andExpect(jsonPath("$.calificacion").value(DEFAULT_CALIFICACION.doubleValue()))
            .andExpect(jsonPath("$.latitud").value(sameNumber(DEFAULT_LATITUD)))
            .andExpect(jsonPath("$.longitud").value(sameNumber(DEFAULT_LONGITUD)));
    }

    @Test
    @Transactional
    void getNonExistingRestaurante() throws Exception {
        // Get the restaurante
        restRestauranteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRestaurante() throws Exception {
        // Initialize the database
        restauranteRepository.saveAndFlush(restaurante);

        int databaseSizeBeforeUpdate = restauranteRepository.findAll().size();

        // Update the restaurante
        Restaurante updatedRestaurante = restauranteRepository.findById(restaurante.getId()).get();
        // Disconnect from session so that the updates on updatedRestaurante are not directly saved in db
        em.detach(updatedRestaurante);
        updatedRestaurante
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .activo(UPDATED_ACTIVO)
            .calificacion(UPDATED_CALIFICACION)
            .latitud(UPDATED_LATITUD)
            .longitud(UPDATED_LONGITUD);

        restRestauranteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRestaurante.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRestaurante))
            )
            .andExpect(status().isOk());

        // Validate the Restaurante in the database
        List<Restaurante> restauranteList = restauranteRepository.findAll();
        assertThat(restauranteList).hasSize(databaseSizeBeforeUpdate);
        Restaurante testRestaurante = restauranteList.get(restauranteList.size() - 1);
        assertThat(testRestaurante.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testRestaurante.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testRestaurante.getActivo()).isEqualTo(UPDATED_ACTIVO);
        assertThat(testRestaurante.getCalificacion()).isEqualTo(UPDATED_CALIFICACION);
        assertThat(testRestaurante.getLatitud()).isEqualTo(UPDATED_LATITUD);
        assertThat(testRestaurante.getLongitud()).isEqualTo(UPDATED_LONGITUD);
    }

    @Test
    @Transactional
    void putNonExistingRestaurante() throws Exception {
        int databaseSizeBeforeUpdate = restauranteRepository.findAll().size();
        restaurante.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRestauranteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, restaurante.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(restaurante))
            )
            .andExpect(status().isBadRequest());

        // Validate the Restaurante in the database
        List<Restaurante> restauranteList = restauranteRepository.findAll();
        assertThat(restauranteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRestaurante() throws Exception {
        int databaseSizeBeforeUpdate = restauranteRepository.findAll().size();
        restaurante.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRestauranteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(restaurante))
            )
            .andExpect(status().isBadRequest());

        // Validate the Restaurante in the database
        List<Restaurante> restauranteList = restauranteRepository.findAll();
        assertThat(restauranteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRestaurante() throws Exception {
        int databaseSizeBeforeUpdate = restauranteRepository.findAll().size();
        restaurante.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRestauranteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(restaurante)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Restaurante in the database
        List<Restaurante> restauranteList = restauranteRepository.findAll();
        assertThat(restauranteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRestauranteWithPatch() throws Exception {
        // Initialize the database
        restauranteRepository.saveAndFlush(restaurante);

        int databaseSizeBeforeUpdate = restauranteRepository.findAll().size();

        // Update the restaurante using partial update
        Restaurante partialUpdatedRestaurante = new Restaurante();
        partialUpdatedRestaurante.setId(restaurante.getId());

        partialUpdatedRestaurante.nombre(UPDATED_NOMBRE).activo(UPDATED_ACTIVO).calificacion(UPDATED_CALIFICACION).latitud(UPDATED_LATITUD);

        restRestauranteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRestaurante.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRestaurante))
            )
            .andExpect(status().isOk());

        // Validate the Restaurante in the database
        List<Restaurante> restauranteList = restauranteRepository.findAll();
        assertThat(restauranteList).hasSize(databaseSizeBeforeUpdate);
        Restaurante testRestaurante = restauranteList.get(restauranteList.size() - 1);
        assertThat(testRestaurante.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testRestaurante.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testRestaurante.getActivo()).isEqualTo(UPDATED_ACTIVO);
        assertThat(testRestaurante.getCalificacion()).isEqualTo(UPDATED_CALIFICACION);
        assertThat(testRestaurante.getLatitud()).isEqualByComparingTo(UPDATED_LATITUD);
        assertThat(testRestaurante.getLongitud()).isEqualByComparingTo(DEFAULT_LONGITUD);
    }

    @Test
    @Transactional
    void fullUpdateRestauranteWithPatch() throws Exception {
        // Initialize the database
        restauranteRepository.saveAndFlush(restaurante);

        int databaseSizeBeforeUpdate = restauranteRepository.findAll().size();

        // Update the restaurante using partial update
        Restaurante partialUpdatedRestaurante = new Restaurante();
        partialUpdatedRestaurante.setId(restaurante.getId());

        partialUpdatedRestaurante
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .activo(UPDATED_ACTIVO)
            .calificacion(UPDATED_CALIFICACION)
            .latitud(UPDATED_LATITUD)
            .longitud(UPDATED_LONGITUD);

        restRestauranteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRestaurante.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRestaurante))
            )
            .andExpect(status().isOk());

        // Validate the Restaurante in the database
        List<Restaurante> restauranteList = restauranteRepository.findAll();
        assertThat(restauranteList).hasSize(databaseSizeBeforeUpdate);
        Restaurante testRestaurante = restauranteList.get(restauranteList.size() - 1);
        assertThat(testRestaurante.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testRestaurante.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testRestaurante.getActivo()).isEqualTo(UPDATED_ACTIVO);
        assertThat(testRestaurante.getCalificacion()).isEqualTo(UPDATED_CALIFICACION);
        assertThat(testRestaurante.getLatitud()).isEqualByComparingTo(UPDATED_LATITUD);
        assertThat(testRestaurante.getLongitud()).isEqualByComparingTo(UPDATED_LONGITUD);
    }

    @Test
    @Transactional
    void patchNonExistingRestaurante() throws Exception {
        int databaseSizeBeforeUpdate = restauranteRepository.findAll().size();
        restaurante.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRestauranteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, restaurante.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(restaurante))
            )
            .andExpect(status().isBadRequest());

        // Validate the Restaurante in the database
        List<Restaurante> restauranteList = restauranteRepository.findAll();
        assertThat(restauranteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRestaurante() throws Exception {
        int databaseSizeBeforeUpdate = restauranteRepository.findAll().size();
        restaurante.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRestauranteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(restaurante))
            )
            .andExpect(status().isBadRequest());

        // Validate the Restaurante in the database
        List<Restaurante> restauranteList = restauranteRepository.findAll();
        assertThat(restauranteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRestaurante() throws Exception {
        int databaseSizeBeforeUpdate = restauranteRepository.findAll().size();
        restaurante.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRestauranteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(restaurante))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Restaurante in the database
        List<Restaurante> restauranteList = restauranteRepository.findAll();
        assertThat(restauranteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRestaurante() throws Exception {
        // Initialize the database
        restauranteRepository.saveAndFlush(restaurante);

        int databaseSizeBeforeDelete = restauranteRepository.findAll().size();

        // Delete the restaurante
        restRestauranteMockMvc
            .perform(delete(ENTITY_API_URL_ID, restaurante.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Restaurante> restauranteList = restauranteRepository.findAll();
        assertThat(restauranteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
