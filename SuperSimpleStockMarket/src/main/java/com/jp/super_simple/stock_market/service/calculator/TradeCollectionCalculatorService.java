package com.jp.super_simple.stock_market.service.calculator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.jp.super_simple.stock_market.domain.aggregator.TradeAggregator;

/**
 * Execute calculations for group of trades.
 * @author Daniel
 *
 */
public class TradeCollectionCalculatorService {
	
	private static final Logger log = Logger.getLogger(TradeCollectionCalculatorService.class.getSimpleName());
	
	/**
	 * Calculate Volume Weight Stock Price as formula.
	 * @param trades
	 * @return
	 */
	public BigDecimal calculateVolumeWeightStockPrice(Collection<TradeAggregator> trades) {
		BigDecimal allPriceMultQuantity = BigDecimal.ZERO;
		BigInteger allQuantities = BigInteger.ZERO;

		for(TradeAggregator tradeAggregator : trades){
			BigInteger quantity = tradeAggregator.getTrade().getQuantity();
			BigDecimal priceMultQuantity = tradeAggregator.getQuantityMultPrice();

			allQuantities = allQuantities.add(quantity);
			allPriceMultQuantity = allPriceMultQuantity.add(priceMultQuantity);
		}
		
		log.debug("allPriceMultQuantity:"+allPriceMultQuantity);
		log.debug("allQuantities:"+allQuantities);
		return allPriceMultQuantity.divide(new BigDecimal(allQuantities), MathContext.DECIMAL64);
	}
	
	/**
	 * Calculate geometrical mean of list of trades.
	 * @param trades
	 * @return
	 */
	public BigDecimal calculateGeometricalMean(Collection<TradeAggregator> trades) {
		BigDecimal allPriceMultQuantity = BigDecimal.ONE;
		Integer numberOfTrades = 0;
		
		for(TradeAggregator tradeAggregator : trades){
			BigDecimal price = tradeAggregator.getTrade().getPrice();

			allPriceMultQuantity = allPriceMultQuantity.multiply(price);
			numberOfTrades ++;
		}
		log.debug("allPriceMultQuantity:"+allPriceMultQuantity);
		log.debug("numberOfTrades:"+numberOfTrades);
		return new BigDecimal(Math.pow(allPriceMultQuantity.doubleValue(), 1.0 / numberOfTrades));
	}
}
