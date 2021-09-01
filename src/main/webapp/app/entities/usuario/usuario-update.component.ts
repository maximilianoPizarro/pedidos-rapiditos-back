import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, decimal, minValue, maxValue } from 'vuelidate/lib/validators';

import CarritoService from '@/entities/carrito/carrito.service';
import { ICarrito } from '@/shared/model/carrito.model';

import { IUsuario, Usuario } from '@/shared/model/usuario.model';
import UsuarioService from './usuario.service';

const validations: any = {
  usuario: {
    username: {
      required,
    },
    password: {
      required,
    },
    rol: {
      required,
    },
    telefono: {
      required,
    },
    direccion1: {
      required,
    },
    ciudad: {
      required,
    },
    pais: {
      required,
    },
    latitud: {
      decimal,
      min: minValue(-90),
      max: maxValue(90),
    },
    longitud: {
      decimal,
      min: minValue(-180),
      max: maxValue(180),
    },
  },
};

@Component({
  validations,
})
export default class UsuarioUpdate extends Vue {
  @Inject('usuarioService') private usuarioService: () => UsuarioService;
  public usuario: IUsuario = new Usuario();

  @Inject('carritoService') private carritoService: () => CarritoService;

  public carritos: ICarrito[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.usuarioId) {
        vm.retrieveUsuario(to.params.usuarioId);
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
    if (this.usuario.id) {
      this.usuarioService()
        .update(this.usuario)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('pedidosrapiditosApp.usuario.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.usuarioService()
        .create(this.usuario)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('pedidosrapiditosApp.usuario.created', { param: param.id });
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

  public retrieveUsuario(usuarioId): void {
    this.usuarioService()
      .find(usuarioId)
      .then(res => {
        this.usuario = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.carritoService()
      .retrieve()
      .then(res => {
        this.carritos = res.data;
      });
  }
}
