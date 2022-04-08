package com.davide.falcone.fabricktest.model;

import lombok.Data;

@Data
public class MoneyTransferResponse {
	private String status;
	private Object error;
	private MoneyTransferPayloadResponse payload;
}
