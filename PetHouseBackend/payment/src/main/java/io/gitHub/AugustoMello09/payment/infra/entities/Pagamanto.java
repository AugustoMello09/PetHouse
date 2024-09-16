package io.gitHub.AugustoMello09.payment.infra.entities;

import java.util.UUID;

import io.gitHub.AugustoMello09.payment.infra.entities.enums.billingType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pagamanto {
	
	private UUID id;
	private float value;
	private billingType billingType;
	private String dueDate;

}
