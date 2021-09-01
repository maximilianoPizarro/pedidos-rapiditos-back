/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import RestauranteCategoriaDetailComponent from '@/entities/restaurante-categoria/restaurante-categoria-details.vue';
import RestauranteCategoriaClass from '@/entities/restaurante-categoria/restaurante-categoria-details.component';
import RestauranteCategoriaService from '@/entities/restaurante-categoria/restaurante-categoria.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('RestauranteCategoria Management Detail Component', () => {
    let wrapper: Wrapper<RestauranteCategoriaClass>;
    let comp: RestauranteCategoriaClass;
    let restauranteCategoriaServiceStub: SinonStubbedInstance<RestauranteCategoriaService>;

    beforeEach(() => {
      restauranteCategoriaServiceStub = sinon.createStubInstance<RestauranteCategoriaService>(RestauranteCategoriaService);

      wrapper = shallowMount<RestauranteCategoriaClass>(RestauranteCategoriaDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { restauranteCategoriaService: () => restauranteCategoriaServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundRestauranteCategoria = { id: 123 };
        restauranteCategoriaServiceStub.find.resolves(foundRestauranteCategoria);

        // WHEN
        comp.retrieveRestauranteCategoria(123);
        await comp.$nextTick();

        // THEN
        expect(comp.restauranteCategoria).toBe(foundRestauranteCategoria);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRestauranteCategoria = { id: 123 };
        restauranteCategoriaServiceStub.find.resolves(foundRestauranteCategoria);

        // WHEN
        comp.beforeRouteEnter({ params: { restauranteCategoriaId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.restauranteCategoria).toBe(foundRestauranteCategoria);
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
