/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import RestauranteUpdateComponent from '@/entities/restaurante/restaurante-update.vue';
import RestauranteClass from '@/entities/restaurante/restaurante-update.component';
import RestauranteService from '@/entities/restaurante/restaurante.service';

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
  describe('Restaurante Management Update Component', () => {
    let wrapper: Wrapper<RestauranteClass>;
    let comp: RestauranteClass;
    let restauranteServiceStub: SinonStubbedInstance<RestauranteService>;

    beforeEach(() => {
      restauranteServiceStub = sinon.createStubInstance<RestauranteService>(RestauranteService);

      wrapper = shallowMount<RestauranteClass>(RestauranteUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          restauranteService: () => restauranteServiceStub,

          restauranteCategoriaService: () => new RestauranteCategoriaService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.restaurante = entity;
        restauranteServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(restauranteServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.restaurante = entity;
        restauranteServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(restauranteServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRestaurante = { id: 123 };
        restauranteServiceStub.find.resolves(foundRestaurante);
        restauranteServiceStub.retrieve.resolves([foundRestaurante]);

        // WHEN
        comp.beforeRouteEnter({ params: { restauranteId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.restaurante).toBe(foundRestaurante);
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
