package com.jp.super_simple.stock_market.domain.aggregator;

import java.math.BigDecimal;

import com.jp.super_simple.stock_market.domain.model.Stock;

/**
 * Wrap Stock object with results of calculation, for Trade use. 
 * @author Daniel
 *
 */
public class StockAggregator {

	private final Stock stock;
	private BigDecimal dividendYeld;
	private BigDecimal peRatio;
	
	public StockAggregator(Stock stock) {
		this.stock = stock;
	}
	
	public Stock getStock() {
		return stock;
	}
	public BigDecimal getPeRatio() {
		return peRatio;
	}
	public void setPeRatio(BigDecimal peRatio) {
		this.peRatio = peRatio;
	}
	public BigDecimal getDividendYeld() {
		return dividendYeld;
	}
	public void setDividendYeld(BigDecimal dividendYeld) {
		this.dividendYeld = dividendYeld;
	}
	
	@Override
	public String toString() {
		return stock.toString() + " \tdividend:" + dividendYeld + " \tPEratio:"+peRatio;
	}
}
