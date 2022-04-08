package com.davide.falcone.fabricktest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigProperties {
	private final String accountId;
	private final String moneyTransferEndpoint;
	private final String transactionEndpoint;
	private final String accountBalanceEndpoint;
	private final String fromAccountingDate;
	private final String toAccountingDate;

	public ConfigProperties(@Value("${fabrick.test.account.id}") final String accountId,
													@Value("${fabrick.test.endpoint.moneytransfer}") final String moneyTransferEndpoint,
													@Value("${fabrick.test.endpoint.transaction}") final String transactionEndpoint,
													@Value("${fabrick.test.endpoint.accountbalance}") final String accountBalanceEndpoint,
													@Value("${fabrick.test.from.accountingdate}") final String fromAccountingDate,
													@Value("${fabrick.test.to.accountingdate}") final String toAccountingDate
	) {

		this.accountId = accountId;
		this.moneyTransferEndpoint = moneyTransferEndpoint;
		this.transactionEndpoint = transactionEndpoint;
		this.accountBalanceEndpoint = accountBalanceEndpoint;
		this.fromAccountingDate = fromAccountingDate;
		this.toAccountingDate = toAccountingDate;

	}

	public String getAccountId() {
		return accountId;
	}

	public String getMoneyTransferEndpoint() {
		return moneyTransferEndpoint;
	}

	public String getTransactionEndpoint() {
		return transactionEndpoint;
	}

	public String getAccountBalanceEndpoint() {
		return accountBalanceEndpoint;
	}

	public String getFromAccountingDate() {
		return fromAccountingDate;
	}

	public String getToAccountingDate() {
		return toAccountingDate;
	}
}
