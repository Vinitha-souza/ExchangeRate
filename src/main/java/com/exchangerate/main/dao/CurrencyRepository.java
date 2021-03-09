package com.exchangerate.main.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exchangerate.main.model.Currency;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {

	Currency findByname(String upperCase);

}
