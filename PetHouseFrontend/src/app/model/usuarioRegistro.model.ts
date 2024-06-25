import { CargoId } from "./cargoId.model";

export interface usuarioRegistro {
  id?: any;
  nome: string;
  email: string;
  senha: string;
  cargos: CargoId[];
}