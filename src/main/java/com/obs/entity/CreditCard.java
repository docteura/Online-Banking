package com.obs.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CreditCard {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cardId;

	private String name;
	private String email;
	private String cardNumber;
	private String cardType;
	private String bankName;
	private String cvv;
	private String description;
	private Date date;
	private Long userId;

	private BigDecimal totalCreditLimit;
	private BigDecimal availableCredit;
	private BigDecimal withdrwalCredit;
	private BigDecimal loanAvailable;

	private boolean status;
	private String incrementFlag;

	public Long getCardId() {
		return cardId;
	}

	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public BigDecimal getTotalCreditLimit() {
		return totalCreditLimit;
	}

	public void setTotalCreditLimit(BigDecimal totalCreditLimit) {
		this.totalCreditLimit = totalCreditLimit;
	}

	public BigDecimal getAvailableCredit() {
		return availableCredit;
	}

	public void setAvailableCredit(BigDecimal availableCredit) {
		this.availableCredit = availableCredit;
	}

	public BigDecimal getWithdrwalCredit() {
		return withdrwalCredit;
	}

	public void setWithdrwalCredit(BigDecimal withdrwalCredit) {
		this.withdrwalCredit = withdrwalCredit;
	}

	public BigDecimal getLoanAvailable() {
		return loanAvailable;
	}

	public void setLoanAvailable(BigDecimal loanAvailable) {
		this.loanAvailable = loanAvailable;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getIncrementFlag() {
		return incrementFlag;
	}

	public void setIncrementFlag(String incrementFlag) {
		this.incrementFlag = incrementFlag;
	}

	@Override
	public String toString() {
		return "CreditCard [cardId=" + cardId + ", name=" + name + ", email=" + email + ", cardNumber=" + cardNumber
				+ ", cardType=" + cardType + ", bankName=" + bankName + ", cvv=" + cvv + ", description=" + description
				+ ", date=" + date + ", userId=" + userId + ", totalCreditLimit=" + totalCreditLimit
				+ ", availableCredit=" + availableCredit + ", withdrwalCredit=" + withdrwalCredit + ", loanAvailable="
				+ loanAvailable + ", status=" + status + ", incrementFlag=" + incrementFlag + "]";
	}

}
