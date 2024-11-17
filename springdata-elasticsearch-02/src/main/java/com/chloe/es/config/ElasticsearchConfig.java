package com.chloe.es.config;

import lombok.Data;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

/**
 * ClassName: ElasticsearchConfig
 * Package: com.chloe.es.config
 * Description:
 *
 * @Author Xu, Luqin
 * @Create 2024/11/17 22:37
 * @Version 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "elasticsearch")
@Data
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {
    private String host;
    private Integer port;
    @Override
    public RestHighLevelClient elasticsearchClient() {
        return new RestHighLevelClient(RestClient.builder(new HttpHost(host, port)));
    }
}
