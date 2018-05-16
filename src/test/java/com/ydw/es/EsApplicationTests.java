package com.ydw.es;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.node.Node;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class EsApplicationTests {

	Client client;
	@Before
	public void before() throws UnknownHostException {
		//通过Settings类设置属性参数
		Settings settings = Settings.builder().put("cluster.name","index-name").build();

//启动Client
		Client client = TransportClient.builder().settings(settings).build().
				addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"),9300));

//如果不需要设置参数，直接如下
/*Client client = TransportClient.builder().build().
        addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.xxx.xxx"),9300));*/

//关闭Clinet
		client.close();
	}

	@Test
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

	@Test
	public void contextLoads() {

	}

}
