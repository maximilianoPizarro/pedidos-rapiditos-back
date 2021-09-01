/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import RestauranteCategoriaComponent from '@/entities/restaurante-categoria/restaurante-categoria.vue';
import RestauranteCategoriaClass from '@/entities/restaurante-categoria/restaurante-categoria.component';
import RestauranteCategoriaService from '@/entities/restaurante-categoria/restaurante-categoria.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.component('jhi-sort-indicator', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('RestauranteCategoria Management Component', () => {
    let wrapper: Wrapper<RestauranteCategoriaClass>;
    let comp: RestauranteCategoriaClass;
    let restauranteCategoriaServiceStub: SinonStubbedInstance<RestauranteCategoriaService>;

    beforeEach(() => {
      restauranteCategoriaServiceStub = sinon.createStubInstance<RestauranteCategoriaService>(RestauranteCategoriaService);
      restauranteCategoriaServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<RestauranteCategoriaClass>(RestauranteCategoriaComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          restauranteCategoriaService: () => restauranteCategoriaServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      restauranteCategoriaServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllRestauranteCategorias();
      await comp.$nextTick();

      // THEN
      expect(restauranteCategoriaServiceStub.retrieve.called).toBeTruthy();
      expect(comp.restauranteCategorias[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      restauranteCategoriaServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(restauranteCategoriaServiceStub.retrieve.called).toBeTruthy();
      expect(comp.restauranteCategorias[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      restauranteCategoriaServiceStub.retrieve.reset();
      restauranteCategoriaServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(restauranteCategoriaServiceStub.retrieve.callCount).toEqual(2);
      expect(comp.page).toEqual(1);
      expect(comp.restauranteCategorias[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,asc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // GIVEN
      comp.propOrder = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,asc', 'id']);
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      restauranteCategoriaServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeRestauranteCategoria();
      await comp.$nextTick();

      // THEN
      expect(restauranteCategoriaServiceStub.delete.called).toBeTruthy();
      expect(restauranteCategoriaServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
