package io.GitHub.AugustoMello09.PetHouseBackend.provider;

import io.GitHub.AugustoMello09.PetHouseBackend.dtos.CargoDTO;

public class CargoDTOProvider {
	
	private static final long ID = 1L;
	private static final String AUTHORITY = "ROLE_OPERATOR";

	public CargoDTO criar() {
		CargoDTO cargo = new CargoDTO();
		cargo.setId(ID);
		cargo.setAuthority(AUTHORITY);
		return cargo;
	}

}
