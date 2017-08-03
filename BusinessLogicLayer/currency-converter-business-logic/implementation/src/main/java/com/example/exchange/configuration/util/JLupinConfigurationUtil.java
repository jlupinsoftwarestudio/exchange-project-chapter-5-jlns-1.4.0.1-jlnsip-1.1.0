package com.example.exchange.configuration.util;

import com.jlupin.impl.balancer.ext.impl.roundrobin.JLupinRoundRobinLoadBalancerImpl;
import com.jlupin.impl.balancer.type.JLupinBalancerType;
import com.jlupin.impl.client.delegator.balance.JLupinQueueLoadBalancerDelegatorImpl;
import com.jlupin.impl.client.delegator.balance.JLupinRMCLoadBalancerDelegatorImpl;
import com.jlupin.interfaces.container.system.JLupinSystemContainer;
import com.jlupin.interfaces.logger.JLupinLogger;
import com.jlupin.interfaces.serialize.JLupinSerializer;

public class JLupinConfigurationUtil {
    public JLupinRMCLoadBalancerDelegatorImpl generateJLupinRMCLoadBalancerDelegatorImpl(int howOftenCheckingServerInMillis, int repeatsAmount,
                                                                                         int changeServerIntervalInMillis) {
        final JLupinSystemContainer jLupinSystemContainer = JLupinSystemContainer.getInstance();
        final JLupinSerializer jLupinSerializer = jLupinSystemContainer.getJLupinSerializer();

        final JLupinRoundRobinLoadBalancerImpl jLupinLoadBalancer = generateJLupinRoundRobinLoadBalancerImpl(
                howOftenCheckingServerInMillis,
                repeatsAmount,
                changeServerIntervalInMillis
        );

        return new JLupinRMCLoadBalancerDelegatorImpl(jLupinLoadBalancer, jLupinSerializer);
    }

    public JLupinQueueLoadBalancerDelegatorImpl generateJLupinQueueLoadBalancerDelegatorImpl(int howOftenCheckingServerInMillis, int repeatsAmount,
                                                                                             int changeServerIntervalInMillis) {
        final JLupinSystemContainer jLupinSystemContainer = JLupinSystemContainer.getInstance();
        final JLupinSerializer jLupinSerializer = jLupinSystemContainer.getJLupinSerializer();

        final JLupinRoundRobinLoadBalancerImpl jLupinLoadBalancer = generateJLupinRoundRobinLoadBalancerImpl(
                howOftenCheckingServerInMillis,
                repeatsAmount,
                changeServerIntervalInMillis
        );

        return new JLupinQueueLoadBalancerDelegatorImpl(jLupinLoadBalancer, jLupinSerializer);
    }

    private JLupinRoundRobinLoadBalancerImpl generateJLupinRoundRobinLoadBalancerImpl(int howOftenCheckingServerInMillis, int repeatsAmount,
                                                                                      int changeServerIntervalInMillis) {
        final JLupinSystemContainer jLupinSystemContainer = JLupinSystemContainer.getInstance();
        final JLupinLogger jLupinLogger = jLupinSystemContainer.getJLupinLogger();

        final JLupinRoundRobinLoadBalancerImpl jLupinLoadBalancer = new JLupinRoundRobinLoadBalancerImpl(
                jLupinLogger,
                howOftenCheckingServerInMillis,
                repeatsAmount,
                changeServerIntervalInMillis
        );
        jLupinLoadBalancer.setJLupinBalancerType(JLupinBalancerType.INNER_MICROSERVICE);

        return jLupinLoadBalancer;
    }
}
