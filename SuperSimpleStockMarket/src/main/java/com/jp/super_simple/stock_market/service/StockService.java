package com.jp.super_simple.stock_market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jp.super_simple.stock_market.domain.constant.STOCK_SYMB;
import com.jp.super_simple.stock_market.domain.model.Stock;
import com.jp.super_simple.stock_market.repository.StockRepository;

/**
 * Stock service.
 * 
 * @author Daniel
 *
 */
@Service
public class StockService {

	@Autowired
	private StockRepository stockRepository;

	public Stock getStock(STOCK_SYMB symb) {
		return stockRepository.getStock(symb);
	}
	
}
