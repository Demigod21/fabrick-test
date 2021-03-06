package com.davide.falcone.fabricktest.service;

import com.davide.falcone.fabricktest.exception.FabrickTestException;
import com.davide.falcone.fabricktest.model.MoneyTransferResponse;
import com.davide.falcone.fabricktest.model.TransactionPayloadResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface AccountService {

	BigDecimal getAccountBalance(String accountId) throws FabrickTestException;

	TransactionPayloadResponse getListTransaction(String accountId, String fromAccountingDate, String toAccountingDate) throws FabrickTestException;

	MoneyTransferResponse transferMoney(String receiverName, String description, String currency, String amount, String executionDate, String accountId) throws FabrickTestException;

}
