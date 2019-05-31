package com.atguigu.crud.service;

import com.atguigu.crud.bean.Employee;

import java.util.List;

/**
 * @Auther: Albert
 * @Date: 2018/8/12 21:18
 * @Description:
 */
public interface EmployeeService {
    List getAllEmps();

    List<Employee> getAll();

    Boolean saveEmp(Employee employee);

    boolean checkEmpName(String empName);

    Employee getEmpById(Integer id);

    boolean updateEmpById(Employee employee);

    boolean deleteEmpById(Integer id);

    boolean deleteEmpByIdList(List listIds);
}
