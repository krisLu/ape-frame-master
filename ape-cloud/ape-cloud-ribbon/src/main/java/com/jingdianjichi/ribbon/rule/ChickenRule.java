package com.jingdianjichi.ribbon.rule;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.List;

public class ChickenRule extends AbstractLoadBalancerRule {

    public ChickenRule() {
    }

    @SuppressWarnings({"RCN_REDUNDANT_NULLCHECK_OF_NULL_VALUE"})
    public Server choose(ILoadBalancer lb, Object key) {
        if (lb == null) {
            return null;
        }
        Server server = null;
        while (server == null) {
            if (Thread.interrupted()) {
                return null;
            }
            //port端口号必须大于25（两年半练习生）.
            List<Server> upList = lb.getReachableServers();
            List<Server> allList = lb.getAllServers();
            int serverCount = allList.size();
            if (serverCount == 0) {
                return null;
            }
            for (Server upServer : upList) {
                int port = upServer.getPort();
                if (port > 25) {
                    server = upServer;
                    break;
                }
            }
            if (server == null) {
                Thread.yield();
            } else {
                if (server.isAlive()) {
                    return server;
                }
                server = null;
                Thread.yield();
            }
        }
        return server;
    }

    @Override
    public Server choose(Object key) {
        return this.choose(this.getLoadBalancer(), key);
    }

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }


}
