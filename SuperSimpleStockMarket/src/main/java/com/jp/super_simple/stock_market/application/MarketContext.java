package com.jp.super_simple.stock_market.application;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.jp.super_simple.stock_market.domain.aggregator.StockAggregator;
import com.jp.super_simple.stock_market.domain.aggregator.TradeAggregator;
import com.jp.super_simple.stock_market.domain.constant.STOCK_SYMB;
import com.jp.super_simple.stock_market.domain.constant.TRADE_INDICATOR;
import com.jp.super_simple.stock_market.domain.model.Stock;
import com.jp.super_simple.stock_market.domain.model.Trade;
import com.jp.super_simple.stock_market.repository.StockRepository;
import com.jp.super_simple.stock_market.service.TradeService;
import com.jp.super_simple.stock_market.service.factory.TradeAggregatorFactory;
import com.jp.super_simple.stock_market.service.factory.TradeFactory;

/**
 * StateFull Object, because instance a cache, to simulate a context of the application.
 * 
 * @author Daniel
 * 
 */
public class MarketContext {

	private static final Logger log = Logger.getLogger(MarketContext.class.getSimpleName());
	
	private TradeService tradeService;
	private TradeFactory tradeFactory;
	private TradeAggregatorFactory tradeAggregatorFactory;
	private StockRepository stockRepository;
	
	private MarketCache marketCache;

	{
		BasicConfigurator.configure();
		
		tradeService = new TradeService();
		tradeFactory = new TradeFactory();
		tradeAggregatorFactory = new TradeAggregatorFactory();
		stockRepository = new StockRepository();
		
		marketCache = new MarketCache();

		log.debug("Initialization completed");
	}

	public Stock getStock(STOCK_SYMB symb) {
		return stockRepository.getStock(symb);
	}
	
	public BigDecimal reportDividend(STOCK_SYMB symb, BigDecimal price) {
		StockAggregator stockAggregator = tradeService.calculate(getStock(symb), price); 
		BigDecimal dividend = stockAggregator.getDividendYeld();
		log.debug("Dividend Yeld [" + symb + ","+price+"]:" + dividend);
		return dividend;
	}
	public BigDecimal reportPeRatio(STOCK_SYMB symb, BigDecimal price) {
		StockAggregator stockAggregator = tradeService.calculate(getStock(symb), price); 
		BigDecimal dividend = stockAggregator.getPeRatio();
		log.debug("P/E Ratio [" + symb + ","+price+"]:" + dividend);
		return dividend;
	}

	public Trade simpleGenerateTrade(int quantity, double price, STOCK_SYMB symb, TRADE_INDICATOR indicator) {
		Trade t = tradeFactory.generateTrade(quantity, price, symb, indicator);
		return t;
	}
	public Trade generateTrade(BigInteger quantity, BigDecimal price, Stock stock, TRADE_INDICATOR indicator) {
		Trade t = tradeFactory.generateTrade(quantity, price, stock, indicator);
		return t;
	}

	public void registerTrade(Trade trade) {
		TradeAggregator tradeAggregator = tradeAggregatorFactory.buildTradePrecalculated(trade);
		marketCache.addTrade(tradeAggregator);
		log.debug("Registered " + tradeAggregator );
	}

	public BigDecimal reportVolumeWeightStockPrice(Stock stock) {
		BigDecimal volumeWeightStockPrice = tradeService.calculateVolumeWeightStockPrice(marketCache.getAllTrades(stock));
		log.debug("Volume Weight Stock Price [" + stock + "]: " + volumeWeightStockPrice);
		return volumeWeightStockPrice;
	}

	public BigDecimal reportGeometricalMean() {
		BigDecimal geometricalMean = tradeService.calculateGeometricalMean(marketCache.getAllTrades());
		log.debug("Geometrical Mean: " + geometricalMean);
		return geometricalMean;
	}

	public Integer getNumberOfTrades() {
		return marketCache.getAllTrades().size();
	}
	public Integer getNumberOfTrades(Stock stock) {
		return marketCache.getAllTrades(stock).size();
	}
}