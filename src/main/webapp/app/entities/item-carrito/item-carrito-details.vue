<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="itemCarrito">
        <h2 class="jh-entity-heading" data-cy="itemCarritoDetailsHeading">
          <span v-text="$t('pedidosrapiditosApp.itemCarrito.detail.title')">ItemCarrito</span> {{ itemCarrito.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('pedidosrapiditosApp.itemCarrito.cantidad')">Cantidad</span>
          </dt>
          <dd>
            <span>{{ itemCarrito.cantidad }}</span>
          </dd>
          <dt>
            <span v-text="$t('pedidosrapiditosApp.itemCarrito.precioTotal')">Precio Total</span>
          </dt>
          <dd>
            <span>{{ itemCarrito.precioTotal }}</span>
          </dd>
          <dt>
            <span v-text="$t('pedidosrapiditosApp.itemCarrito.producto')">Producto</span>
          </dt>
          <dd>
            <div v-if="itemCarrito.producto">
              <router-link :to="{ name: 'ProductoView', params: { productoId: itemCarrito.producto.id } }">{{
                itemCarrito.producto.nombre
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('pedidosrapiditosApp.itemCarrito.carrito')">Carrito</span>
          </dt>
          <dd>
            <div v-if="itemCarrito.carrito">
              <router-link :to="{ name: 'CarritoView', params: { carritoId: itemCarrito.carrito.id } }">{{
                itemCarrito.carrito.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="itemCarrito.id"
          :to="{ name: 'ItemCarritoEdit', params: { itemCarritoId: itemCarrito.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./item-carrito-details.component.ts"></script>
