package com.chloe.es;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

/**
 * ClassName: QueryTest
 * Package: com.chloe.es
 * Description:
 *
 * @Author Xu, Luqin
 * @Create 2024/11/17 9:39
 * @Version 1.0
 */
public class QueryTest {
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
     * 查询所有索引数据
     */
    @Test
    public void testMatchAll() throws IOException {
        SearchRequest searchRequest = new SearchRequest().indices(indexName)
                .source(new SearchSourceBuilder().query(QueryBuilders.matchAllQuery()));
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        SearchHits hits = searchResponse.getHits();
        System.out.println("searchResponse.getTook() = " + searchResponse.getTook());
        System.out.println("hits.getTotalHits() = " + hits.getTotalHits());
        Arrays.stream(hits.getHits())
                .forEach(item -> {
                    System.out.println(item.getId() + " => " + "item.getSourceAsString() = " + item.getSourceAsString());
                });
    }

    /**
     * term 查询，查询条件为关键字
     */
    @Test
    public void testTerm() throws IOException {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .query(QueryBuilders.termQuery("age", "30"));

        SearchRequest searchRequest = new SearchRequest()
                .indices(indexName)
                .source(sourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        SearchHits hits = searchResponse.getHits();
        System.out.println("searchResponse.getTook() = " + searchResponse.getTook());
        System.out.println("hits.getTotalHits() = " + hits.getTotalHits());
        Arrays.stream(hits.getHits())
                .forEach(item -> {
                    System.out.println(item.getId() + " => " + "item.getSourceAsString() = " + item.getSourceAsString());
                });
    }

    /**
     * 分页查询
     */
    @Test
    public void testPage() throws IOException {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .query(QueryBuilders.matchAllQuery())
                .from(1)
                .size(2);

        SearchRequest searchRequest = new SearchRequest()
                .indices(indexName)
                .source(sourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        SearchHits hits = searchResponse.getHits();
        System.out.println("searchResponse.getTook() = " + searchResponse.getTook());
        System.out.println("hits.getTotalHits() = " + hits.getTotalHits());
        Arrays.stream(hits.getHits())
                .forEach(item -> {
                    System.out.println(item.getId() + " => " + "item.getSourceAsString() = " + item.getSourceAsString());
                });
    }

    /**
     * 数据排序
     */
    @Test
    public void testOrder() throws IOException {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .query(QueryBuilders.matchAllQuery())
                .sort("age", SortOrder.DESC);

        SearchRequest searchRequest = new SearchRequest()
                .indices(indexName)
                .source(sourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        SearchHits hits = searchResponse.getHits();
        System.out.println("searchResponse.getTook() = " + searchResponse.getTook());
        System.out.println("hits.getTotalHits() = " + hits.getTotalHits());
        Arrays.stream(hits.getHits())
                .forEach(item -> {
                    System.out.println(item.getId() + " => " + "item.getSourceAsString() = " + item.getSourceAsString());
                });
    }


    /**
     * 字段筛选
     */
    @Test
    public void testFilterFields() throws IOException {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .query(QueryBuilders.matchAllQuery())
                .fetchSource(new String[]{"name", "age"}, new String[]{});

        SearchRequest searchRequest = new SearchRequest()
                .indices(indexName)
                .source(sourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        SearchHits hits = searchResponse.getHits();
        System.out.println("searchResponse.getTook() = " + searchResponse.getTook());
        System.out.println("hits.getTotalHits() = " + hits.getTotalHits());
        Arrays.stream(hits.getHits())
                .forEach(item -> {
                    System.out.println(item.getId() + " => " + "item.getSourceAsString() = " + item.getSourceAsString());
                });
    }

    /**
     * Bool查询
     */
    @Test
    public void testBool() throws IOException {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("age", 30))
                .mustNot(QueryBuilders.matchQuery("name", "zhangsan1"))
                .should(QueryBuilders.matchQuery("sex", "男"));

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .query(boolQueryBuilder);

        SearchRequest searchRequest = new SearchRequest()
                .indices(indexName)
                .source(sourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        SearchHits hits = searchResponse.getHits();
        System.out.println("searchResponse.getTook() = " + searchResponse.getTook());
        System.out.println("hits.getTotalHits() = " + hits.getTotalHits());
        Arrays.stream(hits.getHits())
                .forEach(item -> {
                    System.out.println(item.getId() + " => " + "item.getSourceAsString() = " + item.getSourceAsString());
                });
    }

    /**
     * 范围查询
     */
    @Test
    public void testRange() throws IOException {
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("age")
                .gte(30)
                .lte(35);

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .query(rangeQueryBuilder);

        SearchRequest searchRequest = new SearchRequest()
                .indices(indexName)
                .source(sourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        SearchHits hits = searchResponse.getHits();
        System.out.println("searchResponse.getTook() = " + searchResponse.getTook());
        System.out.println("hits.getTotalHits() = " + hits.getTotalHits());
        System.out.println("hits.getMaxScore() = " + hits.getMaxScore());
        Arrays.stream(hits.getHits())
                .forEach(item -> {
                    System.out.println(item.getId() + " => " + "item.getSourceAsString() = " + item.getSourceAsString());
                });
    }

    /**
     * 模糊查询
     */
    @Test
    public void testFreeze() throws IOException {
        FuzzyQueryBuilder fuzzyQueryBuilder = QueryBuilders.fuzzyQuery("name", "zhangsan")
                .fuzziness(Fuzziness.TWO);

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .query(fuzzyQueryBuilder);

        SearchRequest searchRequest = new SearchRequest()
                .indices(indexName)
                .source(sourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        SearchHits hits = searchResponse.getHits();
        System.out.println("searchResponse.getTook() = " + searchResponse.getTook());
        System.out.println("hits.getTotalHits() = " + hits.getTotalHits());
        System.out.println("hits.getMaxScore() = " + hits.getMaxScore());
        Arrays.stream(hits.getHits())
                .forEach(item -> {
                    System.out.println(item.getId() + " => " + "item.getSourceAsString() = " + item.getSourceAsString());
                });
    }

    /**
     * 高亮查询
     */
    @Test
    public void testHighLight() throws IOException {
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "zhangsan1");

        HighlightBuilder highlightBuilder = new HighlightBuilder()
                .preTags("<font color='red'>")
                .postTags("</font>")
                .field("name"); //别忘记这个，否则name这个字段的高亮打不出来

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .query(termQueryBuilder)
                .highlighter(highlightBuilder);

        SearchRequest searchRequest = new SearchRequest()
                .indices(indexName)
                .source(sourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        SearchHits hits = searchResponse.getHits();
        System.out.println("searchResponse.getTook() = " + searchResponse.getTook());
        System.out.println("hits.getTotalHits() = " + hits.getTotalHits());
        System.out.println("hits.getMaxScore() = " + hits.getMaxScore());
        Arrays.stream(hits.getHits())
                .forEach(hit -> {
                    System.out.println(hit.getId() + " => " + "hit.getSourceAsString() = " + hit.getSourceAsString());
                    System.out.println("hit.getHighlightFields() = " + hit.getHighlightFields().toString());
                });
    }


    /**
     * 聚合操作
     */
    @Test
    public void testAggs() throws IOException {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .aggregation(AggregationBuilders.max("maxAge").field("age"));

        SearchRequest searchRequest = new SearchRequest()
                .indices(indexName)
                .source(sourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("searchResponse = " + searchResponse);

    }

        @Test
    public void testGroup() throws IOException {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .aggregation(AggregationBuilders.terms("ageGroup").field("age"));

        SearchRequest searchRequest = new SearchRequest()
                .indices(indexName)
                .source(sourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("searchResponse = " + searchResponse);

    }
}
