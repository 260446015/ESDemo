package com.ydw.es.test;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.Before;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class Test {
    Client client;
    @Before
    public void before() throws UnknownHostException {
        Settings settings = Settings.settingsBuilder().put("cluster.name", "elasticsearch_wenbronk") // 设置集群名
                // .put("client.transport.sniff", true) // 开启嗅探 , 开启后会一直连接不上, 原因未知
                // .put("network.host", "192.168.50.37")
                .put("client.transport.ignore_cluster_name", true) // 忽略集群名字验证, 打开后集群名字不对也能连接上
                // .put("client.transport.nodes_sampler_interval", 5) //报错,
                // .put("client.transport.ping_timeout", 5) // 报错, ping等待时间,
                .build();
        client = TransportClient.builder().settings(settings).build()
                .addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress("localhost", 9300)));
    }

    @org.junit.Test
    public void createIndex() {
        try {
            CreateIndexResponse indexResponse = this.client
                    .admin()
                    .indices()
                    .prepareCreate("123")
                    .get();

            System.out.println(indexResponse.isAcknowledged()); // true表示创建成功
        } catch (ElasticsearchException e) {
            e.printStackTrace();
        }
    }

    public void createDoc(String index, String type) {

        try {
            // 使用XContentBuilder创建一个doc source
            XContentBuilder builder =
                    XContentFactory.jsonBuilder()
                            .startObject()
                            .field("name", "zhangsan")
                            .field("age", "lisi")
                            .endObject();

            IndexResponse indexResponse = this.client
                    .prepareIndex()
                    .setIndex(index)
                    .setType(type)
                    // .setId(id) // 如果没有设置id，则ES会自动生成一个id
                    .setSource(builder.string())
                    .get();
            System.out.println(indexResponse.isCreated());
        } catch (ElasticsearchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void testAdd(){
        createDoc("123","user");
    }
}
