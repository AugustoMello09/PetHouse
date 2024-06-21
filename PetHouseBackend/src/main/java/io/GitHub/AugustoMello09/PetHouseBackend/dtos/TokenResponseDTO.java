package io.GitHub.AugustoMello09.PetHouseBackend.dtos;

import lombok.Builder;

@Builder
public record TokenResponseDTO(String accessToken, String refreshToken) {

}
