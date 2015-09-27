package com.jp.super_simple.stock_market.domain.model;

import java.math.BigDecimal;

import com.jp.super_simple.stock_market.domain.constant.STOCK_SYMB;
import com.jp.super_simple.stock_market.domain.constant.STOCK_TYPE;

/**
 * Stock model object.
 * @author Daniel
 *
 */
public class Stock {
	private STOCK_SYMB symb;
	private STOCK_TYPE type;
	private BigDecimal lastDividend;
	private BigDecimal fixedDividend;
	private BigDecimal parValue;

	public Stock(STOCK_SYMB symb, STOCK_TYPE type, BigDecimal lastDividend, BigDecimal fixedDividend, BigDecimal parValue) {
		super();
		this.symb = symb;
		this.type = type;
		this.lastDividend = lastDividend;
		this.fixedDividend = fixedDividend;
		this.parValue = parValue;
	}

	public STOCK_SYMB getSymb() {
		return symb;
	}

	public void setSymb(STOCK_SYMB symb) {
		this.symb = symb;
	}

	public STOCK_TYPE getType() {
		return type;
	}

	public void setType(STOCK_TYPE type) {
		this.type = type;
	}

	public BigDecimal getLastDividend() {
		return lastDividend;
	}

	public void setLastDividend(BigDecimal lastDividend) {
		this.lastDividend = lastDividend;
	}

	public BigDecimal getParValue() {
		return parValue;
	}

	public void setParValue(BigDecimal parValue) {
		this.parValue = parValue;
	}

	public BigDecimal getFixedDividend() {
		return fixedDividend;
	}

	public void setFixedDividend(BigDecimal fixedDividend) {
		this.fixedDividend = fixedDividend;
	}
	
	@Override
	public String toString() {
		return symb + " ["+type+"] ";
	}
}
