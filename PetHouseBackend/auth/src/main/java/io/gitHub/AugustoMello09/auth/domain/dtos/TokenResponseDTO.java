package io.gitHub.AugustoMello09.auth.domain.dtos;

import lombok.Builder;

@Builder
public record TokenResponseDTO(String accessToken, String refreshToken) {

}
