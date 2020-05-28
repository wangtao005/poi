package com.example.poi.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "t_test")
public class Test {
    /*@Id注解：声明属性为一个OID属性*/
    @Id /*@GeneratedValue注解，指定主键生成策略*/
    @GeneratedValue(strategy = GenerationType.AUTO)/*@Column注解，设置属性与数据库字段的关系，如果属性名和表的字段名相同，可以不设置*/
    @Column(name = "id")
    private Long id;/*BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '学生编号',*/

    @Column(name = "name",length = 50)
    private String name;/*VARCHAR(50) NULL DEFAULT NULL COMMENT '学生名字',*/

    @Column(name = "sex",length = 1)
    private String sex;/*INT(11) NULL DEFAULT NULL COMMENT '性别',*/

    @Column(name = "age")
    private Integer age;/*INT(11) NULL DEFAULT NULL COMMENT '学生年龄',*/

    @Temporal(TemporalType.DATE)
    private Date birthday;/*INT(11) NULL DEFAULT NULL COMMENT '学生年龄',*/

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;/*INT(11) NULL DEFAULT NULL COMMENT '学生年龄',*/

}






