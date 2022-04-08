package com.davide.falcone.fabricktest.model;

import lombok.Data;

@Data
public class AccountBalanceResponse {
	private String status;
	private Object error;
	private AccountBalancePayloadResponse payload;

}
