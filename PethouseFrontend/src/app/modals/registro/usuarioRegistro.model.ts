import { CargoId } from "src/app/model/cargoId.model";



export interface usuarioRegistro {
  id?: any;
  nome: string;
  email: string;
  cpfCnpj: string;
  senha: string;
  cargos: CargoId[];
}