package pl.videopoint.springlessons.financeassistant.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

public class RateConversionService {
	@Autowired
	private CurrencyRateProvider rateProvider;
	
	//tu podajemy gdzie dowiązujemy beana!! tak ma ponoć być ale mozna też wyżej jak w JEE!!!!
	public void setRateProvider(CurrencyRateProvider rateProvider){
		this.rateProvider = rateProvider;
	}

	public BigDecimal convertAmount(BigDecimal amount, String currency) {
		BigDecimal rate = rateProvider.getRate(currency, new Date());
		System.out.println("RateConversionService: Obliczam kwote");
		BigDecimal result = amount.multiply(rate).setScale(2, RoundingMode.HALF_UP);
		return result;
	}

}
