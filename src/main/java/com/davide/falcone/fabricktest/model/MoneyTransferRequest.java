package com.davide.falcone.fabricktest.model;

import lombok.Data;

@Data
public class MoneyTransferRequest {
	public Creditor creditor;
	public String executionDate;
	public String uri;
	public String description;
	public int amount;
	public String currency;
	public boolean isUrgent;
	public boolean isInstant;
	public String feeType;
	public String feeAccountId;
	public TaxRelief taxRelief;
}
