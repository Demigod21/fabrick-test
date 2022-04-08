package com.davide.falcone.fabricktest.service;

import com.davide.falcone.fabricktest.config.ConfigProperties;
import com.davide.falcone.fabricktest.model.AccountBalancePayloadResponse;
import com.davide.falcone.fabricktest.model.AccountBalanceResponse;
import com.davide.falcone.fabricktest.model.MoneyTransferResponse;
import com.davide.falcone.fabricktest.model.TransactionPayloadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService{

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	ConfigProperties configProperties;

	@Override
	public BigDecimal getAccountBalance(String accountId) {
		String accountBalanceEndpoint = configProperties.getAccountBalanceEndpoint();
		// URI (URL) parameters
		Map<String, String> urlParams = new HashMap<>();
		urlParams.put("accountId", accountId);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Auth-Schema", "S2S");
		headers.add("Api-Key", "FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP");

		HttpEntity requestEntity = new HttpEntity<>(headers);

		ResponseEntity<AccountBalanceResponse> response = restTemplate.exchange(accountBalanceEndpoint,
			HttpMethod.GET,
			requestEntity,
			AccountBalanceResponse.class,
			urlParams);
		BigDecimal output = Optional.ofNullable(response)
			.map(HttpEntity::getBody)
			.map(AccountBalanceResponse::getPayload)
			.map(AccountBalancePayloadResponse::getBalance)
			.orElse(null);

		return output;
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
