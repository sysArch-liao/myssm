package com.atguigu.crud.test;

import com.atguigu.crud.bean.Employee;
import com.atguigu.crud.dao.EmployeeMapper;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.xssf.usermodel.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

/**
 * @Auther: Albert
 * @Date: 2018/10/21 19:58
 * @Description:
 */
@Service
public class POIDemoWriteExcel {
    @Autowired
    EmployeeMapper employeeMapper;

    List<Employee> employeeList = null;

    @Test
    public static void main1(String[] args){
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(new File("D:\\poiDemo\\我成功了，我很棒.xlsx"));
            writeExcel(outputStream);
        }catch (FileNotFoundException e){
            System.out.println(e.getStackTrace());
        }catch (Exception ioexcepton){
            System.out.println(ioexcepton.getStackTrace());
        }
    }

    public static void writeExcel(OutputStream outputStream)throws Exception{
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("我的sheet");
        XSSFRow row = sheet.createRow(0);
        XSSFCell cell  = row.createCell(0);
        cell.setCellValue(new XSSFRichTextString("我的饿excel内容"));
        workbook.write(outputStream);
    }
}
