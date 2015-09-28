package com.jp.super_simple.stock_market.repository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.TreeMap;

import org.springframework.stereotype.Repository;

import com.jp.super_simple.stock_market.domain.constant.STOCK_SYMB;
import com.jp.super_simple.stock_market.domain.constant.STOCK_TYPE;
import com.jp.super_simple.stock_market.domain.model.Stock;

/**
 * In memory repository, for manage one predefined configuration of Stock, unmodifible.
 * @author Daniel
 *
 */
@Repository
public class StockRepository {
	static TreeMap<STOCK_SYMB, Stock> stocks;
	
	{
		stocks = new TreeMap<STOCK_SYMB, Stock>();

		stocks.put(STOCK_SYMB.TEA, new Stock(STOCK_SYMB.TEA, STOCK_TYPE.Common, BigDecimal.ZERO, new BigDecimal(100), BigDecimal.ZERO));
		stocks.put(STOCK_SYMB.POP, new Stock(STOCK_SYMB.POP, STOCK_TYPE.Common, new BigDecimal(8), new BigDecimal(100), BigDecimal.ZERO));
		stocks.put(STOCK_SYMB.ALE, new Stock(STOCK_SYMB.ALE, STOCK_TYPE.Common, new BigDecimal(23), new BigDecimal(60), BigDecimal.ZERO));
		stocks.put(STOCK_SYMB.GIN, new Stock(STOCK_SYMB.GIN, STOCK_TYPE.Preferred, new BigDecimal(8), new BigDecimal(100), new BigDecimal(.02)));
		stocks.put(STOCK_SYMB.JOE, new Stock(STOCK_SYMB.JOE, STOCK_TYPE.Common, new BigDecimal(13), new BigDecimal(250), BigDecimal.ZERO));
	}
	
	/**
	 * Retrieve Stock object
	 * @param symb
	 * @return
	 */
	public Stock getStock(STOCK_SYMB symb) {
		return stocks.get(symb);
	}
	
	/**
	 * Retrieve all Stocks.
	 * @return
	 */
	public Collection<Stock> getAllStock() {
		return stocks.values();
	}
}
