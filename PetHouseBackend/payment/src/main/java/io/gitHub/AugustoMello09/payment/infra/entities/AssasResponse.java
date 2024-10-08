package io.gitHub.AugustoMello09.payment.infra.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssasResponse {
	
	private String id;
	private String description;
	private String dueDate;
	private String nossoNumero;
	private String invoiceNumber;
	private String dateCreated;

}
