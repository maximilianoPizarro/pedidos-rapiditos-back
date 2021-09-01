import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IItemCarrito } from '@/shared/model/item-carrito.model';

import ItemCarritoService from './item-carrito.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class ItemCarrito extends Vue {
  @Inject('itemCarritoService') private itemCarritoService: () => ItemCarritoService;
  private removeId: number = null;

  public itemCarritos: IItemCarrito[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllItemCarritos();
  }

  public clear(): void {
    this.retrieveAllItemCarritos();
  }

  public retrieveAllItemCarritos(): void {
    this.isFetching = true;
    this.itemCarritoService()
      .retrieve()
      .then(
        res => {
          this.itemCarritos = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IItemCarrito): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeItemCarrito(): void {
    this.itemCarritoService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('pedidosrapiditosApp.itemCarrito.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllItemCarritos();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
