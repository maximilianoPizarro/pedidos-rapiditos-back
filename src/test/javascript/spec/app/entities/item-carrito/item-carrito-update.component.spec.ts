/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import ItemCarritoUpdateComponent from '@/entities/item-carrito/item-carrito-update.vue';
import ItemCarritoClass from '@/entities/item-carrito/item-carrito-update.component';
import ItemCarritoService from '@/entities/item-carrito/item-carrito.service';

import ProductoService from '@/entities/producto/producto.service';

import CarritoService from '@/entities/carrito/carrito.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('ItemCarrito Management Update Component', () => {
    let wrapper: Wrapper<ItemCarritoClass>;
    let comp: ItemCarritoClass;
    let itemCarritoServiceStub: SinonStubbedInstance<ItemCarritoService>;

    beforeEach(() => {
      itemCarritoServiceStub = sinon.createStubInstance<ItemCarritoService>(ItemCarritoService);

      wrapper = shallowMount<ItemCarritoClass>(ItemCarritoUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          itemCarritoService: () => itemCarritoServiceStub,

          productoService: () => new ProductoService(),

          carritoService: () => new CarritoService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.itemCarrito = entity;
        itemCarritoServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(itemCarritoServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.itemCarrito = entity;
        itemCarritoServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(itemCarritoServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundItemCarrito = { id: 123 };
        itemCarritoServiceStub.find.resolves(foundItemCarrito);
        itemCarritoServiceStub.retrieve.resolves([foundItemCarrito]);

        // WHEN
        comp.beforeRouteEnter({ params: { itemCarritoId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.itemCarrito).toBe(foundItemCarrito);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
