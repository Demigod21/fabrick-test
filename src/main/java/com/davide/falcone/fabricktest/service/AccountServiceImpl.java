package com.davide.falcone.fabricktest.service;

import com.davide.falcone.fabricktest.config.ConfigProperties;
import com.davide.falcone.fabricktest.exception.FabrickTestException;
import com.davide.falcone.fabricktest.model.Account;
import com.davide.falcone.fabricktest.model.AccountBalancePayloadResponse;
import com.davide.falcone.fabricktest.model.AccountBalanceResponse;
import com.davide.falcone.fabricktest.model.Creditor;
import com.davide.falcone.fabricktest.model.MoneyTransferRequest;
import com.davide.falcone.fabricktest.model.MoneyTransferResponse;
import com.davide.falcone.fabricktest.model.NaturalPersonBeneficiary;
import com.davide.falcone.fabricktest.model.TaxRelief;
import com.davide.falcone.fabricktest.model.TransactionPayloadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
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
	public MoneyTransferResponse transferMoney(String receiverName, String description, String currency, String amount, String executionDate, String accountId) throws FabrickTestException {
		String transferMoneyEndpoint = configProperties.getMoneyTransferEndpoint();

		// URI (URL) parameters
		Map<String, String> urlParams = new HashMap<>();
		urlParams.put("accountId", accountId);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Auth-Schema", "S2S");
		headers.add("Api-Key", "FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP");
		headers.setContentType(MediaType.APPLICATION_JSON);
		MoneyTransferRequest moneyTransferRequest = createRequest(receiverName, description, currency, amount, executionDate);
		HttpEntity<MoneyTransferRequest> requestEntity = new HttpEntity<>(moneyTransferRequest, headers);

		try {
			ResponseEntity<MoneyTransferResponse> response = restTemplate.postForEntity(transferMoneyEndpoint, requestEntity, MoneyTransferResponse.class, urlParams);
			return response.getBody();
		}catch (HttpClientErrorException ex){
			HttpStatus code = ex.getStatusCode();
			String message = ex.getMessage();
			throw new FabrickTestException(code, message);
		}

	}

	private MoneyTransferRequest createRequest(String receiverName, String description, String currency, String amount, String executionDate){
		MoneyTransferRequest output = new MoneyTransferRequest();
		output.setAmount(Integer.valueOf(amount));
		output.setCurrency(currency);

		Creditor creditor = new Creditor();
		creditor.setName(receiverName);
		Account accountCreditor = new Account();
		accountCreditor.setAccountCode("IT23A0336844430152923804660");
		accountCreditor.setBicCode("SELBIT2BXXX");
		creditor.setAccount(accountCreditor);

		TaxRelief taxRelief = new TaxRelief();
		taxRelief.setBeneficiaryType("NATURAL_PERSON");
		taxRelief.setCreditorFiscalCode("56258745832");
		NaturalPersonBeneficiary naturalPersonBeneficiary = new NaturalPersonBeneficiary();
		naturalPersonBeneficiary.setFiscalCode1("MRLFNC81L04A859L");
		taxRelief.setNaturalPersonBeneficiary(naturalPersonBeneficiary);

		output.setCreditor(creditor);
		output.setTaxRelief(taxRelief);
		output.setDescription(description);
		output.setExecutionDate(executionDate);
		return output;
	}

}
