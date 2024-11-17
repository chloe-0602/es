package com.chloe.es;

import com.chloe.es.dao.ProductDao;
import com.chloe.es.entities.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

/**
 * ClassName: SpringDataESProductDaoTest
 * Package: com.chloe.es
 * Description:
 *
 * @Author Xu, Luqin
 * @Create 2024/11/17 22:54
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringDataESProductDaoTest {
    @Autowired
    private ProductDao productDao;

    @Test
    public void testSave() {
        Product product = new Product();
        product.setId(1L);
        product.setTitle("华为手机");
        product.setCategory("手机");
        product.setPrice(2999.0);
        product.setImages("http://www.atguigu/hw.jpg");
        productDao.save(product);
    }

    @Test
    public void testUpdate() {
        Product product = new Product();
        product.setId(1L);
        product.setTitle("小米 2 手机");
        product.setCategory("手机");
        product.setPrice(9999.0);
        product.setImages("http://www.atguigu/xm.jpg");
        productDao.save(product);
    }

    @Test
    public void testFindById() {
        Product product = productDao.findById(1L).get();
        System.out.println("product = " + product);
    }

    @Test
    public void testFindAll() {
        productDao.findAll().forEach(System.out::println);
    }

    @Test
    public void testDelete() {
        productDao.deleteById(1L);
    }

    @Test
    public void testSaveBatch() {
        ArrayList<Product> list = new ArrayList<>();
        for (int i = 21; i < 30; i++) {
            Product product = new Product();
            product.setId(Long.valueOf(i));
            product.setTitle("xiaomi");
            product.setCategory("手机");
            product.setPrice(1999.0 + i);
            product.setImages("http://www.atguigu/xm.jpg");
            list.add(product);
        }
        productDao.saveAll(list);
    }

    @Test
    public void testPage(){
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "id"));
        Page<Product> all = productDao.findAll(pageRequest);
        all.forEach(System.out::println);
    }
}
