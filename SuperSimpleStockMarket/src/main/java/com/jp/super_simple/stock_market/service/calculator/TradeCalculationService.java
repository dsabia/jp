package com.jp.super_simple.stock_market.service.calculator;

import java.math.BigDecimal;

import com.jp.super_simple.stock_market.domain.model.Trade;

/**
 * Execute calculations specific for Trade.
 * @author Daniel
 *
 */
public class TradeCalculationService {

	/**
	 * Multiply Price for Quantity.
	 */
	public BigDecimal getPriceMultQuantity(Trade trade) {
		return trade.getPrice().multiply(new BigDecimal(trade.getQuantity()));
	}
	
}
