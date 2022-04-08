package com.davide.falcone.fabricktest.model;

import lombok.Data;

@Data
public class Fee {
	public String feeCode;
	public String description;
	public double amount;
	public String currency;
}
