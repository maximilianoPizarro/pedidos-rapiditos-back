<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="pedidosrapiditosApp.restauranteCategoria.home.createOrEditLabel"
          data-cy="RestauranteCategoriaCreateUpdateHeading"
          v-text="$t('pedidosrapiditosApp.restauranteCategoria.home.createOrEditLabel')"
        >
          Create or edit a RestauranteCategoria
        </h2>
        <div>
          <div class="form-group" v-if="restauranteCategoria.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="restauranteCategoria.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('pedidosrapiditosApp.restauranteCategoria.nombre')"
              for="restaurante-categoria-nombre"
              >Nombre</label
            >
            <input
              type="text"
              class="form-control"
              name="nombre"
              id="restaurante-categoria-nombre"
              data-cy="nombre"
              :class="{ valid: !$v.restauranteCategoria.nombre.$invalid, invalid: $v.restauranteCategoria.nombre.$invalid }"
              v-model="$v.restauranteCategoria.nombre.$model"
              required
            />
            <div v-if="$v.restauranteCategoria.nombre.$anyDirty && $v.restauranteCategoria.nombre.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.restauranteCategoria.nombre.required"
                v-text="$t('entity.validation.required')"
              >
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('pedidosrapiditosApp.restauranteCategoria.description')"
              for="restaurante-categoria-description"
              >Description</label
            >
            <input
              type="text"
              class="form-control"
              name="description"
              id="restaurante-categoria-description"
              data-cy="description"
              :class="{ valid: !$v.restauranteCategoria.description.$invalid, invalid: $v.restauranteCategoria.description.$invalid }"
              v-model="$v.restauranteCategoria.description.$model"
            />
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.restauranteCategoria.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./restaurante-categoria-update.component.ts"></script>
