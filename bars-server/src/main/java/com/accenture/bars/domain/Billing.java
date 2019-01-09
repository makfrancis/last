package com.accenture.bars.domain;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;



import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Billing Entity
 *
 */
@Entity
public class Billing {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="billing_id")
	private int billingId;
	
	@Column(name="billing_cycle")
	private int billingCycle;
	
	@Column(name="billing_month")
	private String billingMonth;
	
	@Column(name="amount")
	private double amount;

	//@ManyToOne(cascade = CascadeType.ALL)
	@ManyToOne
	@JoinColumn(name = "account_id")
    @JsonBackReference
	private Account account;

	@Column(name="start_date")
	private Date startDate;
	
	@Column(name="end_date")
	private Date endDate;
	
	@Column(name="last_edited")
	private String lastEdited;

	public Billing() {
	}

	public Billing(int billingCycle, String billingMonth, double amount,
			Account account, Date startDate, Date endDate, String lastEdited) {
		this.billingCycle = billingCycle;
		this.billingMonth = billingMonth;
		this.amount = amount;
		this.account = account;
		this.startDate = startDate;
		this.endDate = endDate;
		this.lastEdited = lastEdited;
	}
	
	

	public int getBillingId() {
		return billingId;
	}

	public void setBillingId(int billingId) {
		this.billingId = billingId;
	}

	public int getBillingCycle() {
		return billingCycle;
	}

	public void setBillingCycle(int billingCycle) {
		this.billingCycle = billingCycle;
	}

	public String getBillingMonth() {
		return billingMonth;
	}

	public void setBillingMonth(String billingMonth) {
		this.billingMonth = billingMonth;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getLastEdited() {
		return lastEdited;
	}

	public void setLastEdited(String lastEdited) {
		this.lastEdited = lastEdited;
	}

}