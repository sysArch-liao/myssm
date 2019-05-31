package com.atguigu.crud.test;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Objects;

/**
 * @Auther: Albert
 * @Date: 2018/10/21 18:42
 * @Description:
 */
public class POIDemo {
    public static void main(String[] args){
        File file = new File("D:\\poiDemo\\demo.xls");
        String fileName = file.getName();
        String excelType = fileName.substring(fileName.lastIndexOf(".")+1);
        System.out.println(excelType);
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            readExcel(inputStream, excelType);
        } catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        } catch (IOException E){

        } finally {
            try {
                if (null != inputStream){
                    inputStream.close();
                }
            }catch (IOException e){
                System.out.println(e.getStackTrace());
            }
        }
    }

    public static void readExcel(InputStream inputStream, String excelType)throws IOException{
        Workbook workbook = null;
        if (Objects.equals("xlsx", excelType)){
            workbook = new XSSFWorkbook(inputStream);
        }else {
            workbook = new HSSFWorkbook(inputStream);
        }
        Sheet sheet = workbook.getSheetAt(0);
        Row row0 =  sheet.getRow(0);
        Cell cell00 = row0.getCell(5);
        System.out.println(cell00.getRichStringCellValue().toString());
    }

}
