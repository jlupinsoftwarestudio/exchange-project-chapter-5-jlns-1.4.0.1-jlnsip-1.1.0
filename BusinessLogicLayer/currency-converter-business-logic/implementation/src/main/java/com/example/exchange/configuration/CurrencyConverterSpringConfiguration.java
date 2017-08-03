package com.example.exchange.configuration;

import com.example.exchange.configuration.util.JLupinConfigurationUtil;
import com.example.exchange.service.interfaces.ExchangeRatesService;
import com.jlupin.impl.client.proxy.remote.producer.ext.JLupinRemoteProxyObjectSupportsExceptionProducerImpl;
import com.jlupin.interfaces.client.delegator.JLupinDelegator;
import com.jlupin.interfaces.client.delegator.exception.JLupinDelegatorException;
import com.jlupin.interfaces.client.proxy.producer.JLupinProxyObjectProducer;
import com.jlupin.interfaces.container.system.JLupinSystemContainer;
import com.jlupin.interfaces.logger.JLupinLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan("com.example.exchange")
public class CurrencyConverterSpringConfiguration {
    private static final int HOW_OFTEN_CHECKING_SERVER_IN_MILLIS = 5000;
    private static final int REPEATS_AMOUNT = 3;
    private static final int CHANGE_SERVER_INTERVAL_IN_MILLIS = 5000;

    private final JLupinLogger jLupinLogger;
    private final JLupinDelegator jLupinDelegator;

    public CurrencyConverterSpringConfiguration() {
        final JLupinSystemContainer jLupinSystemContainer = JLupinSystemContainer.getInstance();
        final JLupinConfigurationUtil jLupinConfigurationUtil = new JLupinConfigurationUtil();

        jLupinLogger = jLupinSystemContainer.getJLupinLogger();
        jLupinDelegator = jLupinConfigurationUtil.generateJLupinRMCLoadBalancerDelegatorImpl(
                HOW_OFTEN_CHECKING_SERVER_IN_MILLIS,
                REPEATS_AMOUNT,
                CHANGE_SERVER_INTERVAL_IN_MILLIS
        );
    }

    @PostConstruct
    public void startLoadBalancingDelegator() throws JLupinDelegatorException {
        jLupinDelegator.before();
    }

    // @Bean(name = "exampleService")
    // public ExampleService getExampleService() {
    //     JLupinProxyObjectProducer objectProducer =
    //             new JLupinRemoteProxyObjectSupportsExceptionProducerImpl("example-microservice", jLupinDelegator, jLupinLogger);
    //
    //     return objectProducer.produceObject(ExampleService.class);
    // }

    @Bean(name = "exchangeRatesService")
    public ExchangeRatesService getExchangeRatesService() {
        JLupinProxyObjectProducer objectProducer =
                new JLupinRemoteProxyObjectSupportsExceptionProducerImpl("exchange-rates", jLupinDelegator, jLupinLogger);
        return objectProducer.produceObject(ExchangeRatesService.class);
    }

    @Bean(name = "jLupinRegularExpressionToRemotelyEnabled")
    public List getRemotelyBeanList() {
        List<String> list = new ArrayList<>();
        list.add("currencyConverterService");
        // list.add("<REMOTE_SERVICE_NAME>");
        return list;
    }
}

