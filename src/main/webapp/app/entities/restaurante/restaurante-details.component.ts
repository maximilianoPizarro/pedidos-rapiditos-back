import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRestaurante } from '@/shared/model/restaurante.model';
import RestauranteService from './restaurante.service';

@Component
export default class RestauranteDetails extends Vue {
  @Inject('restauranteService') private restauranteService: () => RestauranteService;
  public restaurante: IRestaurante = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.restauranteId) {
        vm.retrieveRestaurante(to.params.restauranteId);
      }
    });
  }

  public retrieveRestaurante(restauranteId) {
    this.restauranteService()
      .find(restauranteId)
      .then(res => {
        this.restaurante = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
