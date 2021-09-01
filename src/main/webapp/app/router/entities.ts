import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// prettier-ignore
const Producto = () => import('@/entities/producto/producto.vue');
// prettier-ignore
const ProductoUpdate = () => import('@/entities/producto/producto-update.vue');
// prettier-ignore
const ProductoDetails = () => import('@/entities/producto/producto-details.vue');
// prettier-ignore
const Restaurante = () => import('@/entities/restaurante/restaurante.vue');
// prettier-ignore
const RestauranteUpdate = () => import('@/entities/restaurante/restaurante-update.vue');
// prettier-ignore
const RestauranteDetails = () => import('@/entities/restaurante/restaurante-details.vue');
// prettier-ignore
const RestauranteCategoria = () => import('@/entities/restaurante-categoria/restaurante-categoria.vue');
// prettier-ignore
const RestauranteCategoriaUpdate = () => import('@/entities/restaurante-categoria/restaurante-categoria-update.vue');
// prettier-ignore
const RestauranteCategoriaDetails = () => import('@/entities/restaurante-categoria/restaurante-categoria-details.vue');
// prettier-ignore
const ProductoCategoria = () => import('@/entities/producto-categoria/producto-categoria.vue');
// prettier-ignore
const ProductoCategoriaUpdate = () => import('@/entities/producto-categoria/producto-categoria-update.vue');
// prettier-ignore
const ProductoCategoriaDetails = () => import('@/entities/producto-categoria/producto-categoria-details.vue');
// prettier-ignore
const Usuario = () => import('@/entities/usuario/usuario.vue');
// prettier-ignore
const UsuarioUpdate = () => import('@/entities/usuario/usuario-update.vue');
// prettier-ignore
const UsuarioDetails = () => import('@/entities/usuario/usuario-details.vue');
// prettier-ignore
const Carrito = () => import('@/entities/carrito/carrito.vue');
// prettier-ignore
const CarritoUpdate = () => import('@/entities/carrito/carrito-update.vue');
// prettier-ignore
const CarritoDetails = () => import('@/entities/carrito/carrito-details.vue');
// prettier-ignore
const ItemCarrito = () => import('@/entities/item-carrito/item-carrito.vue');
// prettier-ignore
const ItemCarritoUpdate = () => import('@/entities/item-carrito/item-carrito-update.vue');
// prettier-ignore
const ItemCarritoDetails = () => import('@/entities/item-carrito/item-carrito-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default [
  {
    path: '/producto',
    name: 'Producto',
    component: Producto,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/producto/new',
    name: 'ProductoCreate',
    component: ProductoUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/producto/:productoId/edit',
    name: 'ProductoEdit',
    component: ProductoUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/producto/:productoId/view',
    name: 'ProductoView',
    component: ProductoDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/restaurante',
    name: 'Restaurante',
    component: Restaurante,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/restaurante/new',
    name: 'RestauranteCreate',
    component: RestauranteUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/restaurante/:restauranteId/edit',
    name: 'RestauranteEdit',
    component: RestauranteUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/restaurante/:restauranteId/view',
    name: 'RestauranteView',
    component: RestauranteDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/restaurante-categoria',
    name: 'RestauranteCategoria',
    component: RestauranteCategoria,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/restaurante-categoria/new',
    name: 'RestauranteCategoriaCreate',
    component: RestauranteCategoriaUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/restaurante-categoria/:restauranteCategoriaId/edit',
    name: 'RestauranteCategoriaEdit',
    component: RestauranteCategoriaUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/restaurante-categoria/:restauranteCategoriaId/view',
    name: 'RestauranteCategoriaView',
    component: RestauranteCategoriaDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/producto-categoria',
    name: 'ProductoCategoria',
    component: ProductoCategoria,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/producto-categoria/new',
    name: 'ProductoCategoriaCreate',
    component: ProductoCategoriaUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/producto-categoria/:productoCategoriaId/edit',
    name: 'ProductoCategoriaEdit',
    component: ProductoCategoriaUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/producto-categoria/:productoCategoriaId/view',
    name: 'ProductoCategoriaView',
    component: ProductoCategoriaDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/usuario',
    name: 'Usuario',
    component: Usuario,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/usuario/new',
    name: 'UsuarioCreate',
    component: UsuarioUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/usuario/:usuarioId/edit',
    name: 'UsuarioEdit',
    component: UsuarioUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/usuario/:usuarioId/view',
    name: 'UsuarioView',
    component: UsuarioDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/carrito',
    name: 'Carrito',
    component: Carrito,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/carrito/new',
    name: 'CarritoCreate',
    component: CarritoUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/carrito/:carritoId/edit',
    name: 'CarritoEdit',
    component: CarritoUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/carrito/:carritoId/view',
    name: 'CarritoView',
    component: CarritoDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/item-carrito',
    name: 'ItemCarrito',
    component: ItemCarrito,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/item-carrito/new',
    name: 'ItemCarritoCreate',
    component: ItemCarritoUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/item-carrito/:itemCarritoId/edit',
    name: 'ItemCarritoEdit',
    component: ItemCarritoUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/item-carrito/:itemCarritoId/view',
    name: 'ItemCarritoView',
    component: ItemCarritoDetails,
    meta: { authorities: [Authority.USER] },
  },
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
