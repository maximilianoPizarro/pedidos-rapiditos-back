<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="carrito">
        <h2 class="jh-entity-heading" data-cy="carritoDetailsHeading">
          <span v-text="$t('pedidosrapiditosApp.carrito.detail.title')">Carrito</span> {{ carrito.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('pedidosrapiditosApp.carrito.fecha')">Fecha</span>
          </dt>
          <dd>
            <span v-if="carrito.fecha">{{ $d(Date.parse(carrito.fecha), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('pedidosrapiditosApp.carrito.status')">Status</span>
          </dt>
          <dd>
            <span v-text="$t('pedidosrapiditosApp.OrdenStatus.' + carrito.status)">{{ carrito.status }}</span>
          </dd>
          <dt>
            <span v-text="$t('pedidosrapiditosApp.carrito.precioTotal')">Precio Total</span>
          </dt>
          <dd>
            <span>{{ carrito.precioTotal }}</span>
          </dd>
          <dt>
            <span v-text="$t('pedidosrapiditosApp.carrito.metodoDePago')">Metodo De Pago</span>
          </dt>
          <dd>
            <span v-text="$t('pedidosrapiditosApp.MetodoDePago.' + carrito.metodoDePago)">{{ carrito.metodoDePago }}</span>
          </dd>
          <dt>
            <span v-text="$t('pedidosrapiditosApp.carrito.referencia')">Referencia</span>
          </dt>
          <dd>
            <span>{{ carrito.referencia }}</span>
          </dd>
          <dt>
            <span v-text="$t('pedidosrapiditosApp.carrito.restaurante')">Restaurante</span>
          </dt>
          <dd>
            <div v-if="carrito.restaurante">
              <router-link :to="{ name: 'RestauranteView', params: { restauranteId: carrito.restaurante.id } }">{{
                carrito.restaurante.nombre
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('pedidosrapiditosApp.carrito.usuario')">Usuario</span>
          </dt>
          <dd>
            <div v-if="carrito.usuario">
              <router-link :to="{ name: 'UsuarioView', params: { usuarioId: carrito.usuario.id } }">{{ carrito.usuario.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="carrito.id" :to="{ name: 'CarritoEdit', params: { carritoId: carrito.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./carrito-details.component.ts"></script>
