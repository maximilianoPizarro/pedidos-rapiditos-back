import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minValue, decimal } from 'vuelidate/lib/validators';

import ProductoService from '@/entities/producto/producto.service';
import { IProducto } from '@/shared/model/producto.model';

import CarritoService from '@/entities/carrito/carrito.service';
import { ICarrito } from '@/shared/model/carrito.model';

import { IItemCarrito, ItemCarrito } from '@/shared/model/item-carrito.model';
import ItemCarritoService from './item-carrito.service';

const validations: any = {
  itemCarrito: {
    cantidad: {
      required,
      numeric,
      min: minValue(0),
    },
    precioTotal: {
      required,
      decimal,
      min: minValue(0),
    },
    producto: {
      required,
    },
    carrito: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class ItemCarritoUpdate extends Vue {
  @Inject('itemCarritoService') private itemCarritoService: () => ItemCarritoService;
  public itemCarrito: IItemCarrito = new ItemCarrito();

  @Inject('productoService') private productoService: () => ProductoService;

  public productos: IProducto[] = [];

  @Inject('carritoService') private carritoService: () => CarritoService;

  public carritos: ICarrito[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.itemCarritoId) {
        vm.retrieveItemCarrito(to.params.itemCarritoId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.itemCarrito.id) {
      this.itemCarritoService()
        .update(this.itemCarrito)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('pedidosrapiditosApp.itemCarrito.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.itemCarritoService()
        .create(this.itemCarrito)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('pedidosrapiditosApp.itemCarrito.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    }
  }

  public retrieveItemCarrito(itemCarritoId): void {
    this.itemCarritoService()
      .find(itemCarritoId)
      .then(res => {
        this.itemCarrito = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.productoService()
      .retrieve()
      .then(res => {
        this.productos = res.data;
      });
    this.carritoService()
      .retrieve()
      .then(res => {
        this.carritos = res.data;
      });
  }
}
