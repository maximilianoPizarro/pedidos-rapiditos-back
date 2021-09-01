import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRestauranteCategoria } from '@/shared/model/restaurante-categoria.model';
import RestauranteCategoriaService from './restaurante-categoria.service';

@Component
export default class RestauranteCategoriaDetails extends Vue {
  @Inject('restauranteCategoriaService') private restauranteCategoriaService: () => RestauranteCategoriaService;
  public restauranteCategoria: IRestauranteCategoria = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.restauranteCategoriaId) {
        vm.retrieveRestauranteCategoria(to.params.restauranteCategoriaId);
      }
    });
  }

  public retrieveRestauranteCategoria(restauranteCategoriaId) {
    this.restauranteCategoriaService()
      .find(restauranteCategoriaId)
      .then(res => {
        this.restauranteCategoria = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
