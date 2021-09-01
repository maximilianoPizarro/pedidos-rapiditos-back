<template>
  <div>
    <h2 id="page-heading" data-cy="UsuarioHeading">
      <span v-text="$t('pedidosrapiditosApp.usuario.home.title')" id="usuario-heading">Usuarios</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('pedidosrapiditosApp.usuario.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'UsuarioCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-usuario"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('pedidosrapiditosApp.usuario.home.createLabel')"> Create a new Usuario </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && usuarios && usuarios.length === 0">
      <span v-text="$t('pedidosrapiditosApp.usuario.home.notFound')">No usuarios found</span>
    </div>
    <div class="table-responsive" v-if="usuarios && usuarios.length > 0">
      <table class="table table-striped" aria-describedby="usuarios">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('username')">
              <span v-text="$t('pedidosrapiditosApp.usuario.username')">Username</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'username'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('password')">
              <span v-text="$t('pedidosrapiditosApp.usuario.password')">Password</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'password'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('rol')">
              <span v-text="$t('pedidosrapiditosApp.usuario.rol')">Rol</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'rol'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('telefono')">
              <span v-text="$t('pedidosrapiditosApp.usuario.telefono')">Telefono</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'telefono'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('direccion1')">
              <span v-text="$t('pedidosrapiditosApp.usuario.direccion1')">Direccion 1</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'direccion1'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('ciudad')">
              <span v-text="$t('pedidosrapiditosApp.usuario.ciudad')">Ciudad</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'ciudad'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('pais')">
              <span v-text="$t('pedidosrapiditosApp.usuario.pais')">Pais</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'pais'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('latitud')">
              <span v-text="$t('pedidosrapiditosApp.usuario.latitud')">Latitud</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'latitud'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('longitud')">
              <span v-text="$t('pedidosrapiditosApp.usuario.longitud')">Longitud</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'longitud'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="usuario in usuarios" :key="usuario.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'UsuarioView', params: { usuarioId: usuario.id } }">{{ usuario.id }}</router-link>
            </td>
            <td>{{ usuario.username }}</td>
            <td>{{ usuario.password }}</td>
            <td v-text="$t('pedidosrapiditosApp.RolUsuario.' + usuario.rol)">{{ usuario.rol }}</td>
            <td>{{ usuario.telefono }}</td>
            <td>{{ usuario.direccion1 }}</td>
            <td>{{ usuario.ciudad }}</td>
            <td>{{ usuario.pais }}</td>
            <td>{{ usuario.latitud }}</td>
            <td>{{ usuario.longitud }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'UsuarioView', params: { usuarioId: usuario.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'UsuarioEdit', params: { usuarioId: usuario.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(usuario)"
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
        ><span id="pedidosrapiditosApp.usuario.delete.question" data-cy="usuarioDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-usuario-heading" v-text="$t('pedidosrapiditosApp.usuario.delete.question', { id: removeId })">
          Are you sure you want to delete this Usuario?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-usuario"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeUsuario()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="usuarios && usuarios.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./usuario.component.ts"></script>
