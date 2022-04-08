package com.davide.falcone.fabricktest.service;

import com.davide.falcone.fabricktest.config.BeanConfiguration;
import com.davide.falcone.fabricktest.exception.FabrickTestException;
import com.davide.falcone.fabricktest.model.AccountBalancePayloadResponse;
import com.davide.falcone.fabricktest.model.AccountBalanceResponse;
import com.davide.falcone.fabricktest.model.MoneyTransferPayloadResponse;
import com.davide.falcone.fabricktest.model.MoneyTransferResponse;
import com.davide.falcone.fabricktest.model.Transaction;
import com.davide.falcone.fabricktest.model.TransactionPayloadResponse;
import com.davide.falcone.fabricktest.model.TransactionResponse;
import com.davide.falcone.fabricktest.repo.TransactionRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BeanConfiguration.class)
class AccountServiceTest {

	@Autowired
	AccountService accountService;

	@Autowired
	RestTemplate restTemplate;

	@Mock
	TransactionRepo transactionRepo;

	private MockRestServiceServer mockServer;
	private ObjectMapper mapper = new ObjectMapper();

	@BeforeEach
	public void init() {
		mockServer = MockRestServiceServer.createServer(restTemplate);
	}

	@Test
	void getAccountBalance() throws JsonProcessingException, URISyntaxException, FabrickTestException {

		AccountBalanceResponse accountBalanceResponse = new AccountBalanceResponse();
		AccountBalancePayloadResponse accountBalancePayloadResponse = new AccountBalancePayloadResponse();
		BigDecimal balanceMock = new BigDecimal(5);
		accountBalancePayloadResponse.setBalance(balanceMock);
		accountBalanceResponse.setPayload(accountBalancePayloadResponse);

		mockServer.expect(ExpectedCount.once(),
				requestTo(new URI("https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/123/balance")))
			.andExpect(method(HttpMethod.GET))
			.andRespond(withStatus(HttpStatus.OK)
				.contentType(MediaType.APPLICATION_JSON)
				.body(mapper.writeValueAsString(accountBalanceResponse))
			);

		BigDecimal balance = accountService.getAccountBalance("123");

		Assertions.assertEquals(balance, balanceMock);
	}

	@Test
	void getListTransaction() throws URISyntaxException, JsonProcessingException, FabrickTestException {

		String mockTransactionId = "1234";
		TransactionResponse transactionResponse = new TransactionResponse();
		TransactionPayloadResponse transactionPayloadResponse = new TransactionPayloadResponse();
		Transaction transaction = new Transaction();
		transaction.setTransactionId(mockTransactionId);
		List<Transaction> list = new ArrayList<>();
		list.add(transaction);
		transactionPayloadResponse.setList(list);
		transactionResponse.setPayload(transactionPayloadResponse);

		mockServer.expect(ExpectedCount.once(),
				requestTo(new URI("https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/123/transactions?fromAccountingDate=2020-01-01&toAccountingDate=2020-12-01")))
			.andExpect(method(HttpMethod.GET))
			.andRespond(withStatus(HttpStatus.OK)
				.contentType(MediaType.APPLICATION_JSON)
				.body(mapper.writeValueAsString(transactionResponse))
			);

		TransactionPayloadResponse transactionPayloadResponseTest = accountService.getListTransaction("123", "2020-01-01", "2020-12-01");
		String transactionId = Optional.ofNullable(transactionPayloadResponseTest)
			.map(TransactionPayloadResponse::getList)
			.map(x->x.get(0))
			.map(Transaction::getTransactionId)
			.orElse("");
		Assertions.assertEquals(mockTransactionId, transactionId);
	}

	@Test
	void transferMoney() throws URISyntaxException, JsonProcessingException, FabrickTestException {

		Mockito.when(transactionRepo.save(any())).thenReturn(null);
		MoneyTransferResponse moneyTransferResponse = new MoneyTransferResponse();
		MoneyTransferPayloadResponse moneyTransferPayloadResponse = new MoneyTransferPayloadResponse();
		String mockDescription = "mockDescription";
		moneyTransferPayloadResponse.setDescription(mockDescription);
		moneyTransferResponse.setPayload(moneyTransferPayloadResponse);

		mockServer.expect(ExpectedCount.once(),
				requestTo(new URI("https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/123/payments/money-transfers")))
			.andExpect(method(HttpMethod.POST))
			.andRespond(withStatus(HttpStatus.OK)
				.contentType(MediaType.APPLICATION_JSON)
				.body(mapper.writeValueAsString(moneyTransferResponse))
			);

		MoneyTransferResponse moneyTransferResponseTest = accountService.transferMoney("123", mockDescription, "EUR",
			"50", "2019-01-04", "123");
		String description = Optional.ofNullable(moneyTransferResponseTest)
			.map(MoneyTransferResponse::getPayload)
			.map(MoneyTransferPayloadResponse::getDescription)
			.orElse("");

		Assertions.assertEquals(mockDescription, description);

	}
}