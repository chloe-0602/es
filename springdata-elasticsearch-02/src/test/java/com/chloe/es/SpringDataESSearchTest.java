package com.chloe.es;

import com.chloe.es.dao.ProductDao;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * ClassName: SpringDataESSearchTest
 * Package: com.chloe.es
 * Description:
 *
 * @Author Xu, Luqin
 * @Create 2024/11/17 23:05
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringDataESSearchTest {
    @Autowired
    private ProductDao productDao;

    @Test
    public void testTermQuery(){
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title", "xiaomi"); //识别中文有问题
        productDao.search(termQueryBuilder)
                .forEach(System.out::println);
    }
    
    @Test
    public void testQueryByPage(){
        productDao.search(QueryBuilders.termQuery("title", "xiaomi"),
                          PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "price")))
                .forEach(System.out::println);
    }
}
