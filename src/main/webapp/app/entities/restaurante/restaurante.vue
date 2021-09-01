<template>
  <div>
    <h2 id="page-heading" data-cy="RestauranteHeading">
      <span v-text="$t('pedidosrapiditosApp.restaurante.home.title')" id="restaurante-heading">Restaurantes</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('pedidosrapiditosApp.restaurante.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'RestauranteCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-restaurante"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('pedidosrapiditosApp.restaurante.home.createLabel')"> Create a new Restaurante </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && restaurantes && restaurantes.length === 0">
      <span v-text="$t('pedidosrapiditosApp.restaurante.home.notFound')">No restaurantes found</span>
    </div>
    <div class="table-responsive" v-if="restaurantes && restaurantes.length > 0">
      <table class="table table-striped" aria-describedby="restaurantes">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('nombre')">
              <span v-text="$t('pedidosrapiditosApp.restaurante.nombre')">Nombre</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'nombre'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('descripcion')">
              <span v-text="$t('pedidosrapiditosApp.restaurante.descripcion')">Descripcion</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'descripcion'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('activo')">
              <span v-text="$t('pedidosrapiditosApp.restaurante.activo')">Activo</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'activo'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('calificacion')">
              <span v-text="$t('pedidosrapiditosApp.restaurante.calificacion')">Calificacion</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'calificacion'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('latitud')">
              <span v-text="$t('pedidosrapiditosApp.restaurante.latitud')">Latitud</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'latitud'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('longitud')">
              <span v-text="$t('pedidosrapiditosApp.restaurante.longitud')">Longitud</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'longitud'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('categoria.nombre')">
              <span v-text="$t('pedidosrapiditosApp.restaurante.categoria')">Categoria</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'categoria.nombre'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="restaurante in restaurantes" :key="restaurante.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'RestauranteView', params: { restauranteId: restaurante.id } }">{{ restaurante.id }}</router-link>
            </td>
            <td>{{ restaurante.nombre }}</td>
            <td>{{ restaurante.descripcion }}</td>
            <td>{{ restaurante.activo }}</td>
            <td>{{ restaurante.calificacion }}</td>
            <td>{{ restaurante.latitud }}</td>
            <td>{{ restaurante.longitud }}</td>
            <td>
              <div v-if="restaurante.categoria">
                <router-link :to="{ name: 'RestauranteCategoriaView', params: { restauranteCategoriaId: restaurante.categoria.id } }">{{
                  restaurante.categoria.nombre
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'RestauranteView', params: { restauranteId: restaurante.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'RestauranteEdit', params: { restauranteId: restaurante.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(restaurante)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
        <infinite-loading
          ref="infiniteLoading"
          v-if="totalItems > itemsPerPage"
          :identifier="infiniteId"
          slot="append"
          @infinite="loadMore"
          force-use-infinite-wrapper=".el-table__body-wrapper"
          :distance="20"
        >
        </infinite-loading>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span
          id="pedidosrapiditosApp.restaurante.delete.question"
          data-cy="restauranteDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-restaurante-heading" v-text="$t('pedidosrapiditosApp.restaurante.delete.question', { id: removeId })">
          Are you sure you want to delete this Restaurante?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-restaurante"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeRestaurante()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./restaurante.component.ts"></script>
