package com.chloe.es;

import com.chloe.es.entities.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * ClassName: SpringDataESTest
 * Package: com.chloe.es
 * Description:
 *
 * @Author Xu, Luqin
 * @Create 2024/11/17 22:45
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringDataESIndexTest {
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    
    @Test
    public void testCreate(){
        System.out.println("创建索引");
    }

    @Test
    public void testDelete(){
        boolean deleteIndex = elasticsearchRestTemplate.deleteIndex(Product.class);
        System.out.println("deleteIndex = " + deleteIndex);
    }
}
