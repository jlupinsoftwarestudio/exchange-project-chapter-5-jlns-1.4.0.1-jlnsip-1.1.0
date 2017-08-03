package com.example.exchange.data.layer;

import com.example.exchange.util.JLupinTestConfigurationUtil;
import com.jlupin.impl.client.proxy.remote.producer.ext.JLupinRemoteProxyObjectSupportsExceptionProducerImpl;
import com.jlupin.impl.logger.impl.log4j.JLupinLoggerOverLog4jImpl;
import com.jlupin.interfaces.client.delegator.JLupinDelegator;
import com.jlupin.interfaces.client.proxy.producer.JLupinProxyObjectProducer;
import com.jlupin.interfaces.logger.JLupinLogger;

/**
 * Created by Piotr Heilman on 2017-07-31.
 */
public class ExchangeRatesTest {
    private static final int HOW_OFTEN_CHECKING_SERVER_IN_MILLIS = 5000;
    private static final int REPEATS_AMOUNT = 3;
    private static final int CHANGE_SERVER_INTERVAL_IN_MILLIS = 5000;

    private static JLupinLogger getJLupinLogger() {
        return JLupinLoggerOverLog4jImpl.getInstance();
    }

    private JLupinDelegator getJLupinDelegator() {
        final JLupinTestConfigurationUtil jLupinConfigurationUtil = new JLupinTestConfigurationUtil();

        return jLupinConfigurationUtil.generateJLupinRMCLoadBalancerDelegatorImpl(
                HOW_OFTEN_CHECKING_SERVER_IN_MILLIS,
                REPEATS_AMOUNT,
                CHANGE_SERVER_INTERVAL_IN_MILLIS
        );
    }

    private JLupinProxyObjectProducer getJLupinProxyObjectProducer() {
        return new JLupinRemoteProxyObjectSupportsExceptionProducerImpl(
                "exchange-rates", getJLupinDelegator(), getJLupinLogger()
        );
    }

    // Example test
    // @Test
    // public void exampleTest() {
    //     ExampleService service = getJLupinProxyObjectProducer().produceObject(ExampleService.class);
    //     assertEquals("2 + 2 must be 4", new Integer(4), service.add(2, 2));
    // }
}