package com.broadridge.mbse.pdfextract.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@JsonIgnoreProperties(ignoreUnknown=true)
public class PMBRKRecord {
	
	private String cusip;
	private String accountNo;
	private String settleMent;
	private String partNo;
	private String securityDesc;
	private String quantity;
	private String amount;
	private String idControl;
	private String tagNo;
	public String getCusip() {
		return cusip;
	}
	public void setCusip(String cusip) {
		this.cusip = cusip;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getSettleMent() {
		return settleMent;
	}
	public void setSettleMent(String settleMent) {
		this.settleMent = settleMent;
	}
	public String getPartNo() {
		return partNo;
	}
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
	public String getSecurityDesc() {
		return securityDesc;
	}
	public void setSecurityDesc(String securityDesc) {
		this.securityDesc = securityDesc;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getIdControl() {
		return idControl;
	}
	public void setIdControl(String idControl) {
		this.idControl = idControl;
	}
	public String getTagNo() {
		return tagNo;
	}
	public void setTagNo(String tagNo) {
		this.tagNo = tagNo;
	}
	
	

}
