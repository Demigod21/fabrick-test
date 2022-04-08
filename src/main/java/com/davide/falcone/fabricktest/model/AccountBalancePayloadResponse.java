package com.davide.falcone.fabricktest.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AccountBalancePayloadResponse {

	private LocalDate date;
	private BigDecimal balance;
	private BigDecimal availableBlanace;
	private String currency;
}
