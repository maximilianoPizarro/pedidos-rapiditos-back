import { Component, Vue, Inject } from 'vue-property-decorator';

import { IItemCarrito } from '@/shared/model/item-carrito.model';
import ItemCarritoService from './item-carrito.service';

@Component
export default class ItemCarritoDetails extends Vue {
  @Inject('itemCarritoService') private itemCarritoService: () => ItemCarritoService;
  public itemCarrito: IItemCarrito = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.itemCarritoId) {
        vm.retrieveItemCarrito(to.params.itemCarritoId);
      }
    });
  }

  public retrieveItemCarrito(itemCarritoId) {
    this.itemCarritoService()
      .find(itemCarritoId)
      .then(res => {
        this.itemCarrito = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
