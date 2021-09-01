import { IItemCarrito } from '@/shared/model/item-carrito.model';
import { IRestaurante } from '@/shared/model/restaurante.model';
import { IUsuario } from '@/shared/model/usuario.model';

import { OrdenStatus } from '@/shared/model/enumerations/orden-status.model';
import { MetodoDePago } from '@/shared/model/enumerations/metodo-de-pago.model';
export interface ICarrito {
  id?: number;
  fecha?: Date;
  status?: OrdenStatus;
  precioTotal?: number;
  metodoDePago?: MetodoDePago;
  referencia?: string | null;
  items?: IItemCarrito[] | null;
  restaurante?: IRestaurante;
  usuario?: IUsuario;
}

export class Carrito implements ICarrito {
  constructor(
    public id?: number,
    public fecha?: Date,
    public status?: OrdenStatus,
    public precioTotal?: number,
    public metodoDePago?: MetodoDePago,
    public referencia?: string | null,
    public items?: IItemCarrito[] | null,
    public restaurante?: IRestaurante,
    public usuario?: IUsuario
  ) {}
}
