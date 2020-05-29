package com.heimuheimu.util.grafana;

import com.heimuheimu.util.grafana.dashboard.DashboardClient;
import com.heimuheimu.util.grafana.datasource.DataSource;
import com.heimuheimu.util.grafana.datasource.DataSourceClient;
import com.heimuheimu.util.grafana.folder.FolderClient;
import com.heimuheimu.util.grafana.http.GrafanaHttpClient;
import com.heimuheimu.util.grafana.organization.OrganizationClient;
import com.heimuheimu.util.grafana.support.async.consumer.AsyncConsumerDashboardsBuilder;
import com.heimuheimu.util.grafana.support.async.producer.AsyncProducerDashboardsBuilder;
import com.heimuheimu.util.grafana.support.cache.localcache.LocalCacheDashboardsBuilder;
import com.heimuheimu.util.grafana.support.cache.memcached.MemcachedDashboardsBuilder;
import com.heimuheimu.util.grafana.support.hotspot.HotspotDashboardsBuilder;
import com.heimuheimu.util.grafana.support.mysql.MysqlDashboardsBuilder;
import com.heimuheimu.util.grafana.support.raven.RavenDashboardsBuilder;
import com.heimuheimu.util.grafana.support.redis.client.RedisClientDashboardsBuilder;
import com.heimuheimu.util.grafana.support.redis.lock.RedisLockDashboardsBuilder;
import com.heimuheimu.util.grafana.support.redis.publisher.RedisPublisherDashboardsBuilder;
import com.heimuheimu.util.grafana.support.redis.subscriber.RedisSubscriberDashboardsBuilder;
import com.heimuheimu.util.grafana.support.rpc.client.RpcClientDashboardsBuilder;
import com.heimuheimu.util.grafana.support.rpc.server.RpcServerDashboardsBuilder;

@SuppressWarnings("unused")
public class Main {

    /**
     * JVM 监控信息图表生成器，对应的 Prometheus 监控指标采集器：
     * <a href="https://github.com/heimuheimu/naivemonitor/blob/master/src/main/java/com/heimuheimu/naivemonitor/prometheus/support/hotspot/HotspotCompositePrometheusCollector.java">
     *     HotspotCompositePrometheusCollector
     * </a>
     */
    private static final HotspotDashboardsBuilder HOTSPOT_BUILDER;

    /**
     * MySQL 客户端监控信息图表生成器，对应的 Prometheus 监控指标采集器：
     * <a href="https://github.com/heimuheimu/mysql-jdbc/blob/master/src/main/java/com/heimuheimu/mysql/jdbc/monitor/prometheus/DatabaseCompositePrometheusCollector.java">
     *     DatabaseCompositePrometheusCollector
     * </a>
     */
    private static final MysqlDashboardsBuilder MYSQL_BUILDER;

    /**
     * RPC 服务端监控信息图表生成器，对应的 Prometheus 监控指标采集器：
     * <a href="https://github.com/heimuheimu/naiverpc/blob/master/src/main/java/com/heimuheimu/naiverpc/monitor/server/prometheus/RpcServerCompositePrometheusCollector.java">
     *     RpcServerCompositePrometheusCollector
     * </a>
     */
    private static final RpcServerDashboardsBuilder RPC_SERVER_BUILDER;

    /**
     * RPC 客户端监控信息图表生成器，对应的 Prometheus 监控指标采集器：
     * <a href="https://github.com/heimuheimu/naiverpc/blob/master/src/main/java/com/heimuheimu/naiverpc/monitor/client/prometheus/RpcClientCompositePrometheusCollector.java">
     *     RpcClientCompositePrometheusCollector
     * </a>
     */
    private static final RpcClientDashboardsBuilder RPC_CLIENT_BUILDER;

    /**
     * Redis 客户端监控信息图表生成器，对应的 Prometheus 监控指标采集器：
     * <a href="https://github.com/heimuheimu/naiveredis/blob/master/src/main/java/com/heimuheimu/naiveredis/monitor/prometheus/RedisCompositePrometheusCollector.java">
     *     RedisCompositePrometheusCollector
     * </a>
     */
    private static final RedisClientDashboardsBuilder REDIS_CLIENT_BUILDER;

    /**
     * Redis 分布式锁信息图表生成器，对应的 Prometheus 监控指标采集器：
     * <a href="https://github.com/heimuheimu/naiveredis/blob/master/src/main/java/com/heimuheimu/naiveredis/monitor/prometheus/RedisLockCompositePrometheusCollector.java">
     *     RedisLockCompositePrometheusCollector
     * </a>
     */
    private static final RedisLockDashboardsBuilder REDIS_LOCK_BUILDER;

    /**
     * Redis 消息发布客户端信息图表生成器，对应的 Prometheus 监控指标采集器：
     * <a href="https://github.com/heimuheimu/naiveredis/blob/master/src/main/java/com/heimuheimu/naiveredis/monitor/prometheus/RedisPublisherPrometheusCollector.java">
     *     RedisPublisherPrometheusCollector
     * </a>
     */
    private static final RedisPublisherDashboardsBuilder REDIS_PUB_BUILDER;

    /**
     * Redis 消息订阅客户端信息图表生成器，对应的 Prometheus 监控指标采集器：
     * <a href="https://github.com/heimuheimu/naiveredis/blob/master/src/main/java/com/heimuheimu/naiveredis/monitor/prometheus/RedisSubscriberPrometheusCollector.java">
     *     RedisSubscriberPrometheusCollector
     * </a>
     */
    private static final RedisSubscriberDashboardsBuilder REDIS_SUB_BUILDER;

    /**
     * Memcached 客户端信息图表生成器，对应的 Prometheus 监控指标采集器：
     * <a href="https://github.com/heimuheimu/naivecache/blob/master/src/main/java/com/heimuheimu/naivecache/memcached/monitor/prometheus/MemcachedCompositePrometheusCollector.java">
     *     MemcachedCompositePrometheusCollector
     * </a>
     */
    private static final MemcachedDashboardsBuilder MEMCACHED_BUILDER;

    /**
     * 本地缓存客户端信息图表生成器，对应的 Prometheus 监控指标采集器：
     * <a href="https://github.com/heimuheimu/naivecache/blob/master/src/main/java/com/heimuheimu/naivecache/localcache/monitor/prometheus/LocalCachePrometheusDataCollector.java">
     *     LocalCachePrometheusDataCollector
     * </a>
     */
    private static final LocalCacheDashboardsBuilder LOCAL_CACHE_BUILDER;

    /**
     * IM 服务信息图表生成器，对应的 Prometheus 监控指标采集器：
     * <a href="https://github.com/heimuheimu/raven/blob/master/src/main/java/com/heimuheimu/raven/monitor/prometheus/IMCompositePrometheusCollector.java">
     *     IMCompositePrometheusCollector
     * </a>
     */
    private static final RavenDashboardsBuilder RAVEN_BUILDER;

    /**
     * 异步消息生产者信息图表生成器，对应的 Prometheus 监控指标采集器：
     * <a href="https://github.com/heimuheimu/naiveasync/blob/master/src/main/java/com/heimuheimu/naiveasync/monitor/producer/prometheus/AsyncMessageProducerPrometheusDataCollector.java">
     *     AsyncMessageProducerPrometheusDataCollector
     * </a>
     */
    private static final AsyncProducerDashboardsBuilder ASYNC_PRODUCER_BUILDER;

    /**
     * 异步消息消费者信息图表生成器，对应的 Prometheus 监控指标采集器：
     * <a href="https://github.com/heimuheimu/naiveasync/blob/master/src/main/java/com/heimuheimu/naiveasync/monitor/consumer/prometheus/AsyncMessageConsumerPrometheusDataCollector.java">
     *     AsyncMessageConsumerPrometheusDataCollector
     * </a>
     */
    private static final AsyncConsumerDashboardsBuilder ASYNC_CONSUMER_BUILDER;

    static {
        // 设置 Granfana 的管理员账号、密码、主机、端口
        GrafanaHttpClient grafanaHttpClient = new GrafanaHttpClient("admin", "********",
                "localhost", 8300, false);
        OrganizationClient organizationClient = new OrganizationClient(grafanaHttpClient);
        DataSourceClient dataSourceClient = new DataSourceClient(grafanaHttpClient);
        DataSource dataSource = new DataSource();
        // 设置 DataSource 名称
        dataSource.setName("Prometheus");
        dataSource.setType(DataSource.TYPE_PROMETHEUS);
        // 设置 DataSource 对应的 Prometheus 访问路径
        dataSource.setUrl("http://localhost:9090");
        dataSource.setAccess(DataSource.ACCESS_MODE_PROXY);
        FolderClient folderClient = new FolderClient(grafanaHttpClient);
        DashboardClient dashboardClient = new DashboardClient(grafanaHttpClient);
        // hotspot
        HOTSPOT_BUILDER = new HotspotDashboardsBuilder(organizationClient, dataSourceClient, dataSource, folderClient, dashboardClient);
        // mysql-jdbc
        MYSQL_BUILDER = new MysqlDashboardsBuilder(organizationClient, dataSourceClient, dataSource, folderClient, dashboardClient);
        // naiverpc
        RPC_SERVER_BUILDER = new RpcServerDashboardsBuilder(organizationClient, dataSourceClient, dataSource, folderClient, dashboardClient);
        RPC_CLIENT_BUILDER = new RpcClientDashboardsBuilder(organizationClient, dataSourceClient, dataSource, folderClient, dashboardClient);
        // naiveredis
        REDIS_CLIENT_BUILDER = new RedisClientDashboardsBuilder(organizationClient, dataSourceClient, dataSource, folderClient, dashboardClient);
        REDIS_LOCK_BUILDER = new RedisLockDashboardsBuilder(organizationClient, dataSourceClient, dataSource, folderClient, dashboardClient);
        REDIS_PUB_BUILDER = new RedisPublisherDashboardsBuilder(organizationClient, dataSourceClient, dataSource, folderClient, dashboardClient);
        REDIS_SUB_BUILDER = new RedisSubscriberDashboardsBuilder(organizationClient, dataSourceClient, dataSource, folderClient, dashboardClient);
        // naivecache
        MEMCACHED_BUILDER = new MemcachedDashboardsBuilder(organizationClient, dataSourceClient, dataSource, folderClient, dashboardClient);
        LOCAL_CACHE_BUILDER = new LocalCacheDashboardsBuilder(organizationClient, dataSourceClient, dataSource, folderClient, dashboardClient);
        // raven
        RAVEN_BUILDER = new RavenDashboardsBuilder(organizationClient, dataSourceClient, dataSource, folderClient, dashboardClient);
        // naiveasync
        ASYNC_PRODUCER_BUILDER = new AsyncProducerDashboardsBuilder(organizationClient, dataSourceClient, dataSource, folderClient, dashboardClient);
        ASYNC_CONSUMER_BUILDER = new AsyncConsumerDashboardsBuilder(organizationClient, dataSourceClient, dataSource, folderClient, dashboardClient);
    }

    public static void main(String[] args) {
        // 为 Prometheus 中名称为 "demo-project" 的 job 生成 MySQL 客户端监控信息图表
        MYSQL_BUILDER.build("demo-project", "30s", System.out);

        // 为 Prometheus 中名称为 "demo-project" 的 job 生成异步消息消费者信息图表
        ASYNC_CONSUMER_BUILDER.build("demo-project", "30s", System.out);
    }
}
