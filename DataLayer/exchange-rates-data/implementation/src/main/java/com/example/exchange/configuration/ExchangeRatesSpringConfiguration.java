package com.example.exchange.configuration;

import com.example.exchange.configuration.util.JLupinConfigurationUtil;
import com.jlupin.interfaces.client.delegator.JLupinDelegator;
import com.jlupin.interfaces.client.delegator.exception.JLupinDelegatorException;
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
public class ExchangeRatesSpringConfiguration {
    private static final int HOW_OFTEN_CHECKING_SERVER_IN_MILLIS = 5000;
    private static final int REPEATS_AMOUNT = 3;
    private static final int CHANGE_SERVER_INTERVAL_IN_MILLIS = 5000;

    private final JLupinLogger jLupinLogger;
    private final JLupinDelegator jLupinDelegator;

    public ExchangeRatesSpringConfiguration() {
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

    @Bean(name = "jLupinRegularExpressionToRemotelyEnabled")
    public List getRemotelyBeanList() {
        List<String> list = new ArrayList<>();
        list.add("exchangeRatesService");
        // list.add("<REMOTE_SERVICE_NAME>");
        return list;
    }
}

