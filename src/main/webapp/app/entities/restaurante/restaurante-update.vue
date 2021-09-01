<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="pedidosrapiditosApp.restaurante.home.createOrEditLabel"
          data-cy="RestauranteCreateUpdateHeading"
          v-text="$t('pedidosrapiditosApp.restaurante.home.createOrEditLabel')"
        >
          Create or edit a Restaurante
        </h2>
        <div>
          <div class="form-group" v-if="restaurante.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="restaurante.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pedidosrapiditosApp.restaurante.nombre')" for="restaurante-nombre">Nombre</label>
            <input
              type="text"
              class="form-control"
              name="nombre"
              id="restaurante-nombre"
              data-cy="nombre"
              :class="{ valid: !$v.restaurante.nombre.$invalid, invalid: $v.restaurante.nombre.$invalid }"
              v-model="$v.restaurante.nombre.$model"
              required
            />
            <div v-if="$v.restaurante.nombre.$anyDirty && $v.restaurante.nombre.$invalid">
              <small class="form-text text-danger" v-if="!$v.restaurante.nombre.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pedidosrapiditosApp.restaurante.descripcion')" for="restaurante-descripcion"
              >Descripcion</label
            >
            <input
              type="text"
              class="form-control"
              name="descripcion"
              id="restaurante-descripcion"
              data-cy="descripcion"
              :class="{ valid: !$v.restaurante.descripcion.$invalid, invalid: $v.restaurante.descripcion.$invalid }"
              v-model="$v.restaurante.descripcion.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pedidosrapiditosApp.restaurante.activo')" for="restaurante-activo">Activo</label>
            <input
              type="checkbox"
              class="form-check"
              name="activo"
              id="restaurante-activo"
              data-cy="activo"
              :class="{ valid: !$v.restaurante.activo.$invalid, invalid: $v.restaurante.activo.$invalid }"
              v-model="$v.restaurante.activo.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pedidosrapiditosApp.restaurante.calificacion')" for="restaurante-calificacion"
              >Calificacion</label
            >
            <input
              type="number"
              class="form-control"
              name="calificacion"
              id="restaurante-calificacion"
              data-cy="calificacion"
              :class="{ valid: !$v.restaurante.calificacion.$invalid, invalid: $v.restaurante.calificacion.$invalid }"
              v-model.number="$v.restaurante.calificacion.$model"
            />
            <div v-if="$v.restaurante.calificacion.$anyDirty && $v.restaurante.calificacion.$invalid">
              <small class="form-text text-danger" v-if="!$v.restaurante.calificacion.min" v-text="$t('entity.validation.min', { min: 0 })">
                This field should be at least 0.
              </small>
              <small class="form-text text-danger" v-if="!$v.restaurante.calificacion.max" v-text="$t('entity.validation.max', { max: 5 })">
                This field cannot be longer than 5 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.restaurante.calificacion.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pedidosrapiditosApp.restaurante.latitud')" for="restaurante-latitud"
              >Latitud</label
            >
            <input
              type="number"
              class="form-control"
              name="latitud"
              id="restaurante-latitud"
              data-cy="latitud"
              :class="{ valid: !$v.restaurante.latitud.$invalid, invalid: $v.restaurante.latitud.$invalid }"
              v-model.number="$v.restaurante.latitud.$model"
            />
            <div v-if="$v.restaurante.latitud.$anyDirty && $v.restaurante.latitud.$invalid">
              <small class="form-text text-danger" v-if="!$v.restaurante.latitud.min" v-text="$t('entity.validation.min', { min: -90 })">
                This field should be at least -90.
              </small>
              <small class="form-text text-danger" v-if="!$v.restaurante.latitud.max" v-text="$t('entity.validation.max', { max: 90 })">
                This field cannot be longer than 90 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.restaurante.latitud.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pedidosrapiditosApp.restaurante.longitud')" for="restaurante-longitud"
              >Longitud</label
            >
            <input
              type="number"
              class="form-control"
              name="longitud"
              id="restaurante-longitud"
              data-cy="longitud"
              :class="{ valid: !$v.restaurante.longitud.$invalid, invalid: $v.restaurante.longitud.$invalid }"
              v-model.number="$v.restaurante.longitud.$model"
            />
            <div v-if="$v.restaurante.longitud.$anyDirty && $v.restaurante.longitud.$invalid">
              <small class="form-text text-danger" v-if="!$v.restaurante.longitud.min" v-text="$t('entity.validation.min', { min: -180 })">
                This field should be at least -180.
              </small>
              <small class="form-text text-danger" v-if="!$v.restaurante.longitud.max" v-text="$t('entity.validation.max', { max: 180 })">
                This field cannot be longer than 180 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.restaurante.longitud.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pedidosrapiditosApp.restaurante.categoria')" for="restaurante-categoria"
              >Categoria</label
            >
            <select
              class="form-control"
              id="restaurante-categoria"
              data-cy="categoria"
              name="categoria"
              v-model="restaurante.categoria"
              required
            >
              <option v-if="!restaurante.categoria" v-bind:value="null" selected></option>
              <option
                v-bind:value="
                  restaurante.categoria && restauranteCategoriaOption.id === restaurante.categoria.id
                    ? restaurante.categoria
                    : restauranteCategoriaOption
                "
                v-for="restauranteCategoriaOption in restauranteCategorias"
                :key="restauranteCategoriaOption.id"
              >
                {{ restauranteCategoriaOption.nombre }}
              </option>
            </select>
          </div>
          <div v-if="$v.restaurante.categoria.$anyDirty && $v.restaurante.categoria.$invalid">
            <small class="form-text text-danger" v-if="!$v.restaurante.categoria.required" v-text="$t('entity.validation.required')">
              This field is required.
            </small>
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
            :disabled="$v.restaurante.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./restaurante-update.component.ts"></script>
