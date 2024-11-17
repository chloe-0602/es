package es;

import com.chloe.es.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

/**
 * ClassName: DocumentTest
 * Package: com.chloe.es
 * Description:
 * document 测试
 *
 * @Author Xu, Luqin
 * @Create 2024/11/17 9:00
 * @Version 1.0
 */
public class DocumentTest {
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

    /**
     * 新增文档
     */
    @Test
    public void testCreate() throws IOException {
        IndexRequest request = new IndexRequest().index(indexName).id("1001");
        request.source(new ObjectMapper().writeValueAsString(new User().setName("zhangsan").setSex("男").setAge(30)), XContentType.JSON);

        IndexResponse response = client.index(request, RequestOptions.DEFAULT);

        System.out.println("response = " + response);
        System.out.println("_id = " + response.getId());
        System.out.println("_index = " + response.getIndex());
        System.out.println("_result = " + response.getResult());
    }

    /**
     * 修改文档
     * 1.全部修改  - ok
     * 2.修改部分字段
     */
    @Test
    public void testUpdate1() throws IOException {
        UpdateRequest updateRequest = new UpdateRequest(indexName, "1001");
        String jsonStr = new ObjectMapper().writeValueAsString(new User().setName("zhangsan").setSex("女").setAge(31));
        updateRequest.doc(jsonStr, XContentType.JSON);

        UpdateResponse response = client.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println("response = " + response);
        System.out.println("response.getId() = " + response.getId());
        System.out.println("response.getIndex() = " + response.getIndex());
        System.out.println("response.getResult() = " + response.getResult());
    }

    /**
     * 修改文档
     * 1.全部修改  - ok
     * 2.修改部分字段
     */
    @Test
    public void testUpdate2() throws IOException {
        UpdateRequest updateRequest = new UpdateRequest(indexName, "1001");
        updateRequest.doc(XContentType.JSON, "sex", "男");

        UpdateResponse response = client.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println("response = " + response);
    }

    /**
     * 简单查询文档
     */
    @Test
    public void testSearch() throws IOException {
        GetResponse response = client.get(new GetRequest().index(indexName).id("1001"), RequestOptions.DEFAULT);
        System.out.println("response = " + response);
    }

    @Test
    public void testDelete() throws IOException {
        DeleteResponse response = client.delete(new DeleteRequest().index(indexName).id("1"), RequestOptions.DEFAULT);
        System.out.println("response = " + response);
    }

    /**
     * 批量操作之一： 批量新增
     */
    @Test
    public void testBulkAdd() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();

        bulkRequest.add(new IndexRequest().index(indexName).id("1001").source(XContentType.JSON, "name", "zhangsan", "sex", "男", "age", 20));
        bulkRequest.add(new IndexRequest().index(indexName).id("1002").source(XContentType.JSON, "name", "lisi", "sex", "女", "age", 25));
        bulkRequest.add(new IndexRequest().index(indexName).id("1003").source(XContentType.JSON, "name", "wangwu", "sex", "男", "age", 27));
        bulkRequest.add(new IndexRequest().index(indexName).id("1004").source(XContentType.JSON, "name", "zhangsan1", "sex", "男", "age", 28));
        bulkRequest.add(new IndexRequest().index(indexName).id("1005").source(XContentType.JSON, "name", "zhangsan2", "sex", "男", "age", 29));
        bulkRequest.add(new IndexRequest().index(indexName).id("1006").source(XContentType.JSON, "name", "zhangsan3", "sex", "男", "age", 30));
        bulkRequest.add(new IndexRequest().index(indexName).id("1007").source(XContentType.JSON, "name", "zhangsan44", "sex", "男", "age", 31));

        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println("bulkResponse = " + bulkResponse);
        System.out.println("bulkResponse.getTook() = " + bulkResponse.getTook());
        System.out.println("bulkResponse.getItems() = ");
        Arrays.stream(bulkResponse.getItems())
                .map(item -> item.getId() + "," + item.getIndex())
                .forEach(System.out::println);
    }

    @Test
    public void testBulkDelete() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();

        bulkRequest.add(new DeleteRequest().index(indexName).id("1001"));
        bulkRequest.add(new DeleteRequest().index(indexName).id("1002"));
        bulkRequest.add(new DeleteRequest().index(indexName).id("1003"));

        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println("bulkResponse.getTook() = " + bulkResponse.getTook());
        System.out.println("bulkResponse.getItems() = " + bulkResponse.getItems());
    }
}
