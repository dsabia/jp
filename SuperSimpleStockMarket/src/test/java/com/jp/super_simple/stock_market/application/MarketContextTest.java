package com.jp.super_simple.stock_market.application;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jp.super_simple.stock_market.domain.constant.STOCK_SYMB;
import com.jp.super_simple.stock_market.domain.constant.TRADE_INDICATOR;
import com.jp.super_simple.stock_market.domain.model.Stock;
import com.jp.super_simple.stock_market.domain.model.Trade;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:application-context.xml"})
public class MarketContextTest {

	public static final int T1_QUANTITY = 10;
	public static final double T1_PRICE = .5; 
	public static final STOCK_SYMB T1_STOCK_SYMB = STOCK_SYMB.ALE;
	public static final int T2_QUANTITY = 10;
	public static final double T2_PRICE = 1.5; 
	public static final int T3_QUANTITY = 10;
	public static final double T3_PRICE = 1; 
	public static final STOCK_SYMB T3_STOCK_SYMB = STOCK_SYMB.GIN;
	
	public static final double delta = .0000000001;
	
	@Autowired
	private MarketContext marketContext;

	@Test
	public void testCheckInitialization(){
		Assert.assertEquals(marketContext.getNumberOfTrades(), new Integer(0));
	}
	
	@Test
	public void testCreateTrade(){
		Trade t1 = createSimpleTradeT1();
		Assert.assertNotNull(t1);
		Assert.assertNotNull(t1.getStock());
	}
	
	@Test
	public void testRegisterTrade(){
		Trade t1 = createSimpleTradeT1();
		marketContext.registerTrade(t1);
		
		Assert.assertEquals(marketContext.getNumberOfTrades(), new Integer(1));
		Assert.assertEquals(marketContext.getNumberOfTrades(t1.getStock()), new Integer(1));
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testAllDividend(){
		Map<STOCK_SYMB, BigDecimal> mapExpectedValues = new HashMap<STOCK_SYMB, BigDecimal>();
		mapExpectedValues.put(STOCK_SYMB.TEA, new BigDecimal("0"));
		mapExpectedValues.put(STOCK_SYMB.POP, new BigDecimal("0.08"));
		mapExpectedValues.put(STOCK_SYMB.ALE, new BigDecimal("0.23"));
		mapExpectedValues.put(STOCK_SYMB.GIN, new BigDecimal("0.000004"));
		mapExpectedValues.put(STOCK_SYMB.JOE, new BigDecimal("0.13"));
		BigDecimal price = new BigDecimal("100");
		
		for(STOCK_SYMB symb : STOCK_SYMB.values()){
			BigDecimal dividend = marketContext.reportDividend(symb, price);
			
			BigDecimal expected = mapExpectedValues.get(symb);
			Assert.assertEquals(expected.doubleValue(), dividend.doubleValue(), delta);
		}
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testAllPERatio(){
		Map<STOCK_SYMB, BigDecimal> mapExpectedValues = new HashMap<STOCK_SYMB, BigDecimal>();
		mapExpectedValues.put(STOCK_SYMB.TEA, new BigDecimal("0"));
		mapExpectedValues.put(STOCK_SYMB.POP, new BigDecimal("1.25E+3"));
		mapExpectedValues.put(STOCK_SYMB.ALE, new BigDecimal("434.7826086956522"));
		mapExpectedValues.put(STOCK_SYMB.GIN, new BigDecimal("2.5E+7"));
		mapExpectedValues.put(STOCK_SYMB.JOE, new BigDecimal("769.2307692307692"));
		BigDecimal price = new BigDecimal("100");
		
		for(STOCK_SYMB symb : STOCK_SYMB.values()){
			BigDecimal dividend = marketContext.reportPeRatio(symb, price);

			BigDecimal expected = mapExpectedValues.get(symb);
			Assert.assertEquals(expected.doubleValue(), dividend.doubleValue(), delta);
		}
	}
	
	@Test
	public void testVolumeWeightStockPrice(){
		populateMarket();
		Stock stock = marketContext.getStock(T1_STOCK_SYMB);
		BigDecimal vwsp = marketContext.reportVolumeWeightStockPrice(stock);
		BigDecimal expected = new BigDecimal("1");
		Assert.assertEquals(vwsp.doubleValue(), expected.doubleValue(), delta);
	}
	
	@Test
	public void testGeometricalMean(){
		populateMarket();
		BigDecimal gm = marketContext.reportGeometricalMean();
		BigDecimal expected = new BigDecimal("0.9085602964160698");
		Assert.assertEquals(gm.doubleValue(), expected.doubleValue(), delta);
	}
	
	private void populateMarket() {
		marketContext.registerTrade(createSimpleTradeT1());
		marketContext.registerTrade(createSimpleTradeT2());
		marketContext.registerTrade(createSimpleTradeT3());
	}

	private Trade createSimpleTradeT1() {
		Trade t1 = marketContext.simpleGenerateTrade(T1_QUANTITY, T1_PRICE, T1_STOCK_SYMB, TRADE_INDICATOR.BUY);
		return t1;
	}
	private Trade createSimpleTradeT2() {
		Trade t1 = marketContext.simpleGenerateTrade(T2_QUANTITY, T2_PRICE, T1_STOCK_SYMB, TRADE_INDICATOR.BUY);
		return t1;
	}
	private Trade createSimpleTradeT3() {
		Trade t1 = marketContext.simpleGenerateTrade(T3_QUANTITY, T3_PRICE, T3_STOCK_SYMB, TRADE_INDICATOR.BUY);
		return t1;
	}
}