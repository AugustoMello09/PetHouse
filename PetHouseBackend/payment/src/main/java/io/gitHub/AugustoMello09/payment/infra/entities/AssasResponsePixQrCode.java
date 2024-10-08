package io.gitHub.AugustoMello09.payment.infra.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssasResponsePixQrCode {
	
	private String encodedImage;
	private String payload;

}
