/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ItemCarritoDetailComponent from '@/entities/item-carrito/item-carrito-details.vue';
import ItemCarritoClass from '@/entities/item-carrito/item-carrito-details.component';
import ItemCarritoService from '@/entities/item-carrito/item-carrito.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ItemCarrito Management Detail Component', () => {
    let wrapper: Wrapper<ItemCarritoClass>;
    let comp: ItemCarritoClass;
    let itemCarritoServiceStub: SinonStubbedInstance<ItemCarritoService>;

    beforeEach(() => {
      itemCarritoServiceStub = sinon.createStubInstance<ItemCarritoService>(ItemCarritoService);

      wrapper = shallowMount<ItemCarritoClass>(ItemCarritoDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { itemCarritoService: () => itemCarritoServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundItemCarrito = { id: 123 };
        itemCarritoServiceStub.find.resolves(foundItemCarrito);

        // WHEN
        comp.retrieveItemCarrito(123);
        await comp.$nextTick();

        // THEN
        expect(comp.itemCarrito).toBe(foundItemCarrito);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundItemCarrito = { id: 123 };
        itemCarritoServiceStub.find.resolves(foundItemCarrito);

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
