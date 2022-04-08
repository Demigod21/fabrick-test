package com.davide.falcone.fabricktest.repo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "transaction")
public class TransactionDTO {
	@Id
	String uuid;
	int amount;
	String currency;
	String creditorName;
	String description;
	String result;
	String errorMessage;
	LocalDate transactionDate;
}
