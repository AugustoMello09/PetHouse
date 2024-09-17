import { CargoId } from "src/app/model/CargoId.model";


export interface usuarioRegistro {
  id?: any;
  nome: string;
  email: string;
  cpfCnpj: string;
  senha: string;
  cargos: CargoId[];
}