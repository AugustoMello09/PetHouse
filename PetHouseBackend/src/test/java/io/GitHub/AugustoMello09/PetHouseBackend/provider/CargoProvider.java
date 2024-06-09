package io.GitHub.AugustoMello09.PetHouseBackend.provider;

import io.GitHub.AugustoMello09.PetHouseBackend.entities.Cargo;

public class CargoProvider {

	private static final long ID = 1L;
	private static final String AUTHORITY = "ROLE_OPERATOR";

	public Cargo criar() {
		Cargo cargo = new Cargo();
		cargo.setId(ID);
		cargo.setAuthority(AUTHORITY);
		return cargo;
	}
}
