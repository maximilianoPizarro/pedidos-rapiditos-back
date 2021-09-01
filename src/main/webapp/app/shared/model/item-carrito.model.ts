import { IProducto } from '@/shared/model/producto.model';
import { ICarrito } from '@/shared/model/carrito.model';

export interface IItemCarrito {
  id?: number;
  cantidad?: number;
  precioTotal?: number;
  producto?: IProducto;
  carrito?: ICarrito;
}

export class ItemCarrito implements IItemCarrito {
  constructor(
    public id?: number,
    public cantidad?: number,
    public precioTotal?: number,
    public producto?: IProducto,
    public carrito?: ICarrito
  ) {}
}
