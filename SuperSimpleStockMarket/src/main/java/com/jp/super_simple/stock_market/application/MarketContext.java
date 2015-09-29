package com.jp.super_simple.stock_market.application;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jp.super_simple.stock_market.domain.aggregator.StockAggregator;
import com.jp.super_simple.stock_market.domain.aggregator.TradeAggregator;
import com.jp.super_simple.stock_market.domain.constant.STOCK_SYMB;
import com.jp.super_simple.stock_market.domain.constant.TRADE_INDICATOR;
import com.jp.super_simple.stock_market.domain.model.Stock;
import com.jp.super_simple.stock_market.domain.model.Trade;
import com.jp.super_simple.stock_market.service.StockService;
import com.jp.super_simple.stock_market.service.TradeService;

/**
 * StateFull Object, because instance a cache, to simulate a context of the application.
 * 
 * @author Daniel
 * 
 */
@Component
@Scope("prototype")
public class MarketContext {

	private static final Logger log = Logger.getLogger(MarketContext.class.getSimpleName());
	
	@Autowired
	private TradeService tradeService;
	@Autowired
	private StockService stockService;
	
	@Autowired
	private MarketCache marketCache;

	{
		log.debug("Initialization completed");
	}

	public Stock getStock(STOCK_SYMB symb) {
		return stockService.getStock(symb);
	}
	
	public StockAggregator getStockInfo(STOCK_SYMB symb, BigDecimal price){
		return stockService.calculateStockInfo(getStock(symb), price);
	}

	public Trade simpleGenerateTrade(int quantity, double price, STOCK_SYMB symb, TRADE_INDICATOR indicator) {
		Trade t = tradeService.generateTrade(quantity, price, symb, indicator);
		return t;
	}
	public Trade generateTrade(BigInteger quantity, BigDecimal price, Stock stock, TRADE_INDICATOR indicator) {
		Trade t = tradeService.generateTrade(quantity, price, stock, indicator);
		return t;
	}

	public void registerTrade(Trade trade) {
		TradeAggregator tradeAggregator = tradeService.buildTradePrecalculated(trade);
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