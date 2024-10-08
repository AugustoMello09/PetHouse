export interface Pagamento {
  id: any;
  metodoPagamento: string;
  linkPagamento: string;
  preco: number;
  encodedImage: string;
  payload: string;
  dueDate: string;
  nossoNumero: string;
  identificationField: string;
  description: string;
  barCode: string;
  dataCriacao: string;
  invoiceNumber: string;
  nome: string;
  cpfCnpj: string;
  email: string;
}