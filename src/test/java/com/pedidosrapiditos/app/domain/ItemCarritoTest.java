package com.pedidosrapiditos.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.pedidosrapiditos.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ItemCarritoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemCarrito.class);
        ItemCarrito itemCarrito1 = new ItemCarrito();
        itemCarrito1.setId(1L);
        ItemCarrito itemCarrito2 = new ItemCarrito();
        itemCarrito2.setId(itemCarrito1.getId());
        assertThat(itemCarrito1).isEqualTo(itemCarrito2);
        itemCarrito2.setId(2L);
        assertThat(itemCarrito1).isNotEqualTo(itemCarrito2);
        itemCarrito1.setId(null);
        assertThat(itemCarrito1).isNotEqualTo(itemCarrito2);
    }
}
