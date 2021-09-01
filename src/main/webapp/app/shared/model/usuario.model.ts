import { ICarrito } from '@/shared/model/carrito.model';

import { RolUsuario } from '@/shared/model/enumerations/rol-usuario.model';
export interface IUsuario {
  id?: number;
  username?: string;
  password?: string;
  rol?: RolUsuario;
  telefono?: string;
  direccion1?: string;
  ciudad?: string;
  pais?: string;
  latitud?: number | null;
  longitud?: number | null;
  carritos?: ICarrito[] | null;
}

export class Usuario implements IUsuario {
  constructor(
    public id?: number,
    public username?: string,
    public password?: string,
    public rol?: RolUsuario,
    public telefono?: string,
    public direccion1?: string,
    public ciudad?: string,
    public pais?: string,
    public latitud?: number | null,
    public longitud?: number | null,
    public carritos?: ICarrito[] | null
  ) {}
}
