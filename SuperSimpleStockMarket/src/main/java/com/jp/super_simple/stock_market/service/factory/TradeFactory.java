package com.jp.super_simple.stock_market.service.factory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import com.jp.super_simple.stock_market.domain.constant.STOCK_SYMB;
import com.jp.super_simple.stock_market.domain.constant.TRADE_INDICATOR;
import com.jp.super_simple.stock_market.domain.model.Stock;
import com.jp.super_simple.stock_market.domain.model.Trade;
import com.jp.super_simple.stock_market.repository.StockRepository;

/**
 * Generate trade with simplified API.
 * @author Daniel
 *
 */
public class TradeFactory {
	
	private StockRepository stockRepository;
	{
		stockRepository = new StockRepository();
	}
	
	public Trade generateTrade(BigInteger quantity, BigDecimal price, Stock stock, TRADE_INDICATOR indicator) {
		return new Trade(new Date(), quantity, price, stock, indicator);
	}

	public Trade generateTrade(int quantity, double price, STOCK_SYMB symb, TRADE_INDICATOR indicator) {
		Stock stock = stockRepository.getStock(symb);
		return new Trade(new Date(), new BigInteger(""+quantity), new BigDecimal(price), stock, indicator);
	}
}
