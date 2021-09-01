/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import RestauranteDetailComponent from '@/entities/restaurante/restaurante-details.vue';
import RestauranteClass from '@/entities/restaurante/restaurante-details.component';
import RestauranteService from '@/entities/restaurante/restaurante.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Restaurante Management Detail Component', () => {
    let wrapper: Wrapper<RestauranteClass>;
    let comp: RestauranteClass;
    let restauranteServiceStub: SinonStubbedInstance<RestauranteService>;

    beforeEach(() => {
      restauranteServiceStub = sinon.createStubInstance<RestauranteService>(RestauranteService);

      wrapper = shallowMount<RestauranteClass>(RestauranteDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { restauranteService: () => restauranteServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundRestaurante = { id: 123 };
        restauranteServiceStub.find.resolves(foundRestaurante);

        // WHEN
        comp.retrieveRestaurante(123);
        await comp.$nextTick();

        // THEN
        expect(comp.restaurante).toBe(foundRestaurante);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRestaurante = { id: 123 };
        restauranteServiceStub.find.resolves(foundRestaurante);

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
