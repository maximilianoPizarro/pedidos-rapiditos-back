<template>
  <div>
    <h2 id="page-heading" data-cy="ItemCarritoHeading">
      <span v-text="$t('pedidosrapiditosApp.itemCarrito.home.title')" id="item-carrito-heading">Item Carritos</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('pedidosrapiditosApp.itemCarrito.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ItemCarritoCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-item-carrito"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('pedidosrapiditosApp.itemCarrito.home.createLabel')"> Create a new Item Carrito </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && itemCarritos && itemCarritos.length === 0">
      <span v-text="$t('pedidosrapiditosApp.itemCarrito.home.notFound')">No itemCarritos found</span>
    </div>
    <div class="table-responsive" v-if="itemCarritos && itemCarritos.length > 0">
      <table class="table table-striped" aria-describedby="itemCarritos">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('pedidosrapiditosApp.itemCarrito.cantidad')">Cantidad</span></th>
            <th scope="row"><span v-text="$t('pedidosrapiditosApp.itemCarrito.precioTotal')">Precio Total</span></th>
            <th scope="row"><span v-text="$t('pedidosrapiditosApp.itemCarrito.producto')">Producto</span></th>
            <th scope="row"><span v-text="$t('pedidosrapiditosApp.itemCarrito.carrito')">Carrito</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="itemCarrito in itemCarritos" :key="itemCarrito.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ItemCarritoView', params: { itemCarritoId: itemCarrito.id } }">{{ itemCarrito.id }}</router-link>
            </td>
            <td>{{ itemCarrito.cantidad }}</td>
            <td>{{ itemCarrito.precioTotal }}</td>
            <td>
              <div v-if="itemCarrito.producto">
                <router-link :to="{ name: 'ProductoView', params: { productoId: itemCarrito.producto.id } }">{{
                  itemCarrito.producto.nombre
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="itemCarrito.carrito">
                <router-link :to="{ name: 'CarritoView', params: { carritoId: itemCarrito.carrito.id } }">{{
                  itemCarrito.carrito.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ItemCarritoView', params: { itemCarritoId: itemCarrito.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ItemCarritoEdit', params: { itemCarritoId: itemCarrito.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(itemCarrito)"
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
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span
          id="pedidosrapiditosApp.itemCarrito.delete.question"
          data-cy="itemCarritoDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-itemCarrito-heading" v-text="$t('pedidosrapiditosApp.itemCarrito.delete.question', { id: removeId })">
          Are you sure you want to delete this Item Carrito?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-itemCarrito"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeItemCarrito()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./item-carrito.component.ts"></script>
