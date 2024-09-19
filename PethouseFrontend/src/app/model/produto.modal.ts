import { Categoria } from "./categoria.model";

export interface Produto {
  id: number;
  nome: string;
  descricao: string;
  tipo: string;
  preco: number;
  categoria: Categoria;
  img: string;
}