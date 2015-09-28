package com.jp.super_simple.stock_market.service.factory;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jp.super_simple.stock_market.domain.aggregator.StockAggregator;
import com.jp.super_simple.stock_market.domain.aggregator.TradeAggregator;
import com.jp.super_simple.stock_market.domain.model.Stock;
import com.jp.super_simple.stock_market.domain.model.Trade;
import com.jp.super_simple.stock_market.service.TradeService;
import com.jp.super_simple.stock_market.service.calculator.TradeCalculationService;

/**
 * Build TradeAggregator structure of elements with eager calculation.
 * @author Daniel
 *
 */
@Component
public class TradeAggregatorFactory {

	@Autowired
	private TradeService tradeService;
	@Autowired
	private TradeCalculationService tradeCalculationService;
	
	/**
	 * Execute eager calculation such trade.
	 * @param trade
	 * @return
	 */
	public TradeAggregator buildTradePrecalculated(Trade trade) {
		StockAggregator stockAggregator = getStockForTrade(trade.getStock(), trade.getPrice());
		TradeAggregator tradeAggregator = new TradeAggregator(trade, stockAggregator);
		
		BigDecimal priceMultQuantity = tradeCalculationService.getPriceMultQuantity(tradeAggregator.getTrade());
		tradeAggregator.setQuantityMultPrice(priceMultQuantity);
		
		return tradeAggregator;
	}
	
	/**
	 * Execute eager calculation for Stock.
	 * @param stock
	 * @param price
	 * @return
	 */
	private StockAggregator getStockForTrade(Stock stock, BigDecimal price) {
		StockAggregator stockAggregator = tradeService.calculate(stock, price);
		return stockAggregator;
	}
	
}
