package com.davide.falcone.fabricktest.model;

import lombok.Data;

import java.util.List;

@Data
public class CashAccountResponse {
	private List<AccountModel> list;
}
