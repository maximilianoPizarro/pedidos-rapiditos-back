package com.pedidosrapiditos.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.pedidosrapiditos.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RestauranteCategoriaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RestauranteCategoria.class);
        RestauranteCategoria restauranteCategoria1 = new RestauranteCategoria();
        restauranteCategoria1.setId(1L);
        RestauranteCategoria restauranteCategoria2 = new RestauranteCategoria();
        restauranteCategoria2.setId(restauranteCategoria1.getId());
        assertThat(restauranteCategoria1).isEqualTo(restauranteCategoria2);
        restauranteCategoria2.setId(2L);
        assertThat(restauranteCategoria1).isNotEqualTo(restauranteCategoria2);
        restauranteCategoria1.setId(null);
        assertThat(restauranteCategoria1).isNotEqualTo(restauranteCategoria2);
    }
}
