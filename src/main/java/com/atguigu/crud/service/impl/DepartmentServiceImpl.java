package com.atguigu.crud.service.impl;

import com.atguigu.crud.bean.Department;
import com.atguigu.crud.dao.DepartmentMapper;
import com.atguigu.crud.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: Albert
 * @Date: 2018/8/16 23:03
 * @Description:
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public List<Department> getDepts() {
        List<Department> listDepts = departmentMapper.selectByExample(null);
        return listDepts;
    }
}
