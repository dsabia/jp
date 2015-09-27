package com.jp.super_simple.stock_market.service.calculator;

import java.math.BigDecimal;
import java.math.MathContext;

import com.jp.super_simple.stock_market.domain.aggregator.StockAggregator;
import com.jp.super_simple.stock_market.domain.constant.STOCK_TYPE;
import com.jp.super_simple.stock_market.domain.model.Stock;

/**
 * Calculator based for Stock single item.
 * @author Daniel
 */
public class StockCalculationService {
	
	/**
	 * Calculate dividend for type of Stock.
	 */
	public BigDecimal getDividendYeld(Stock stock, BigDecimal price) {
		BigDecimal returnValue = null;
		if (STOCK_TYPE.Common.equals(stock.getType())) {
			returnValue = getCommonDividendYeld(stock, price);
		}
		if (STOCK_TYPE.Preferred.equals(stock.getType())) {
			returnValue = getPreferredDividendYeld(stock, price);
		}
		return returnValue;
	}

	/**
	 * P/E ratio for input Stock.
	 */
	public BigDecimal getPE_Ratio(Stock stock, BigDecimal price) {
		return getPE_Ratio(getDividendYeld(stock, price), price);
	}

	/**
	 * P/E ratio for input StockAggregator.
	 */
	public BigDecimal getPE_Ratio(StockAggregator stockAggregator, BigDecimal price) {
		return getPE_Ratio(stockAggregator.getDividendYeld(), price);
	}
	
	/**
	 * Operation P/E Ratio
	 */
	private BigDecimal getPE_Ratio(BigDecimal dividendYeld, BigDecimal price) {
		return dividendYeld.equals(BigDecimal.ZERO) ? BigDecimal.ZERO : price.divide(dividendYeld, MathContext.DECIMAL64);
	}
	
	/**
     * Operation Common Dividend Yeld
	 */
	private static BigDecimal getCommonDividendYeld(Stock stock, BigDecimal price) {
		BigDecimal lastDividend = stock.getLastDividend();
		return lastDividend.equals(BigDecimal.ZERO) ? BigDecimal.ZERO : lastDividend.divide(price, MathContext.DECIMAL64);
	}

	/**
	 * Operation Preferred Dividend Yeld
	 */
	private static BigDecimal getPreferredDividendYeld(Stock stock, BigDecimal price) {
		BigDecimal fixedDividend = stock.getParValue();
		BigDecimal parValue = stock.getParValue();
		boolean zeroValues = fixedDividend.equals(BigDecimal.ZERO) || parValue.equals(BigDecimal.ZERO); 
		return zeroValues ? BigDecimal.ZERO : (fixedDividend.multiply(parValue)).divide(price, MathContext.DECIMAL64);
	}
}
