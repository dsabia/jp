package com.jp.super_simple.stock_market.service;

import java.math.BigDecimal;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.jp.super_simple.stock_market.domain.aggregator.StockAggregator;
import com.jp.super_simple.stock_market.domain.aggregator.TradeAggregator;
import com.jp.super_simple.stock_market.domain.model.Stock;
import com.jp.super_simple.stock_market.service.calculator.StockCalculationService;
import com.jp.super_simple.stock_market.service.calculator.TradeCollectionCalculatorService;

/**
 * Main service for trades.
 * @author Daniel
 *
 */
public class TradeService {

	private static final Logger log = Logger.getLogger(TradeService.class.getSimpleName());

	private StockCalculationService stockCalculationService;
	private TradeCollectionCalculatorService tradeCollectionCalculatorService;

	{
		stockCalculationService = new StockCalculationService();
		tradeCollectionCalculatorService = new TradeCollectionCalculatorService();
	}

	/**
	 * For given price, retrieve StockAggregator object, and calculate Dividend and P/E Ratio. 
	 * @param stock
	 * @param price
	 * @return
	 */
	public StockAggregator calculate(Stock stock, BigDecimal price) {
		StockAggregator stockAggregator = new StockAggregator(stock);

		BigDecimal dividendYeld = stockCalculationService.getDividendYeld(stock, price);
		stockAggregator.setDividendYeld(dividendYeld);

		BigDecimal peRatio = stockCalculationService.getPE_Ratio(stockAggregator, price);
		stockAggregator.setPeRatio(peRatio);

		return stockAggregator;
	}

	/**
	 * Calculate Volume Weight Stock Price for a collection of trade of the same stock.
	 * @param trades
	 * @return
	 */
	public BigDecimal calculateVolumeWeightStockPrice(Collection<TradeAggregator> trades) {
		if (trades.isEmpty()) {
			return BigDecimal.ZERO;
		}

		return tradeCollectionCalculatorService.calculateVolumeWeightStockPrice(trades);
	}

	/**
	 * Calculate Geometrical Mean from a generic collection of trades.
	 * @param trades
	 * @return
	 */
	public BigDecimal calculateGeometricalMean(Collection<TradeAggregator> trades) {
		if (trades.isEmpty()) {
			return BigDecimal.ZERO;
		}

		return tradeCollectionCalculatorService.calculateGeometricalMean(trades);
	}
}
