package com.atguigu.crud.controller;

import com.atguigu.crud.bean.Department;
import com.atguigu.crud.bean.Msg;
import com.atguigu.crud.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Auther: Albert
 * @Date: 2018/8/16 22:53
 * @Description:  处理部门有关的请求
 */
@Controller
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @ResponseBody
    @RequestMapping("/getDepts")
    public Msg getDepts(){
        List<Department> listDepts = departmentService.getDepts();
        return Msg.success().add("listDepts", listDepts);
    }

}
