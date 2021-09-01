<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="pedidosrapiditosApp.carrito.home.createOrEditLabel"
          data-cy="CarritoCreateUpdateHeading"
          v-text="$t('pedidosrapiditosApp.carrito.home.createOrEditLabel')"
        >
          Create or edit a Carrito
        </h2>
        <div>
          <div class="form-group" v-if="carrito.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="carrito.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pedidosrapiditosApp.carrito.fecha')" for="carrito-fecha">Fecha</label>
            <div class="d-flex">
              <input
                id="carrito-fecha"
                data-cy="fecha"
                type="datetime-local"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.carrito.fecha.$invalid, invalid: $v.carrito.fecha.$invalid }"
                required
                :value="convertDateTimeFromServer($v.carrito.fecha.$model)"
                @change="updateInstantField('fecha', $event)"
              />
            </div>
            <div v-if="$v.carrito.fecha.$anyDirty && $v.carrito.fecha.$invalid">
              <small class="form-text text-danger" v-if="!$v.carrito.fecha.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.carrito.fecha.ZonedDateTimelocal"
                v-text="$t('entity.validation.ZonedDateTimelocal')"
              >
                This field should be a date and time.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pedidosrapiditosApp.carrito.status')" for="carrito-status">Status</label>
            <select
              class="form-control"
              name="status"
              :class="{ valid: !$v.carrito.status.$invalid, invalid: $v.carrito.status.$invalid }"
              v-model="$v.carrito.status.$model"
              id="carrito-status"
              data-cy="status"
              required
            >
              <option value="COMPLETO" v-bind:label="$t('pedidosrapiditosApp.OrdenStatus.COMPLETO')">COMPLETO</option>
              <option value="PAGADO" v-bind:label="$t('pedidosrapiditosApp.OrdenStatus.PAGADO')">PAGADO</option>
              <option value="PENDIENTE" v-bind:label="$t('pedidosrapiditosApp.OrdenStatus.PENDIENTE')">PENDIENTE</option>
              <option value="CANCELADO" v-bind:label="$t('pedidosrapiditosApp.OrdenStatus.CANCELADO')">CANCELADO</option>
              <option value="RECHAZADO" v-bind:label="$t('pedidosrapiditosApp.OrdenStatus.RECHAZADO')">RECHAZADO</option>
            </select>
            <div v-if="$v.carrito.status.$anyDirty && $v.carrito.status.$invalid">
              <small class="form-text text-danger" v-if="!$v.carrito.status.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pedidosrapiditosApp.carrito.precioTotal')" for="carrito-precioTotal"
              >Precio Total</label
            >
            <input
              type="number"
              class="form-control"
              name="precioTotal"
              id="carrito-precioTotal"
              data-cy="precioTotal"
              :class="{ valid: !$v.carrito.precioTotal.$invalid, invalid: $v.carrito.precioTotal.$invalid }"
              v-model.number="$v.carrito.precioTotal.$model"
              required
            />
            <div v-if="$v.carrito.precioTotal.$anyDirty && $v.carrito.precioTotal.$invalid">
              <small class="form-text text-danger" v-if="!$v.carrito.precioTotal.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.carrito.precioTotal.min" v-text="$t('entity.validation.min', { min: 0 })">
                This field should be at least 0.
              </small>
              <small class="form-text text-danger" v-if="!$v.carrito.precioTotal.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pedidosrapiditosApp.carrito.metodoDePago')" for="carrito-metodoDePago"
              >Metodo De Pago</label
            >
            <select
              class="form-control"
              name="metodoDePago"
              :class="{ valid: !$v.carrito.metodoDePago.$invalid, invalid: $v.carrito.metodoDePago.$invalid }"
              v-model="$v.carrito.metodoDePago.$model"
              id="carrito-metodoDePago"
              data-cy="metodoDePago"
              required
            >
              <option value="EFECTIVO" v-bind:label="$t('pedidosrapiditosApp.MetodoDePago.EFECTIVO')">efectivo</option>
              <option value="CUPON" v-bind:label="$t('pedidosrapiditosApp.MetodoDePago.CUPON')">cupon</option>
              <option value="TARJETA" v-bind:label="$t('pedidosrapiditosApp.MetodoDePago.TARJETA')">tarjeta</option>
            </select>
            <div v-if="$v.carrito.metodoDePago.$anyDirty && $v.carrito.metodoDePago.$invalid">
              <small class="form-text text-danger" v-if="!$v.carrito.metodoDePago.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pedidosrapiditosApp.carrito.referencia')" for="carrito-referencia"
              >Referencia</label
            >
            <input
              type="text"
              class="form-control"
              name="referencia"
              id="carrito-referencia"
              data-cy="referencia"
              :class="{ valid: !$v.carrito.referencia.$invalid, invalid: $v.carrito.referencia.$invalid }"
              v-model="$v.carrito.referencia.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pedidosrapiditosApp.carrito.restaurante')" for="carrito-restaurante"
              >Restaurante</label
            >
            <select
              class="form-control"
              id="carrito-restaurante"
              data-cy="restaurante"
              name="restaurante"
              v-model="carrito.restaurante"
              required
            >
              <option v-if="!carrito.restaurante" v-bind:value="null" selected></option>
              <option
                v-bind:value="
                  carrito.restaurante && restauranteOption.id === carrito.restaurante.id ? carrito.restaurante : restauranteOption
                "
                v-for="restauranteOption in restaurantes"
                :key="restauranteOption.id"
              >
                {{ restauranteOption.nombre }}
              </option>
            </select>
          </div>
          <div v-if="$v.carrito.restaurante.$anyDirty && $v.carrito.restaurante.$invalid">
            <small class="form-text text-danger" v-if="!$v.carrito.restaurante.required" v-text="$t('entity.validation.required')">
              This field is required.
            </small>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pedidosrapiditosApp.carrito.usuario')" for="carrito-usuario">Usuario</label>
            <select class="form-control" id="carrito-usuario" data-cy="usuario" name="usuario" v-model="carrito.usuario" required>
              <option v-if="!carrito.usuario" v-bind:value="null" selected></option>
              <option
                v-bind:value="carrito.usuario && usuarioOption.id === carrito.usuario.id ? carrito.usuario : usuarioOption"
                v-for="usuarioOption in usuarios"
                :key="usuarioOption.id"
              >
                {{ usuarioOption.id }}
              </option>
            </select>
          </div>
          <div v-if="$v.carrito.usuario.$anyDirty && $v.carrito.usuario.$invalid">
            <small class="form-text text-danger" v-if="!$v.carrito.usuario.required" v-text="$t('entity.validation.required')">
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
            :disabled="$v.carrito.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./carrito-update.component.ts"></script>
