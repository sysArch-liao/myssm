package com.atguigu.crud.service.impl;

import com.atguigu.crud.bean.Employee;
import com.atguigu.crud.bean.EmployeeExample;
import com.atguigu.crud.dao.EmployeeMapper;
import com.atguigu.crud.service.EmployeeService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: Albert
 * @Date: 2018/8/12 21:18
 * @Description:
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper<Employee> employeeMapper;


    /**
     * 查询所有员工
     * @return
     */
    public List<Employee> getAll() {
        // TODO Auto-generated method stub
        return employeeMapper.selectByExampleWithDept(null);
    }

    @Override
    public Boolean saveEmp(Employee employee) {
        return employeeMapper.insertSelective(employee) > 0;
    }

    @Override
    public boolean checkEmpName(String empName) {
        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        criteria.andEmpNameEqualTo(empName);
        return employeeMapper.countByExample(example) == 0;
    }

    @Override
    public Employee getEmpById(Integer id) {
        return employeeMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean updateEmpById(Employee employee) {

        return employeeMapper.updateByPrimaryKeySelective(employee) > 0;
    }

    @Override
    public boolean deleteEmpByIdList(List listIds) {

        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        criteria.andEmpIdIn(listIds);
        return employeeMapper.deleteByExample(example) > 0;
    }

    @Override
    public boolean deleteEmpById(Integer id) {
        return employeeMapper.deleteByPrimaryKey(id) > 0;
    }


    @Override
    public List<Employee> getAllEmps() {
        return employeeMapper.selectAllEmps();
    }

}
