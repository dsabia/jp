package com.jp.super_simple.stock_market.application;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.jp.super_simple.stock_market.domain.aggregator.TradeAggregator;
import com.jp.super_simple.stock_market.domain.model.Stock;
import com.jp.super_simple.stock_market.repository.StockRepository;

/**
 * Contains 2 different cache, for all Trades and for trades grouped by stock.
 *  
 * @author Daniel
 *
 */
public class MarketCache {

	public interface CacheLimit {
		int DELAY_QUANTITY = 15; // 15 minutes
		TimeUnit CACHE_TIME_UNIT = TimeUnit.SECONDS; //MINUTES;
	}
	
	private LoadingCache<String, TradeAggregator> cacheAllTrades;
	private Map<Stock, LoadingCache<String, TradeAggregator>> cacheAllTradeLines;
	

	
	private StockRepository stockRepository;
	{

		stockRepository = new StockRepository();
		cacheAllTrades = CacheBuilder.newBuilder().build(new CacheLoader<String, TradeAggregator>() {
			@Override
			public TradeAggregator load(String arg0) throws Exception {
				return null;
			}
		});

		cacheAllTradeLines = new HashMap<Stock, LoadingCache<String,TradeAggregator>>();
		for (Stock stock : stockRepository.getAllStock()) {
			LoadingCache<String, TradeAggregator> cacheForStockSymb = CacheBuilder.newBuilder().expireAfterWrite(CacheLimit.DELAY_QUANTITY, CacheLimit.CACHE_TIME_UNIT)
					.build(new CacheLoader<String, TradeAggregator>() {
						@Override
						public TradeAggregator load(String arg0) throws Exception {
							return null;
						}
					});
			cacheAllTradeLines.put(stock, cacheForStockSymb);
		}
	}

	public void addTrade(TradeAggregator t) {
		cacheAllTrades.put(t.toString(), t);
		cacheAllTradeLines.get(t.getTrade().getStock()).put(t.toString(), t);
	}

	public Collection<TradeAggregator> getAllTrades() {
		return cacheAllTrades.asMap().values();
	}

	public Collection<TradeAggregator> getAllTrades(Stock stock) {
		return cacheAllTradeLines.get(stock).asMap().values();
	}

}
