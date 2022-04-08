package com.davide.falcone.fabricktest.service;

import com.davide.falcone.fabricktest.model.MoneyTransferResponse;
import com.davide.falcone.fabricktest.model.TransactionPayloadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService{

	@Autowired
	RestTemplate restTemplate;

	@Override
	public BigDecimal getAccountBalance(String accountId) {
		return null;
	}

	@Override
	public TransactionPayloadResponse getListTransaction(String accountId, String fromAccountingDate, String toAccountingDate) {
		return null;
	}

	@Override
	public MoneyTransferResponse transferMoney(String receiverName, String description, String currency, String amount, String executionDate, String accountId) {
		return null;
	}
}
