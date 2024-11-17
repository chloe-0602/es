package com.chloe.es;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;

import java.io.IOException;

/**
 * ClassName: ESClientTest
 * Package: com.chloe.es
 * Description:
 *
 * @Author Xu, Luqin
 * @Create 2024/11/17 8:43
 * @Version 1.0
 */
public class ESClientTest {
    /**
     * 9200shi ES 的web通信端口
     *
     * @throws IOException
     */
    @Test
    public void testCreateClient() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
        client.close();
    }
}
