package com.davide.falcone.fabricktest.controller;

import com.davide.falcone.fabricktest.config.ConfigProperties;
import com.davide.falcone.fabricktest.model.MoneyTransferResponse;
import com.davide.falcone.fabricktest.model.TransactionPayloadResponse;
import com.davide.falcone.fabricktest.model.TransactionResponse;
import com.davide.falcone.fabricktest.service.AccountService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Optional;

@RequestMapping("/account")
@RestController
public class AccountController {

	@Autowired
	ConfigProperties configProperties;

	@Autowired
	AccountService accountService;

	@RequestMapping(method = RequestMethod.GET, value = "/balance")
	public BigDecimal getBalance() {
		String accountId = Optional.ofNullable(configProperties)
			.map(ConfigProperties::getAccountId)
			.orElse(Strings.EMPTY);
		return accountService.getAccountBalance(accountId);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/transactions")
	public TransactionPayloadResponse getTransactions() {
		String accountId = Optional.ofNullable(configProperties)
			.map(ConfigProperties::getAccountId)
			.orElse(Strings.EMPTY);
		String fromAccountingDate = Optional.ofNullable(configProperties)
			.map(ConfigProperties::getFromAccountingDate)
			.orElse(Strings.EMPTY);
		String toAccountingDate = Optional.ofNullable(configProperties)
			.map(ConfigProperties::getToAccountingDate)
			.orElse(Strings.EMPTY);
		return accountService.getListTransaction(accountId, fromAccountingDate, toAccountingDate);
	}

	@PostMapping("/transfer-money")
	@ResponseBody
	public MoneyTransferResponse addFoo(@RequestParam String receiverName, @RequestParam String description, @RequestParam String currency,
																			@RequestParam String amount, @RequestParam String executionDate) {
		String accountId = Optional.ofNullable(configProperties)
			.map(ConfigProperties::getAccountId)
			.orElse(Strings.EMPTY);
		return accountService.transferMoney(receiverName, description, currency, amount, executionDate, accountId);
	}

}
