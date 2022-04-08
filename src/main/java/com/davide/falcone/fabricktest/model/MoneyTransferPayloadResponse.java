package com.davide.falcone.fabricktest.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
public class MoneyTransferPayloadResponse {
	public String moneyTransferId;
	public String status;
	public String direction;
	public Creditor creditor;
	public Debtor debtor;
	public String cro;
	public String uri;
	public String trn;
	public String description;
	public LocalDate createdDatetime;
	public LocalDate accountedDatetime;
	public String debtorValueDate;
	public String creditorValueDate;
	public Amount amount;
	public boolean isUrgent;
	public boolean isInstant;
	public String feeType;
	public String feeAccountId;
	public ArrayList<Fee> fees;
	public boolean hasTaxRelief;
}
