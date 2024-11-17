package com.chloe.es.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * ClassName: User
 * Package: com.chloe.es.entities
 * Description:
 *
 * @Author Xu, Luqin
 * @Create 2024/11/17 22:35
 * @Version 1.0
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Document(indexName = "product", shards = 3, replicas = 1)
public class Product {
    private Long id;//商品唯一标识
    private String title;//商品名称
    private String category;//分类名称
    private Double price;//商品价格
    private String images;//图片地址
}
