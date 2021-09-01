package com.pedidosrapiditos.app.web.rest;

import static com.pedidosrapiditos.app.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pedidosrapiditos.app.IntegrationTest;
import com.pedidosrapiditos.app.domain.Carrito;
import com.pedidosrapiditos.app.domain.ItemCarrito;
import com.pedidosrapiditos.app.domain.Producto;
import com.pedidosrapiditos.app.repository.ItemCarritoRepository;
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
 * Integration tests for the {@link ItemCarritoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ItemCarritoResourceIT {

    private static final Integer DEFAULT_CANTIDAD = 0;
    private static final Integer UPDATED_CANTIDAD = 1;

    private static final BigDecimal DEFAULT_PRECIO_TOTAL = new BigDecimal(0);
    private static final BigDecimal UPDATED_PRECIO_TOTAL = new BigDecimal(1);

    private static final String ENTITY_API_URL = "/api/item-carritos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ItemCarritoRepository itemCarritoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restItemCarritoMockMvc;

    private ItemCarrito itemCarrito;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemCarrito createEntity(EntityManager em) {
        ItemCarrito itemCarrito = new ItemCarrito().cantidad(DEFAULT_CANTIDAD).precioTotal(DEFAULT_PRECIO_TOTAL);
        // Add required entity
        Producto producto;
        if (TestUtil.findAll(em, Producto.class).isEmpty()) {
            producto = ProductoResourceIT.createEntity(em);
            em.persist(producto);
            em.flush();
        } else {
            producto = TestUtil.findAll(em, Producto.class).get(0);
        }
        itemCarrito.setProducto(producto);
        // Add required entity
        Carrito carrito;
        if (TestUtil.findAll(em, Carrito.class).isEmpty()) {
            carrito = CarritoResourceIT.createEntity(em);
            em.persist(carrito);
            em.flush();
        } else {
            carrito = TestUtil.findAll(em, Carrito.class).get(0);
        }
        itemCarrito.setCarrito(carrito);
        return itemCarrito;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemCarrito createUpdatedEntity(EntityManager em) {
        ItemCarrito itemCarrito = new ItemCarrito().cantidad(UPDATED_CANTIDAD).precioTotal(UPDATED_PRECIO_TOTAL);
        // Add required entity
        Producto producto;
        if (TestUtil.findAll(em, Producto.class).isEmpty()) {
            producto = ProductoResourceIT.createUpdatedEntity(em);
            em.persist(producto);
            em.flush();
        } else {
            producto = TestUtil.findAll(em, Producto.class).get(0);
        }
        itemCarrito.setProducto(producto);
        // Add required entity
        Carrito carrito;
        if (TestUtil.findAll(em, Carrito.class).isEmpty()) {
            carrito = CarritoResourceIT.createUpdatedEntity(em);
            em.persist(carrito);
            em.flush();
        } else {
            carrito = TestUtil.findAll(em, Carrito.class).get(0);
        }
        itemCarrito.setCarrito(carrito);
        return itemCarrito;
    }

    @BeforeEach
    public void initTest() {
        itemCarrito = createEntity(em);
    }

    @Test
    @Transactional
    void createItemCarrito() throws Exception {
        int databaseSizeBeforeCreate = itemCarritoRepository.findAll().size();
        // Create the ItemCarrito
        restItemCarritoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemCarrito)))
            .andExpect(status().isCreated());

        // Validate the ItemCarrito in the database
        List<ItemCarrito> itemCarritoList = itemCarritoRepository.findAll();
        assertThat(itemCarritoList).hasSize(databaseSizeBeforeCreate + 1);
        ItemCarrito testItemCarrito = itemCarritoList.get(itemCarritoList.size() - 1);
        assertThat(testItemCarrito.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testItemCarrito.getPrecioTotal()).isEqualByComparingTo(DEFAULT_PRECIO_TOTAL);
    }

    @Test
    @Transactional
    void createItemCarritoWithExistingId() throws Exception {
        // Create the ItemCarrito with an existing ID
        itemCarrito.setId(1L);

        int databaseSizeBeforeCreate = itemCarritoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemCarritoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemCarrito)))
            .andExpect(status().isBadRequest());

        // Validate the ItemCarrito in the database
        List<ItemCarrito> itemCarritoList = itemCarritoRepository.findAll();
        assertThat(itemCarritoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCantidadIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemCarritoRepository.findAll().size();
        // set the field null
        itemCarrito.setCantidad(null);

        // Create the ItemCarrito, which fails.

        restItemCarritoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemCarrito)))
            .andExpect(status().isBadRequest());

        List<ItemCarrito> itemCarritoList = itemCarritoRepository.findAll();
        assertThat(itemCarritoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrecioTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemCarritoRepository.findAll().size();
        // set the field null
        itemCarrito.setPrecioTotal(null);

        // Create the ItemCarrito, which fails.

        restItemCarritoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemCarrito)))
            .andExpect(status().isBadRequest());

        List<ItemCarrito> itemCarritoList = itemCarritoRepository.findAll();
        assertThat(itemCarritoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllItemCarritos() throws Exception {
        // Initialize the database
        itemCarritoRepository.saveAndFlush(itemCarrito);

        // Get all the itemCarritoList
        restItemCarritoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemCarrito.getId().intValue())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)))
            .andExpect(jsonPath("$.[*].precioTotal").value(hasItem(sameNumber(DEFAULT_PRECIO_TOTAL))));
    }

    @Test
    @Transactional
    void getItemCarrito() throws Exception {
        // Initialize the database
        itemCarritoRepository.saveAndFlush(itemCarrito);

        // Get the itemCarrito
        restItemCarritoMockMvc
            .perform(get(ENTITY_API_URL_ID, itemCarrito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(itemCarrito.getId().intValue()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD))
            .andExpect(jsonPath("$.precioTotal").value(sameNumber(DEFAULT_PRECIO_TOTAL)));
    }

    @Test
    @Transactional
    void getNonExistingItemCarrito() throws Exception {
        // Get the itemCarrito
        restItemCarritoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewItemCarrito() throws Exception {
        // Initialize the database
        itemCarritoRepository.saveAndFlush(itemCarrito);

        int databaseSizeBeforeUpdate = itemCarritoRepository.findAll().size();

        // Update the itemCarrito
        ItemCarrito updatedItemCarrito = itemCarritoRepository.findById(itemCarrito.getId()).get();
        // Disconnect from session so that the updates on updatedItemCarrito are not directly saved in db
        em.detach(updatedItemCarrito);
        updatedItemCarrito.cantidad(UPDATED_CANTIDAD).precioTotal(UPDATED_PRECIO_TOTAL);

        restItemCarritoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedItemCarrito.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedItemCarrito))
            )
            .andExpect(status().isOk());

        // Validate the ItemCarrito in the database
        List<ItemCarrito> itemCarritoList = itemCarritoRepository.findAll();
        assertThat(itemCarritoList).hasSize(databaseSizeBeforeUpdate);
        ItemCarrito testItemCarrito = itemCarritoList.get(itemCarritoList.size() - 1);
        assertThat(testItemCarrito.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testItemCarrito.getPrecioTotal()).isEqualTo(UPDATED_PRECIO_TOTAL);
    }

    @Test
    @Transactional
    void putNonExistingItemCarrito() throws Exception {
        int databaseSizeBeforeUpdate = itemCarritoRepository.findAll().size();
        itemCarrito.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemCarritoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, itemCarrito.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(itemCarrito))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemCarrito in the database
        List<ItemCarrito> itemCarritoList = itemCarritoRepository.findAll();
        assertThat(itemCarritoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchItemCarrito() throws Exception {
        int databaseSizeBeforeUpdate = itemCarritoRepository.findAll().size();
        itemCarrito.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemCarritoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(itemCarrito))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemCarrito in the database
        List<ItemCarrito> itemCarritoList = itemCarritoRepository.findAll();
        assertThat(itemCarritoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamItemCarrito() throws Exception {
        int databaseSizeBeforeUpdate = itemCarritoRepository.findAll().size();
        itemCarrito.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemCarritoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemCarrito)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ItemCarrito in the database
        List<ItemCarrito> itemCarritoList = itemCarritoRepository.findAll();
        assertThat(itemCarritoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateItemCarritoWithPatch() throws Exception {
        // Initialize the database
        itemCarritoRepository.saveAndFlush(itemCarrito);

        int databaseSizeBeforeUpdate = itemCarritoRepository.findAll().size();

        // Update the itemCarrito using partial update
        ItemCarrito partialUpdatedItemCarrito = new ItemCarrito();
        partialUpdatedItemCarrito.setId(itemCarrito.getId());

        partialUpdatedItemCarrito.cantidad(UPDATED_CANTIDAD);

        restItemCarritoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedItemCarrito.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedItemCarrito))
            )
            .andExpect(status().isOk());

        // Validate the ItemCarrito in the database
        List<ItemCarrito> itemCarritoList = itemCarritoRepository.findAll();
        assertThat(itemCarritoList).hasSize(databaseSizeBeforeUpdate);
        ItemCarrito testItemCarrito = itemCarritoList.get(itemCarritoList.size() - 1);
        assertThat(testItemCarrito.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testItemCarrito.getPrecioTotal()).isEqualByComparingTo(DEFAULT_PRECIO_TOTAL);
    }

    @Test
    @Transactional
    void fullUpdateItemCarritoWithPatch() throws Exception {
        // Initialize the database
        itemCarritoRepository.saveAndFlush(itemCarrito);

        int databaseSizeBeforeUpdate = itemCarritoRepository.findAll().size();

        // Update the itemCarrito using partial update
        ItemCarrito partialUpdatedItemCarrito = new ItemCarrito();
        partialUpdatedItemCarrito.setId(itemCarrito.getId());

        partialUpdatedItemCarrito.cantidad(UPDATED_CANTIDAD).precioTotal(UPDATED_PRECIO_TOTAL);

        restItemCarritoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedItemCarrito.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedItemCarrito))
            )
            .andExpect(status().isOk());

        // Validate the ItemCarrito in the database
        List<ItemCarrito> itemCarritoList = itemCarritoRepository.findAll();
        assertThat(itemCarritoList).hasSize(databaseSizeBeforeUpdate);
        ItemCarrito testItemCarrito = itemCarritoList.get(itemCarritoList.size() - 1);
        assertThat(testItemCarrito.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testItemCarrito.getPrecioTotal()).isEqualByComparingTo(UPDATED_PRECIO_TOTAL);
    }

    @Test
    @Transactional
    void patchNonExistingItemCarrito() throws Exception {
        int databaseSizeBeforeUpdate = itemCarritoRepository.findAll().size();
        itemCarrito.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemCarritoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, itemCarrito.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(itemCarrito))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemCarrito in the database
        List<ItemCarrito> itemCarritoList = itemCarritoRepository.findAll();
        assertThat(itemCarritoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchItemCarrito() throws Exception {
        int databaseSizeBeforeUpdate = itemCarritoRepository.findAll().size();
        itemCarrito.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemCarritoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(itemCarrito))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemCarrito in the database
        List<ItemCarrito> itemCarritoList = itemCarritoRepository.findAll();
        assertThat(itemCarritoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamItemCarrito() throws Exception {
        int databaseSizeBeforeUpdate = itemCarritoRepository.findAll().size();
        itemCarrito.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemCarritoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(itemCarrito))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ItemCarrito in the database
        List<ItemCarrito> itemCarritoList = itemCarritoRepository.findAll();
        assertThat(itemCarritoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteItemCarrito() throws Exception {
        // Initialize the database
        itemCarritoRepository.saveAndFlush(itemCarrito);

        int databaseSizeBeforeDelete = itemCarritoRepository.findAll().size();

        // Delete the itemCarrito
        restItemCarritoMockMvc
            .perform(delete(ENTITY_API_URL_ID, itemCarrito.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ItemCarrito> itemCarritoList = itemCarritoRepository.findAll();
        assertThat(itemCarritoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
