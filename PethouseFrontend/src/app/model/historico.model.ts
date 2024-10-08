import { Pedido } from "./pedido.model";

export interface Historico {
  id: any;
  pedidos: Pedido[];
}