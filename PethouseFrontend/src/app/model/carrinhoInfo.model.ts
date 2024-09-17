import { ItemCarrinho } from "./itemCarrinho.model";

export interface CarrinhoInfo {
  id?: any;
  itemsCarrinho: ItemCarrinho[];
  valorTotalCarrinho: number;
} 