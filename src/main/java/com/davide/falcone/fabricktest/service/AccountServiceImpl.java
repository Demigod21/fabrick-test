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
import com.davide.falcone.fabricktest.model.TransactionResponse;
import com.davide.falcone.fabricktest.repo.TransactionDTO;
import com.davide.falcone.fabricktest.repo.TransactionRepo;
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
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService{

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	ConfigProperties configProperties;

	@Autowired
	TransactionRepo transactionRepo;

	@Override
	public BigDecimal getAccountBalance(String accountId) throws FabrickTestException {
		String accountBalanceEndpoint = configProperties.getAccountBalanceEndpoint();
		// URI (URL) parameters
		Map<String, String> urlParams = new HashMap<>();
		urlParams.put("accountId", accountId);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Auth-Schema", "S2S");
		headers.add("Api-Key", "FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP");

		HttpEntity requestEntity = new HttpEntity<>(headers);

		try {
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

		}catch (HttpClientErrorException ex){
			HttpStatus code = ex.getStatusCode();
			String message = ex.getMessage();
			throw new FabrickTestException(code, message);
		}
	}

	@Override
	public TransactionPayloadResponse getListTransaction(String accountId, String fromAccountingDate, String toAccountingDate) throws FabrickTestException {
		String transactionEndpoint = configProperties.getTransactionEndpoint();
		// URI (URL) parameters
		Map<String, String> urlParams = new HashMap<>();
		urlParams.put("accountId", accountId);
		// Query parameters
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(transactionEndpoint)
			// Add query parameter
			.queryParam("fromAccountingDate", LocalDate.parse(fromAccountingDate))
			.queryParam("toAccountingDate", LocalDate.parse(toAccountingDate));

		HttpHeaders headers = new HttpHeaders();
		headers.add("Auth-Schema", "S2S");
		headers.add("Api-Key", "FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP");

		HttpEntity requestEntity = new HttpEntity<>(headers);

		try {
			ResponseEntity<TransactionResponse> response = restTemplate.exchange(builder.buildAndExpand(urlParams).toUri() , HttpMethod.GET, requestEntity, TransactionResponse.class);
			TransactionPayloadResponse output = Optional.ofNullable(response)
				.map(HttpEntity::getBody)
				.map(TransactionResponse::getPayload)
				.orElse(null);

			return output;

		}catch (HttpClientErrorException ex){
			HttpStatus code = ex.getStatusCode();
			String message = ex.getMessage();
			throw new FabrickTestException(code, message);
		}
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
			TransactionDTO transactionDTO = createDTO(moneyTransferRequest);
			transactionRepo.save(transactionDTO);
			return response.getBody();
		}catch (HttpClientErrorException ex){
			HttpStatus code = ex.getStatusCode();
			String message = ex.getMessage();
			TransactionDTO transactionDTO = createDTO(moneyTransferRequest);
			String messageCut = message.length()>250? message.substring(0, 249) : message;
			transactionDTO.setErrorMessage(messageCut);
			transactionRepo.save(transactionDTO);
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

	private TransactionDTO createDTO(MoneyTransferRequest moneyTransferRequest){
		TransactionDTO transactionDTO = new TransactionDTO();
		transactionDTO.setAmount(Optional.ofNullable(moneyTransferRequest)
			.map(MoneyTransferRequest::getAmount)
			.orElse(0));
		transactionDTO.setTransactionDate(LocalDate.now());
		UUID uuid = UUID.randomUUID();
		transactionDTO.setUuid(uuid.toString());

		return transactionDTO;
	}

}
