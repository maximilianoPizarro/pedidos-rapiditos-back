import { IRestaurante } from '@/shared/model/restaurante.model';
import { IProductoCategoria } from '@/shared/model/producto-categoria.model';

import { Medida } from '@/shared/model/enumerations/medida.model';
export interface IProducto {
  id?: number;
  nombre?: string;
  descripcion?: string | null;
  precio?: number;
  medida?: Medida;
  imageContentType?: string | null;
  image?: string | null;
  activo?: boolean | null;
  restaurante?: IRestaurante;
  productoCategoria?: IProductoCategoria;
}

export class Producto implements IProducto {
  constructor(
    public id?: number,
    public nombre?: string,
    public descripcion?: string | null,
    public precio?: number,
    public medida?: Medida,
    public imageContentType?: string | null,
    public image?: string | null,
    public activo?: boolean | null,
    public restaurante?: IRestaurante,
    public productoCategoria?: IProductoCategoria
  ) {
    this.activo = this.activo ?? false;
  }
}
