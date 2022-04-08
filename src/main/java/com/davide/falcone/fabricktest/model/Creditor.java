package com.davide.falcone.fabricktest.model;

import lombok.Data;

@Data
public class Creditor {
	public String name;
	public Account account;
	public Address address;
}
