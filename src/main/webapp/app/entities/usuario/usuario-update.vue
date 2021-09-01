<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="pedidosrapiditosApp.usuario.home.createOrEditLabel"
          data-cy="UsuarioCreateUpdateHeading"
          v-text="$t('pedidosrapiditosApp.usuario.home.createOrEditLabel')"
        >
          Create or edit a Usuario
        </h2>
        <div>
          <div class="form-group" v-if="usuario.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="usuario.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pedidosrapiditosApp.usuario.username')" for="usuario-username">Username</label>
            <input
              type="text"
              class="form-control"
              name="username"
              id="usuario-username"
              data-cy="username"
              :class="{ valid: !$v.usuario.username.$invalid, invalid: $v.usuario.username.$invalid }"
              v-model="$v.usuario.username.$model"
              required
            />
            <div v-if="$v.usuario.username.$anyDirty && $v.usuario.username.$invalid">
              <small class="form-text text-danger" v-if="!$v.usuario.username.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pedidosrapiditosApp.usuario.password')" for="usuario-password">Password</label>
            <input
              type="text"
              class="form-control"
              name="password"
              id="usuario-password"
              data-cy="password"
              :class="{ valid: !$v.usuario.password.$invalid, invalid: $v.usuario.password.$invalid }"
              v-model="$v.usuario.password.$model"
              required
            />
            <div v-if="$v.usuario.password.$anyDirty && $v.usuario.password.$invalid">
              <small class="form-text text-danger" v-if="!$v.usuario.password.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pedidosrapiditosApp.usuario.rol')" for="usuario-rol">Rol</label>
            <select
              class="form-control"
              name="rol"
              :class="{ valid: !$v.usuario.rol.$invalid, invalid: $v.usuario.rol.$invalid }"
              v-model="$v.usuario.rol.$model"
              id="usuario-rol"
              data-cy="rol"
              required
            >
              <option value="CLIENTE" v-bind:label="$t('pedidosrapiditosApp.RolUsuario.CLIENTE')">CLIENTE</option>
              <option value="RESTAURANTE" v-bind:label="$t('pedidosrapiditosApp.RolUsuario.RESTAURANTE')">RESTAURANTE</option>
              <option value="REPARTIDOR" v-bind:label="$t('pedidosrapiditosApp.RolUsuario.REPARTIDOR')">REPARTIDOR</option>
            </select>
            <div v-if="$v.usuario.rol.$anyDirty && $v.usuario.rol.$invalid">
              <small class="form-text text-danger" v-if="!$v.usuario.rol.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pedidosrapiditosApp.usuario.telefono')" for="usuario-telefono">Telefono</label>
            <input
              type="text"
              class="form-control"
              name="telefono"
              id="usuario-telefono"
              data-cy="telefono"
              :class="{ valid: !$v.usuario.telefono.$invalid, invalid: $v.usuario.telefono.$invalid }"
              v-model="$v.usuario.telefono.$model"
              required
            />
            <div v-if="$v.usuario.telefono.$anyDirty && $v.usuario.telefono.$invalid">
              <small class="form-text text-danger" v-if="!$v.usuario.telefono.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pedidosrapiditosApp.usuario.direccion1')" for="usuario-direccion1"
              >Direccion 1</label
            >
            <input
              type="text"
              class="form-control"
              name="direccion1"
              id="usuario-direccion1"
              data-cy="direccion1"
              :class="{ valid: !$v.usuario.direccion1.$invalid, invalid: $v.usuario.direccion1.$invalid }"
              v-model="$v.usuario.direccion1.$model"
              required
            />
            <div v-if="$v.usuario.direccion1.$anyDirty && $v.usuario.direccion1.$invalid">
              <small class="form-text text-danger" v-if="!$v.usuario.direccion1.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pedidosrapiditosApp.usuario.ciudad')" for="usuario-ciudad">Ciudad</label>
            <input
              type="text"
              class="form-control"
              name="ciudad"
              id="usuario-ciudad"
              data-cy="ciudad"
              :class="{ valid: !$v.usuario.ciudad.$invalid, invalid: $v.usuario.ciudad.$invalid }"
              v-model="$v.usuario.ciudad.$model"
              required
            />
            <div v-if="$v.usuario.ciudad.$anyDirty && $v.usuario.ciudad.$invalid">
              <small class="form-text text-danger" v-if="!$v.usuario.ciudad.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pedidosrapiditosApp.usuario.pais')" for="usuario-pais">Pais</label>
            <input
              type="text"
              class="form-control"
              name="pais"
              id="usuario-pais"
              data-cy="pais"
              :class="{ valid: !$v.usuario.pais.$invalid, invalid: $v.usuario.pais.$invalid }"
              v-model="$v.usuario.pais.$model"
              required
            />
            <div v-if="$v.usuario.pais.$anyDirty && $v.usuario.pais.$invalid">
              <small class="form-text text-danger" v-if="!$v.usuario.pais.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pedidosrapiditosApp.usuario.latitud')" for="usuario-latitud">Latitud</label>
            <input
              type="number"
              class="form-control"
              name="latitud"
              id="usuario-latitud"
              data-cy="latitud"
              :class="{ valid: !$v.usuario.latitud.$invalid, invalid: $v.usuario.latitud.$invalid }"
              v-model.number="$v.usuario.latitud.$model"
            />
            <div v-if="$v.usuario.latitud.$anyDirty && $v.usuario.latitud.$invalid">
              <small class="form-text text-danger" v-if="!$v.usuario.latitud.min" v-text="$t('entity.validation.min', { min: -90 })">
                This field should be at least -90.
              </small>
              <small class="form-text text-danger" v-if="!$v.usuario.latitud.max" v-text="$t('entity.validation.max', { max: 90 })">
                This field cannot be longer than 90 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.usuario.latitud.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pedidosrapiditosApp.usuario.longitud')" for="usuario-longitud">Longitud</label>
            <input
              type="number"
              class="form-control"
              name="longitud"
              id="usuario-longitud"
              data-cy="longitud"
              :class="{ valid: !$v.usuario.longitud.$invalid, invalid: $v.usuario.longitud.$invalid }"
              v-model.number="$v.usuario.longitud.$model"
            />
            <div v-if="$v.usuario.longitud.$anyDirty && $v.usuario.longitud.$invalid">
              <small class="form-text text-danger" v-if="!$v.usuario.longitud.min" v-text="$t('entity.validation.min', { min: -180 })">
                This field should be at least -180.
              </small>
              <small class="form-text text-danger" v-if="!$v.usuario.longitud.max" v-text="$t('entity.validation.max', { max: 180 })">
                This field cannot be longer than 180 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.usuario.longitud.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
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
            :disabled="$v.usuario.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./usuario-update.component.ts"></script>
