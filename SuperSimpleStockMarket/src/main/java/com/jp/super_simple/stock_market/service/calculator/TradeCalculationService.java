package com.jp.super_simple.stock_market.service.calculator;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.jp.super_simple.stock_market.domain.model.Trade;

/**
 * Execute calculations specific for Trade.
 * @author Daniel
 *
 */
@Component
public class TradeCalculationService {

	/**
	 * Multiply Price for Quantity.
	 */
	public BigDecimal getPriceMultQuantity(Trade trade) {
		return trade.getPrice().multiply(new BigDecimal(trade.getQuantity()));
	}
	
}
