package com.chloe.es;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * ClassName: IndexTest
 * Package: com.chloe.es
 * Description:
 * 测试索引操作
 *
 * @Author Xu, Luqin
 * @Create 2024/11/17 8:38
 * @Version 1.0
 */
@Slf4j
public class IndexTest {
    private RestHighLevelClient client = null;
    private final static String indexName = "user";

    @Before
    public void init() {
        client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
    }

    @After
    public void close() throws IOException {
        client.close();
    }

    @Test
    public void testCreate() throws IOException {
        CreateIndexResponse response = client.indices().create(new CreateIndexRequest(indexName), RequestOptions.DEFAULT);
        boolean acknowledged = response.isAcknowledged();
        System.out.println("操作状态： " + acknowledged);
    }

    @Test
    public void testSearch() throws IOException {
        // 注意 GetIndexRequest 对应的java包
        GetIndexResponse response = client.indices().get(new GetIndexRequest(indexName),
                RequestOptions.DEFAULT);
        System.out.println(response.getAliases());
        System.out.println(response.getMappings());
        System.out.println(response.getSettings());
    }

    @Test
    public void testDelete() throws IOException {
        AcknowledgedResponse response = client.indices().delete(new DeleteIndexRequest(indexName), RequestOptions.DEFAULT);
        System.out.println(response.isAcknowledged());
    }
}
