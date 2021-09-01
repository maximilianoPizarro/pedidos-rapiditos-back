import { IRestauranteCategoria } from '@/shared/model/restaurante-categoria.model';

export interface IRestaurante {
  id?: number;
  nombre?: string;
  descripcion?: string | null;
  activo?: boolean | null;
  calificacion?: number | null;
  latitud?: number | null;
  longitud?: number | null;
  categoria?: IRestauranteCategoria;
}

export class Restaurante implements IRestaurante {
  constructor(
    public id?: number,
    public nombre?: string,
    public descripcion?: string | null,
    public activo?: boolean | null,
    public calificacion?: number | null,
    public latitud?: number | null,
    public longitud?: number | null,
    public categoria?: IRestauranteCategoria
  ) {
    this.activo = this.activo ?? false;
  }
}
