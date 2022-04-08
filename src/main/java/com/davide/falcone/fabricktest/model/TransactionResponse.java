package com.davide.falcone.fabricktest.model;

import lombok.Data;

@Data
public class TransactionResponse {
	private String status;
	private Object error;
	private TransactionPayloadResponse payload;
}
