package com.atguigu.crud.service;

import java.util.List;

/**
 * @Auther: Albert
 * @Date: 2018/8/16 23:02
 * @Description:
 */
public interface DepartmentService<T> {
    List<T> getDepts();
}
