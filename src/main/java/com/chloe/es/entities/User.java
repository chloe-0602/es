package com.chloe.es.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * ClassName: User
 * Package: com.chloe.es.entities
 * Description:
 *
 * @Author Xu, Luqin
 * @Create 2024/11/17 9:04
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class User {
    private String name;
    private Integer age;
    private String sex;
}
