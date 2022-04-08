package com.davide.falcone.fabricktest.model;

import lombok.Data;

@Data
public class AccountModel {

		private String accountId;
		private String iban;
		private String abiCode;
		private String cabCode;
		private String countryCode;
		private String internationalCin;
		private String nationalCin;
		private String account;
		private String alias;
		private String productName;
		private String holderName;
		private String activatedDate;
		private String currency;

}
