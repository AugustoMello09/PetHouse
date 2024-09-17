import { Cargo } from "./cargo.model";

export interface Usuario {
  id: any;
  nome: string;
  email: string;
  cpfOrCnpj: string;
  cargos: Cargo[];
}