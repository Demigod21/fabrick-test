package com.davide.falcone.fabricktest.model;

import lombok.Data;

@Data
public class TaxRelief{
	public String taxReliefId;
	public boolean isCondoUpgrade;
	public String creditorFiscalCode;
	public String beneficiaryType;
	public NaturalPersonBeneficiary naturalPersonBeneficiary;
	public LegalPersonBeneficiary legalPersonBeneficiary;
}
