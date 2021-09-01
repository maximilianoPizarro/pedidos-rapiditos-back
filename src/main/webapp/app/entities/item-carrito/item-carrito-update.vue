<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="pedidosrapiditosApp.itemCarrito.home.createOrEditLabel"
          data-cy="ItemCarritoCreateUpdateHeading"
          v-text="$t('pedidosrapiditosApp.itemCarrito.home.createOrEditLabel')"
        >
          Create or edit a ItemCarrito
        </h2>
        <div>
          <div class="form-group" v-if="itemCarrito.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="itemCarrito.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pedidosrapiditosApp.itemCarrito.cantidad')" for="item-carrito-cantidad"
              >Cantidad</label
            >
            <input
              type="number"
              class="form-control"
              name="cantidad"
              id="item-carrito-cantidad"
              data-cy="cantidad"
              :class="{ valid: !$v.itemCarrito.cantidad.$invalid, invalid: $v.itemCarrito.cantidad.$invalid }"
              v-model.number="$v.itemCarrito.cantidad.$model"
              required
            />
            <div v-if="$v.itemCarrito.cantidad.$anyDirty && $v.itemCarrito.cantidad.$invalid">
              <small class="form-text text-danger" v-if="!$v.itemCarrito.cantidad.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.itemCarrito.cantidad.min" v-text="$t('entity.validation.min', { min: 0 })">
                This field should be at least 0.
              </small>
              <small class="form-text text-danger" v-if="!$v.itemCarrito.cantidad.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pedidosrapiditosApp.itemCarrito.precioTotal')" for="item-carrito-precioTotal"
              >Precio Total</label
            >
            <input
              type="number"
              class="form-control"
              name="precioTotal"
              id="item-carrito-precioTotal"
              data-cy="precioTotal"
              :class="{ valid: !$v.itemCarrito.precioTotal.$invalid, invalid: $v.itemCarrito.precioTotal.$invalid }"
              v-model.number="$v.itemCarrito.precioTotal.$model"
              required
            />
            <div v-if="$v.itemCarrito.precioTotal.$anyDirty && $v.itemCarrito.precioTotal.$invalid">
              <small class="form-text text-danger" v-if="!$v.itemCarrito.precioTotal.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.itemCarrito.precioTotal.min" v-text="$t('entity.validation.min', { min: 0 })">
                This field should be at least 0.
              </small>
              <small class="form-text text-danger" v-if="!$v.itemCarrito.precioTotal.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pedidosrapiditosApp.itemCarrito.producto')" for="item-carrito-producto"
              >Producto</label
            >
            <select
              class="form-control"
              id="item-carrito-producto"
              data-cy="producto"
              name="producto"
              v-model="itemCarrito.producto"
              required
            >
              <option v-if="!itemCarrito.producto" v-bind:value="null" selected></option>
              <option
                v-bind:value="itemCarrito.producto && productoOption.id === itemCarrito.producto.id ? itemCarrito.producto : productoOption"
                v-for="productoOption in productos"
                :key="productoOption.id"
              >
                {{ productoOption.nombre }}
              </option>
            </select>
          </div>
          <div v-if="$v.itemCarrito.producto.$anyDirty && $v.itemCarrito.producto.$invalid">
            <small class="form-text text-danger" v-if="!$v.itemCarrito.producto.required" v-text="$t('entity.validation.required')">
              This field is required.
            </small>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pedidosrapiditosApp.itemCarrito.carrito')" for="item-carrito-carrito"
              >Carrito</label
            >
            <select class="form-control" id="item-carrito-carrito" data-cy="carrito" name="carrito" v-model="itemCarrito.carrito" required>
              <option v-if="!itemCarrito.carrito" v-bind:value="null" selected></option>
              <option
                v-bind:value="itemCarrito.carrito && carritoOption.id === itemCarrito.carrito.id ? itemCarrito.carrito : carritoOption"
                v-for="carritoOption in carritos"
                :key="carritoOption.id"
              >
                {{ carritoOption.id }}
              </option>
            </select>
          </div>
          <div v-if="$v.itemCarrito.carrito.$anyDirty && $v.itemCarrito.carrito.$invalid">
            <small class="form-text text-danger" v-if="!$v.itemCarrito.carrito.required" v-text="$t('entity.validation.required')">
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
            :disabled="$v.itemCarrito.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./item-carrito-update.component.ts"></script>
