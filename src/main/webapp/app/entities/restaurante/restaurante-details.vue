<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="restaurante">
        <h2 class="jh-entity-heading" data-cy="restauranteDetailsHeading">
          <span v-text="$t('pedidosrapiditosApp.restaurante.detail.title')">Restaurante</span> {{ restaurante.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('pedidosrapiditosApp.restaurante.nombre')">Nombre</span>
          </dt>
          <dd>
            <span>{{ restaurante.nombre }}</span>
          </dd>
          <dt>
            <span v-text="$t('pedidosrapiditosApp.restaurante.descripcion')">Descripcion</span>
          </dt>
          <dd>
            <span>{{ restaurante.descripcion }}</span>
          </dd>
          <dt>
            <span v-text="$t('pedidosrapiditosApp.restaurante.activo')">Activo</span>
          </dt>
          <dd>
            <span>{{ restaurante.activo }}</span>
          </dd>
          <dt>
            <span v-text="$t('pedidosrapiditosApp.restaurante.calificacion')">Calificacion</span>
          </dt>
          <dd>
            <span>{{ restaurante.calificacion }}</span>
          </dd>
          <dt>
            <span v-text="$t('pedidosrapiditosApp.restaurante.latitud')">Latitud</span>
          </dt>
          <dd>
            <span>{{ restaurante.latitud }}</span>
          </dd>
          <dt>
            <span v-text="$t('pedidosrapiditosApp.restaurante.longitud')">Longitud</span>
          </dt>
          <dd>
            <span>{{ restaurante.longitud }}</span>
          </dd>
          <dt>
            <span v-text="$t('pedidosrapiditosApp.restaurante.categoria')">Categoria</span>
          </dt>
          <dd>
            <div v-if="restaurante.categoria">
              <router-link :to="{ name: 'RestauranteCategoriaView', params: { restauranteCategoriaId: restaurante.categoria.id } }">{{
                restaurante.categoria.nombre
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="restaurante.id"
          :to="{ name: 'RestauranteEdit', params: { restauranteId: restaurante.id } }"
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

<script lang="ts" src="./restaurante-details.component.ts"></script>
