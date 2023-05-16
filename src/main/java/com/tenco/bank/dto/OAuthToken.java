package com.tenco.bank.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OAuthToken {
	
	private String tokenType;
	private String accessToken;
	private Integer expirensIn;
	private String refreshToken;
	private Integer refreshTokenExpiresIn;

}
