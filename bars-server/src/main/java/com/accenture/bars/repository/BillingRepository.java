package com.accenture.bars.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.accenture.bars.domain.Account;
import com.accenture.bars.domain.Billing;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Long>{
	List<Billing> findByBillingId(int billingid);
}
