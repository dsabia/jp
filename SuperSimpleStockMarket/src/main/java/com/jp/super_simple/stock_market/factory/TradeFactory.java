package com.jp.super_simple.stock_market.factory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jp.super_simple.stock_market.domain.constant.TRADE_INDICATOR;
import com.jp.super_simple.stock_market.domain.model.Stock;
import com.jp.super_simple.stock_market.domain.model.Trade;
import com.jp.super_simple.stock_market.repository.StockRepository;

/**
 * Generate trade with simplified API.
 * @author Daniel
 *
 */
@Component
public class TradeFactory {
	
	@Autowired
	private StockRepository stockRepository;
	
	public Trade generateTrade(BigInteger quantity, BigDecimal price, Stock stock, TRADE_INDICATOR indicator) {
		return new Trade(new Date(), quantity, price, stock, indicator);
	}
}
