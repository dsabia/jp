package com.jp.super_simple.stock_market.domain.aggregator;

import java.math.BigDecimal;

import com.jp.super_simple.stock_market.domain.model.Trade;

/**
 * Wrap trade, Stock aggregator and some results of calculation, for
 * optimization proposal.
 * 
 * @author Daniel
 * 
 */
public class TradeAggregator {

	private Trade trade;
	private StockAggregator stockAggregator;
	private BigDecimal quantityMultPrice;

	public TradeAggregator(Trade trade, StockAggregator stockAggregator) {
		super();
		this.trade = trade;
		this.stockAggregator = stockAggregator;
	}

	public Trade getTrade() {
		return trade;
	}

	public void setTrade(Trade trade) {
		this.trade = trade;
	}

	public StockAggregator getStockAggregator() {
		return stockAggregator;
	}

	public void setStockAggregator(StockAggregator stockAggregator) {
		this.stockAggregator = stockAggregator;
	}

	public BigDecimal getQuantityMultPrice() {
		return quantityMultPrice;
	}

	public void setQuantityMultPrice(BigDecimal quantityMultPrice) {
		this.quantityMultPrice = quantityMultPrice;
	}

	@Override
	public String toString() {
		return trade.toString();
	}
}