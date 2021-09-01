import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, decimal, minValue } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import ItemCarritoService from '@/entities/item-carrito/item-carrito.service';
import { IItemCarrito } from '@/shared/model/item-carrito.model';

import RestauranteService from '@/entities/restaurante/restaurante.service';
import { IRestaurante } from '@/shared/model/restaurante.model';

import UsuarioService from '@/entities/usuario/usuario.service';
import { IUsuario } from '@/shared/model/usuario.model';

import { ICarrito, Carrito } from '@/shared/model/carrito.model';
import CarritoService from './carrito.service';

const validations: any = {
  carrito: {
    fecha: {
      required,
    },
    status: {
      required,
    },
    precioTotal: {
      required,
      decimal,
      min: minValue(0),
    },
    metodoDePago: {
      required,
    },
    referencia: {},
    restaurante: {
      required,
    },
    usuario: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class CarritoUpdate extends Vue {
  @Inject('carritoService') private carritoService: () => CarritoService;
  public carrito: ICarrito = new Carrito();

  @Inject('itemCarritoService') private itemCarritoService: () => ItemCarritoService;

  public itemCarritos: IItemCarrito[] = [];

  @Inject('restauranteService') private restauranteService: () => RestauranteService;

  public restaurantes: IRestaurante[] = [];

  @Inject('usuarioService') private usuarioService: () => UsuarioService;

  public usuarios: IUsuario[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.carritoId) {
        vm.retrieveCarrito(to.params.carritoId);
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
    if (this.carrito.id) {
      this.carritoService()
        .update(this.carrito)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('pedidosrapiditosApp.carrito.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.carritoService()
        .create(this.carrito)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('pedidosrapiditosApp.carrito.created', { param: param.id });
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

  public convertDateTimeFromServer(date: Date): string {
    if (date && dayjs(date).isValid()) {
      return dayjs(date).format(DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.carrito[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.carrito[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.carrito[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.carrito[field] = null;
    }
  }

  public retrieveCarrito(carritoId): void {
    this.carritoService()
      .find(carritoId)
      .then(res => {
        res.fecha = new Date(res.fecha);
        this.carrito = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.itemCarritoService()
      .retrieve()
      .then(res => {
        this.itemCarritos = res.data;
      });
    this.restauranteService()
      .retrieve()
      .then(res => {
        this.restaurantes = res.data;
      });
    this.usuarioService()
      .retrieve()
      .then(res => {
        this.usuarios = res.data;
      });
  }
}
