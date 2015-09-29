package com.jp.super_simple.stock_market.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jp.super_simple.stock_market.domain.aggregator.StockAggregator;
import com.jp.super_simple.stock_market.domain.constant.STOCK_SYMB;
import com.jp.super_simple.stock_market.domain.model.Stock;
import com.jp.super_simple.stock_market.repository.StockRepository;
import com.jp.super_simple.stock_market.service.calculator.StockCalculationService;

/**
 * Stock service.
 * 
 * @author Daniel
 *
 */
@Service
public class StockService {

	@Autowired
	private StockRepository stockRepository;
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
	 * delegate operation from repository.
	 */
	public Stock getStock(STOCK_SYMB symb) {
		return stockRepository.getStock(symb);
	}
	
}
