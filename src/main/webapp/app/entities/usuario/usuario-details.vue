<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="usuario">
        <h2 class="jh-entity-heading" data-cy="usuarioDetailsHeading">
          <span v-text="$t('pedidosrapiditosApp.usuario.detail.title')">Usuario</span> {{ usuario.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('pedidosrapiditosApp.usuario.username')">Username</span>
          </dt>
          <dd>
            <span>{{ usuario.username }}</span>
          </dd>
          <dt>
            <span v-text="$t('pedidosrapiditosApp.usuario.password')">Password</span>
          </dt>
          <dd>
            <span>{{ usuario.password }}</span>
          </dd>
          <dt>
            <span v-text="$t('pedidosrapiditosApp.usuario.rol')">Rol</span>
          </dt>
          <dd>
            <span v-text="$t('pedidosrapiditosApp.RolUsuario.' + usuario.rol)">{{ usuario.rol }}</span>
          </dd>
          <dt>
            <span v-text="$t('pedidosrapiditosApp.usuario.telefono')">Telefono</span>
          </dt>
          <dd>
            <span>{{ usuario.telefono }}</span>
          </dd>
          <dt>
            <span v-text="$t('pedidosrapiditosApp.usuario.direccion1')">Direccion 1</span>
          </dt>
          <dd>
            <span>{{ usuario.direccion1 }}</span>
          </dd>
          <dt>
            <span v-text="$t('pedidosrapiditosApp.usuario.ciudad')">Ciudad</span>
          </dt>
          <dd>
            <span>{{ usuario.ciudad }}</span>
          </dd>
          <dt>
            <span v-text="$t('pedidosrapiditosApp.usuario.pais')">Pais</span>
          </dt>
          <dd>
            <span>{{ usuario.pais }}</span>
          </dd>
          <dt>
            <span v-text="$t('pedidosrapiditosApp.usuario.latitud')">Latitud</span>
          </dt>
          <dd>
            <span>{{ usuario.latitud }}</span>
          </dd>
          <dt>
            <span v-text="$t('pedidosrapiditosApp.usuario.longitud')">Longitud</span>
          </dt>
          <dd>
            <span>{{ usuario.longitud }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="usuario.id" :to="{ name: 'UsuarioEdit', params: { usuarioId: usuario.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./usuario-details.component.ts"></script>
