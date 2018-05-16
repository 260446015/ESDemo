package com.ydw.es;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@ComponentScan(basePackages = "com.ydw.es")
@EntityScan(basePackages = "com.yw")
@Configuration
public class EsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EsApplication.class, args);
	}

	@Bean
	public Client getClient(){
		Settings settings = Settings.settingsBuilder().put("cluster.name", "elasticsearch_wenbronk") // 设置集群名
				// .put("client.transport.sniff", true) // 开启嗅探 , 开启后会一直连接不上, 原因未知
				// .put("network.host", "192.168.50.37")
				.put("client.transport.ignore_cluster_name", true) // 忽略集群名字验证, 打开后集群名字不对也能连接上
				// .put("client.transport.nodes_sampler_interval", 5) //报错,
				// .put("client.transport.ping_timeout", 5) // 报错, ping等待时间,
				.build();
		Client client = TransportClient.builder().settings(settings).build()
				.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress("localhost", 9300)));
		return client;
	}
}
