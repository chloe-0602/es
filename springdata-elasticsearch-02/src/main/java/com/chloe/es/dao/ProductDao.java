package com.chloe.es.dao;

import com.chloe.es.entities.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * ClassName: ProductDao
 * Package: com.chloe.es.dao
 * Description:
 *
 * @Author Xu, Luqin
 * @Create 2024/11/17 22:41
 * @Version 1.0
 */
@Repository
public interface ProductDao extends ElasticsearchRepository<Product,Long> {
}
