package com.jp.super_simple.stock_market.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jp.super_simple.stock_market.domain.aggregator.StockAggregator;
import com.jp.super_simple.stock_market.domain.aggregator.TradeAggregator;
import com.jp.super_simple.stock_market.domain.constant.STOCK_SYMB;
import com.jp.super_simple.stock_market.domain.constant.TRADE_INDICATOR;
import com.jp.super_simple.stock_market.domain.model.Stock;
import com.jp.super_simple.stock_market.domain.model.Trade;
import com.jp.super_simple.stock_market.factory.TradeAggregatorFactory;
import com.jp.super_simple.stock_market.factory.TradeFactory;
import com.jp.super_simple.stock_market.service.calculator.StockCalculationService;
import com.jp.super_simple.stock_market.service.calculator.TradeCollectionCalculatorService;

/**
 * Main service for trades.
 * @author Daniel
 *
 */
@Service
public class TradeService {

	private static final Logger log = Logger.getLogger(TradeService.class.getSimpleName());

	@Autowired
	private TradeCollectionCalculatorService tradeCollectionCalculatorService;
	@Autowired 
	private TradeFactory tradeFactory;
	@Autowired
	private TradeAggregatorFactory tradeAggregatorFactory;
	@Autowired
	private StockService stockService;
	@Autowired
	private StockCalculationService stockCalculationService;
	
	/**
	 * For given price, retrieve StockAggregator object then it store the calculate's value Dividend and P/E Ratio. 
	 * @param stock
	 * @param price
	 * @return
	 */
	public StockAggregator calculateStockInfo(Stock stock, BigDecimal price) {
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

	/**
	 * Generate Trade instance.
	 */
	public Trade generateTrade(int quantity, double price, STOCK_SYMB symb, TRADE_INDICATOR indicator) {
		Stock stock = stockService.getStock(symb);
		return tradeFactory.generateTrade(new BigInteger(""+quantity), new BigDecimal(price), stock, indicator);
	}

	/**
	 * Generate Trade instance.
	 */
	public Trade generateTrade(BigInteger quantity, BigDecimal price, Stock stock, TRADE_INDICATOR indicator) {
		return tradeFactory.generateTrade(quantity, price, stock, indicator);
	}

	/**
	 * Generate TradeAggregator instance.
	 */
	public TradeAggregator buildTradePrecalculated(Trade trade) {
		return tradeAggregatorFactory.buildTradePrecalculated(trade);
	}
}
