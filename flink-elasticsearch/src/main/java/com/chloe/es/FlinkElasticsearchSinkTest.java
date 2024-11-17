package com.chloe.es;

import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkFunction;
import org.apache.flink.streaming.connectors.elasticsearch.RequestIndexer;
import org.apache.flink.streaming.connectors.elasticsearch7.ElasticsearchSink;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Requests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: ${NAME}
 * Package: com.chloe.es
 * Description:
 *
 * @Author Xu, Luqin
 * @Create 2024/11/17 23:52
 * @Version 1.0
 */
public class FlinkElasticsearchSinkTest {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env =
                StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> source = env.socketTextStream("localhost",
                9999);
        List<HttpHost> httpHosts = new ArrayList<>();
        httpHosts.add(new HttpHost("127.0.0.1", 9200, "http"));
        ElasticsearchSink.Builder<String> esSinkBuilder = new ElasticsearchSink.Builder<>(httpHosts, new ElasticsearchSinkFunction<String>() {
            public IndexRequest createIndexRequest(String element) {
                        Map<String, String> json = new HashMap<>();
                        json.put("data", element);
                        return Requests.indexRequest()
                                .index("my-index")
                                //.type("my-type")
                                .source(json);
                    }

                    @Override
                    public void process(String element, RuntimeContext ctx,
                                        RequestIndexer indexer) {
                        indexer.add(createIndexRequest(element));
                    }
                }
        );
        source.addSink(esSinkBuilder.build());
        env.execute("flink-es");
    }
}