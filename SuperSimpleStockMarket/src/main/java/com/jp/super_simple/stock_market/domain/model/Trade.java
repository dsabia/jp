package com.jp.super_simple.stock_market.domain.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import com.jp.super_simple.stock_market.domain.constant.TRADE_INDICATOR;

/**
 * Trade model object.
 * @author Daniel
 *
 */
public class Trade {
	private Date timestamp;
	private BigInteger quantity;
	private BigDecimal price;
	private Stock stock;
	private TRADE_INDICATOR indicator;

	public Trade(Date timestamp, BigInteger quantity, BigDecimal price, Stock stock, TRADE_INDICATOR indicator) {
		super();
		this.timestamp = timestamp;
		this.quantity = quantity;
		this.price = price;
		this.stock = stock;
		this.indicator = indicator;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public BigInteger getQuantity() {
		return quantity;
	}

	public void setQuantity(BigInteger quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public TRADE_INDICATOR getIndicator() {
		return indicator;
	}

	public void setIndicator(TRADE_INDICATOR indicator) {
		this.indicator = indicator;
	}
	
	@Override
	public String toString() {
		return stock.toString() + " ["+timestamp+"]" + indicator + " \tq:" + quantity + " \tp:"+price;
	}
}