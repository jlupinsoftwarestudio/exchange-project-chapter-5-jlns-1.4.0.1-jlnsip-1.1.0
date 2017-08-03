package com.example.exchange.util;

import com.jlupin.common.communication.common.various.JLupinMainServerInZoneConfiguration;
import com.jlupin.impl.balancer.ext.impl.roundrobin.JLupinRoundRobinLoadBalancerImpl;
import com.jlupin.impl.balancer.structures.property.JLupinLoadBalancerProperties;
import com.jlupin.impl.balancer.structures.wrapper.JLupinLoadBalancerPropertiesWrapper;
import com.jlupin.impl.balancer.type.JLupinBalancerType;
import com.jlupin.impl.client.delegator.balance.JLupinQueueLoadBalancerDelegatorImpl;
import com.jlupin.impl.client.delegator.balance.JLupinRMCLoadBalancerDelegatorImpl;
import com.jlupin.impl.logger.impl.log4j.JLupinLoggerOverLog4jImpl;
import com.jlupin.impl.serialize.JLupinFSTSerializerImpl;
import com.jlupin.interfaces.logger.JLupinLogger;
import com.jlupin.interfaces.serialize.JLupinSerializer;

public class JLupinTestConfigurationUtil {

    public JLupinRMCLoadBalancerDelegatorImpl generateJLupinRMCLoadBalancerDelegatorImpl(int howOftenCheckingServerInMillis, int repeatsAmount,
                                                                                         int changeServerIntervalInMillis) {
        final JLupinSerializer jLupinSerializer = JLupinFSTSerializerImpl.getInstance();

        final JLupinRoundRobinLoadBalancerImpl jLupinLoadBalancer = generateJLupinRoundRobinLoadBalancer(
                howOftenCheckingServerInMillis,
                repeatsAmount,
                changeServerIntervalInMillis
        );

        return new JLupinRMCLoadBalancerDelegatorImpl(jLupinLoadBalancer, jLupinSerializer);
    }

    public JLupinQueueLoadBalancerDelegatorImpl generateJLupinQueueLoadBalancerDelegatorImpl(int howOftenCheckingServerInMillis, int repeatsAmount,
                                                                                             int changeServerIntervalInMillis) {
        final JLupinSerializer jLupinSerializer = JLupinFSTSerializerImpl.getInstance();

        final JLupinRoundRobinLoadBalancerImpl jLupinLoadBalancer = generateJLupinRoundRobinLoadBalancer(
                howOftenCheckingServerInMillis,
                repeatsAmount,
                changeServerIntervalInMillis
        );

        return new JLupinQueueLoadBalancerDelegatorImpl(jLupinLoadBalancer, jLupinSerializer);
    }

    private JLupinRoundRobinLoadBalancerImpl generateJLupinRoundRobinLoadBalancer(int howOftenCheckingServerInMillis, int repeatsAmount,
                                                                                  int changeServerIntervalInMillis) {
        final JLupinLogger jLupinLogger = JLupinLoggerOverLog4jImpl.getInstance();

        final JLupinLoadBalancerProperties jLupinLoadBalancerProperties = new JLupinLoadBalancerProperties();
        jLupinLoadBalancerProperties.setJLupinMainServerInZoneConfigurations(new JLupinMainServerInZoneConfiguration[]{
                new JLupinMainServerInZoneConfiguration("NODE_1", "127.0.0.1", 9090, 9095, 9096, 9097)
        });

        final JLupinLoadBalancerPropertiesWrapper jLupinLoadBalancerPropertiesWrapper = new JLupinLoadBalancerPropertiesWrapper();
        jLupinLoadBalancerPropertiesWrapper.setJLupinLoadBalancerProperties(jLupinLoadBalancerProperties);

        final JLupinRoundRobinLoadBalancerImpl jLupinLoadBalancer = new JLupinRoundRobinLoadBalancerImpl(
                jLupinLogger,
                howOftenCheckingServerInMillis,
                repeatsAmount,
                changeServerIntervalInMillis
        );
        jLupinLoadBalancer.setJLupinBalancerType(JLupinBalancerType.OUTER_CLIENT);
        jLupinLoadBalancer.setJLupinLoadBalancerPropertiesWrapper(jLupinLoadBalancerPropertiesWrapper);
        jLupinLoadBalancer.start();

        return jLupinLoadBalancer;
    }
}