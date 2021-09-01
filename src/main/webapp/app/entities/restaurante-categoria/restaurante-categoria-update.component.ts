import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import { IRestauranteCategoria, RestauranteCategoria } from '@/shared/model/restaurante-categoria.model';
import RestauranteCategoriaService from './restaurante-categoria.service';

const validations: any = {
  restauranteCategoria: {
    nombre: {
      required,
    },
    description: {},
  },
};

@Component({
  validations,
})
export default class RestauranteCategoriaUpdate extends Vue {
  @Inject('restauranteCategoriaService') private restauranteCategoriaService: () => RestauranteCategoriaService;
  public restauranteCategoria: IRestauranteCategoria = new RestauranteCategoria();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.restauranteCategoriaId) {
        vm.retrieveRestauranteCategoria(to.params.restauranteCategoriaId);
      }
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
    if (this.restauranteCategoria.id) {
      this.restauranteCategoriaService()
        .update(this.restauranteCategoria)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('pedidosrapiditosApp.restauranteCategoria.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.restauranteCategoriaService()
        .create(this.restauranteCategoria)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('pedidosrapiditosApp.restauranteCategoria.created', { param: param.id });
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

  public retrieveRestauranteCategoria(restauranteCategoriaId): void {
    this.restauranteCategoriaService()
      .find(restauranteCategoriaId)
      .then(res => {
        this.restauranteCategoria = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
