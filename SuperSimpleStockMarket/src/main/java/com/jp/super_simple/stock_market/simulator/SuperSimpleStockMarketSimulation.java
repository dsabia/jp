package com.jp.super_simple.stock_market.simulator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jp.super_simple.stock_market.application.MarketContext;
import com.jp.super_simple.stock_market.domain.constant.STOCK_SYMB;
import com.jp.super_simple.stock_market.domain.constant.TRADE_INDICATOR;
import com.jp.super_simple.stock_market.domain.model.Stock;
import com.jp.super_simple.stock_market.domain.model.Trade;

public class SuperSimpleStockMarketSimulation {

	private static final Logger log = Logger.getLogger(SuperSimpleStockMarketSimulation.class.getSimpleName());
	
	public interface SUPER_SIMPLE_EXAMPLE_LIMIT {
		int MAX_QUANTITY = 10000;
		int MAX_PRICE = 100;
	}
	
	private static MarketContext market;

	public static void main(String[] args) throws InterruptedException {
		BasicConfigurator.configure();
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context.xml");
		market = applicationContext.getBean(MarketContext.class);
		
		SuperSimpleStockMarketSimulation simulation = new SuperSimpleStockMarketSimulation();
		
		log.info("*****");
		log.info("Start simulation (in SECONDS and not MINUTES):");
		log.info(" - 15 iterations of 2 second, 30 second in total");
		log.info(" - stock cache expires every 15 seconds");
		// 15 iterations of 2 minutes, 30 minutes in total
		for (int j = 0; j < 15; j++){
			log.info(" - add new 10 Trades randomly");
			// add new 10 Trades and calculate index every 2 minutes
			for (int i = 0; i < 10; i++) {
				simulation.generateandRegisterRandomTrade();
			}
			
			log.info(" - generate report: ");
			// values calculated on trade actives in the last 15 minutes
			simulation.reportVolumeWeightStockPrice();
			simulation.reportGeometricalMean();
			
			Thread.sleep(1000*2);
		}
		log.info("Simulation completed succesfully.");
	}

	public void generateandRegisterRandomTrade() {
		Trade trade = generateRandomTrade();
		market.registerTrade(trade);
	}

	public void reportVolumeWeightStockPrice() {
		for (STOCK_SYMB symb : STOCK_SYMB.values()) {
			Stock stock = market.getStock(symb);
			BigDecimal v = market.reportVolumeWeightStockPrice(stock);
			Integer numberOfTrades = market.getNumberOfTrades(stock);
			log.info("VolumeWeightStockPrice [" + symb + "]: " + v + " on " + numberOfTrades + " trades" );
		}
	}

	/**
	 * Calculate geometrical mean
	 */
	public void reportGeometricalMean() {
		BigDecimal v = market.reportGeometricalMean();
		Integer numberOfTrades = market.getNumberOfTrades();
		log.info("Geometrical Mean: "+v + " on " + numberOfTrades + " trades.");
	}

	/**
	 * Generate Random Trade
	 * @return
	 */
	private Trade generateRandomTrade() {
		Random random = new Random();
		BigInteger quantity = new BigDecimal(random.nextDouble() * SUPER_SIMPLE_EXAMPLE_LIMIT.MAX_QUANTITY).toBigInteger().add(BigInteger.ONE);
		BigDecimal price = new BigDecimal(random.nextDouble() * SUPER_SIMPLE_EXAMPLE_LIMIT.MAX_PRICE);

		// load random stock
		int indexStock = new BigDecimal(random.nextDouble() * STOCK_SYMB.values().length).intValue();
		Stock stock = market.getStock(STOCK_SYMB.values()[indexStock]);

		TRADE_INDICATOR indicator = random.nextBoolean() ? TRADE_INDICATOR.BUY : TRADE_INDICATOR.SELL;
		return market.generateTrade(quantity, price, stock, indicator);
	}

}
