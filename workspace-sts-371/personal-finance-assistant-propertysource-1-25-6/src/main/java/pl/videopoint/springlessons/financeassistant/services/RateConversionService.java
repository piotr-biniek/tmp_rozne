package pl.videopoint.springlessons.financeassistant.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
//import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
@Service 
public class RateConversionService {
	@Autowired
	private CurrencyRateProvider rateProvider;

	@Autowired(required = false)
	//@Qualifier("secondPprecProw")
	private Map<String, RoundPrecisionProvider> precisionProviders;

	// tu podajemy gdzie dowiązujemy beana!! tak ma ponoć być ale mozna też
	// wyżej jak w JEE!!!!
	public void setRateProvider(CurrencyRateProvider rateProvider) {
		this.rateProvider = rateProvider;
	}

	
	@Autowired
	MessageSource msgSource;
	
	
	
	public BigDecimal convertAmount(BigDecimal amount, String currency) {
		System.out.println(msgSource.getMessage("greetings", null,null, null));
		BigDecimal rate = rateProvider.getRate(currency, new Date());
	
		System.out.println("RateConversionService: Obliczam kwote");
		int precision = 2;
		System.out.println("ilosc dostwcow -" + precisionProviders.size());

		for (String providerName : precisionProviders.keySet()) {
		RoundPrecisionProvider provider = precisionProviders.get(providerName);
		System.out.println("Bean provider: "+providerName);
			if (provider.isSuported("PLN")) {
				precision = provider.getRoundPrecision("PLN");

			}
		}
		BigDecimal result = amount.multiply(rate).setScale(2, RoundingMode.HALF_UP);
		return result;
	}

}
