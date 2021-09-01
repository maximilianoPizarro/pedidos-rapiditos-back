/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import RestauranteCategoriaUpdateComponent from '@/entities/restaurante-categoria/restaurante-categoria-update.vue';
import RestauranteCategoriaClass from '@/entities/restaurante-categoria/restaurante-categoria-update.component';
import RestauranteCategoriaService from '@/entities/restaurante-categoria/restaurante-categoria.service';

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
  describe('RestauranteCategoria Management Update Component', () => {
    let wrapper: Wrapper<RestauranteCategoriaClass>;
    let comp: RestauranteCategoriaClass;
    let restauranteCategoriaServiceStub: SinonStubbedInstance<RestauranteCategoriaService>;

    beforeEach(() => {
      restauranteCategoriaServiceStub = sinon.createStubInstance<RestauranteCategoriaService>(RestauranteCategoriaService);

      wrapper = shallowMount<RestauranteCategoriaClass>(RestauranteCategoriaUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          restauranteCategoriaService: () => restauranteCategoriaServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.restauranteCategoria = entity;
        restauranteCategoriaServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(restauranteCategoriaServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.restauranteCategoria = entity;
        restauranteCategoriaServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(restauranteCategoriaServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRestauranteCategoria = { id: 123 };
        restauranteCategoriaServiceStub.find.resolves(foundRestauranteCategoria);
        restauranteCategoriaServiceStub.retrieve.resolves([foundRestauranteCategoria]);

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
