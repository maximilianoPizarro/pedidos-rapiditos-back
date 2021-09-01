/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ItemCarritoComponent from '@/entities/item-carrito/item-carrito.vue';
import ItemCarritoClass from '@/entities/item-carrito/item-carrito.component';
import ItemCarritoService from '@/entities/item-carrito/item-carrito.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
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
  describe('ItemCarrito Management Component', () => {
    let wrapper: Wrapper<ItemCarritoClass>;
    let comp: ItemCarritoClass;
    let itemCarritoServiceStub: SinonStubbedInstance<ItemCarritoService>;

    beforeEach(() => {
      itemCarritoServiceStub = sinon.createStubInstance<ItemCarritoService>(ItemCarritoService);
      itemCarritoServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ItemCarritoClass>(ItemCarritoComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          itemCarritoService: () => itemCarritoServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      itemCarritoServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllItemCarritos();
      await comp.$nextTick();

      // THEN
      expect(itemCarritoServiceStub.retrieve.called).toBeTruthy();
      expect(comp.itemCarritos[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      itemCarritoServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeItemCarrito();
      await comp.$nextTick();

      // THEN
      expect(itemCarritoServiceStub.delete.called).toBeTruthy();
      expect(itemCarritoServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
