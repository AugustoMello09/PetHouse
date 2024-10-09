package io.gitHub.AugustoMello09.payment.infra.entities;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pagamanto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String customer; 
	private float value;
	private String billingType; 
	private String dueDate;
	private String description;

}
