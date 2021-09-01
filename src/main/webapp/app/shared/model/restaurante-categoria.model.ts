export interface IRestauranteCategoria {
  id?: number;
  nombre?: string;
  description?: string | null;
}

export class RestauranteCategoria implements IRestauranteCategoria {
  constructor(public id?: number, public nombre?: string, public description?: string | null) {}
}
