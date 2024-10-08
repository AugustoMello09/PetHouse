import { ItemPedido } from "./ItemPedido.model";

export interface Pedido {
  id: any;
  data: string;
  itemsPedido: ItemPedido[];
  idUsuario: any;
  valorTotalPedido: number;
  pagamento: string;
  status: string;
}