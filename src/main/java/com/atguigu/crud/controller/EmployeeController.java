package com.atguigu.crud.controller;

import com.atguigu.crud.annotation.SysLog;
import com.atguigu.crud.bean.Employee;
import com.atguigu.crud.bean.Msg;
import com.atguigu.crud.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.code.kaptcha.Producer;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

/**
 * @Auther: Albert
 * @Date: 2018/8/12 21:12
 * @Description:
 */
@Controller
@RequestMapping("/employeeDisplay")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;
    @Resource
    private Producer producer;

    @RequestMapping("/testRestTemplate")
    public  void testFun(HttpServletRequest request, HttpServletResponse response){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://git.gupaoedu.com/vip/nice/issues/185";
        ResponseEntity responseEntity = new ResponseEntity(HttpStatus.MULTI_STATUS);
        ResponseEntity<ResponseEntity> responseEntityResponseEntity = restTemplate.postForEntity(url, request, ResponseEntity.class);
        System.out.println(responseEntityResponseEntity.toString());
    }

    @ResponseBody
    @RequestMapping("/emps")
    public Msg getEmpsWithJson(@RequestParam(value = "pn", defaultValue = "1") Integer pn){
        PageHelper.startPage(pn, 5);
        // startPage后面紧跟的这个查询就是一个分页查询
        List<Employee> emps = employeeService.getAll();
        // 使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了。
        // 封装了详细的分页信息,包括有我们查询出来的数据，传入连续显示的页数
        PageInfo<Employee> page = new PageInfo<>(emps, 5);
        return Msg.success().add("pageInfo", page);
    }

    @GetMapping("/captcha.jpg")
    public void captcha(HttpServletResponse response, String uuid)throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        //获取图片验证码
        //生成文字验证码
        String code = producer.createText();
        System.out.println("验证码："+code);
        BufferedImage image = producer.createImage(code);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }

    /**
     * 查询员工数据（分页查询）
     *
     * @return
     */
//     @RequestMapping("/emps")
    public String getEmps(@RequestParam(value = "pn", defaultValue = "1") Integer pn, Model model) {
        // 这不是一个分页查询；
        // 引入PageHelper分页插件
        // 在查询之前只需要调用，传入页码，以及每页的大小
        PageHelper.startPage(pn, 5);
        // startPage后面紧跟的这个查询就是一个分页查询
        List<Employee> emps = employeeService.getAll();
        // 使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了。
        // 封装了详细的分页信息,包括有我们查询出来的数据，传入连续显示的页数
        PageInfo page = new PageInfo(emps, 5);
        model.addAttribute("pageInfo", page);
        return "list";
    }

    @SysLog("哈哈哈")
    @GetMapping("/getAllEmps")
    public String getAllEmps(Model model){
        List<Employee> listEmps = employeeService.getAllEmps();
        model.addAttribute("listEmps", listEmps);
        return "list";
    }

    /**
     *
     * 功能描述:
     *
     * @param: [employee]
     * @return: com.atguigu.crud.bean.Msg
     * @auther: Albert
     * @date: 2018/8/19 8:48
     */
    @PostMapping("/emp")
    @ResponseBody
    public Msg saveEmp(@Valid Employee employee, BindingResult result){
        if(result.hasErrors()){
            //校验失败，应该返回失败，在模态框中显示校验失败的错误信息
            Map<String, Object> map = new HashMap<>();
            List<FieldError> errors = result.getFieldErrors();
            for(FieldError fieldError : errors){
                System.out.println("错误的字段名："+fieldError.getField());
                System.out.println("错误信息："+fieldError.getDefaultMessage());
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return Msg.fail().add("errorFields", map);
        }else {
            employeeService.saveEmp(employee);
            return Msg.success();
        }
    }


    @ResponseBody
    @PostMapping("/checkEmpName")
    public Msg checkEmpName(@RequestParam("empName") String empName){
        String regEmpName = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5})";
        if(!empName.matches(regEmpName)){
            return Msg.fail().add("va_msg", "有户名只能是2到5个中文或者6个英文和数字的组合");
        }
        boolean flag = employeeService.checkEmpName(empName);
        if(flag){
            return Msg.success();
        }
        return Msg.fail().add("va_msg", "用户名不可用");
    }

    @ResponseBody
    @GetMapping("/getEmpById/{id}")
    public Msg getEmpById(@PathVariable("id") Integer id){
        Employee employee = employeeService.getEmpById(id);
        return Msg.success().add("emp", employee);
    }

    @ResponseBody
    @PutMapping("/updateEmpById/{empId}")
    public Msg updateEmpById(Employee employee){
        System.out.println("名字："+employee.getGender());
        System.out.println("名字："+employee.getEmail());
        employeeService.updateEmpById(employee);
        return Msg.success();
    }

    /**
     * 单个批量二合一
     * 批量删除：1-2-3
     * 单个删除：1
     *
     * @param ids
     * @return
     */
    @ResponseBody
    @DeleteMapping("/deleteEmpById/{ids}")
    public Msg deleteEmpById(@PathVariable String ids){
        List<Integer> listIds = new ArrayList<>();
        String[] strIds = ids.split("-");
        for(String strId : strIds){
            listIds.add(Integer.parseInt(strId));
        }
        if(ids.contains("-")){
            employeeService.deleteEmpByIdList(listIds);
        }else{
            employeeService.deleteEmpById(Integer.parseInt(ids));
        }
        return Msg.success();
    }

    @GetMapping("/writeExcelDemo")
    public void writeExcelDemo() throws Exception{
        OutputStream outputStream = new FileOutputStream(new File("D:\\poiDemo\\我的excel写出成了.xlsx"));
        List<Employee> employeeList = employeeService.getAll();
        Workbook workbook = new XSSFWorkbook();
        Font font = workbook.createFont();
        font.setBold(true);
        CellStyle cellStyle = workbook.createCellStyle();
        CellStyle titileCellStyle = workbook.createCellStyle();
        titileCellStyle.setFont(font);
        titileCellStyle.setBorderTop(BorderStyle.THIN);
        titileCellStyle.setBorderBottom(BorderStyle.THIN);
        titileCellStyle.setBorderLeft(BorderStyle.THIN);
        titileCellStyle.setBorderRight(BorderStyle.THIN);
        titileCellStyle.setAlignment(HorizontalAlignment.CENTER);
        titileCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        Sheet sheet = workbook.createSheet("第一个sheet成功了");
        sheet.createFreezePane(5, 1, 5, 1);
        Row rowTitle = sheet.createRow(0);
        Cell cell0 = rowTitle.createCell(0);
        cell0.setCellValue("姓名");
        cell0.setCellStyle(titileCellStyle);
        Cell cell1 = rowTitle.createCell(1);
        cell1.setCellValue("性别");
        cell1.setCellStyle(titileCellStyle);
        Cell cell2 = rowTitle.createCell(2);
        cell2.setCellValue("邮箱");
        cell2.setCellStyle(titileCellStyle);
        Cell cell3 = rowTitle.createCell(3);
        cell3.setCellValue("部门");
        cell3.setCellStyle(titileCellStyle);
        Cell cell4 = rowTitle.createCell(4);
        cell4.setCellValue("操作");
        cell4.setCellStyle(titileCellStyle);
        Cell cell = null;
        Row row = null;
        Employee  employee = null;
        for (int empNum=0; empNum<employeeList.size(); empNum++){
            row = sheet.createRow(empNum+1);
//            row.setHeight((short)5);
            row.setHeightInPoints((float)20);
            employee = employeeList.get(empNum);
            for (int cellNum=0; cellNum<5; cellNum++){
                cell = row.createCell(cellNum);
                switch (cellNum){
                    case 0:
                        cell.setCellValue(employee.getEmpName());
                        cell.setCellStyle(cellStyle);
                        break;
                    case 1:
                        String sex = Objects.equals("M", employee.getGender())?"男":"女";
                        cell.setCellValue(sex);
                        cell.setCellStyle(cellStyle);
                        break;
                    case 2:
                        cell.setCellValue(employee.getEmail());
                        sheet.setColumnWidth(2, 23*256);
                        cell.setCellStyle(cellStyle);
                        break;
                    case 3:
                        cell.setCellValue(employee.getDepartment().getDeptName());
                        cell.setCellStyle(cellStyle);
                        break;
                    case 4:
                        cell.setCellValue("修改");
                        cell.setCellStyle(cellStyle);
                        break;
                    default:
                }
            }
        }
        workbook.write(outputStream);
    }

    @GetMapping("/studyPOI")
    public void studyPOI(HttpServletResponse response)throws Exception{
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet1 = workbook.createSheet("第一个sheet");
        CellStyle titleCellStyle = workbook.createCellStyle();
        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleCellStyle.setFont(titleFont);
        titleCellStyle.setBorderRight(BorderStyle.THIN);
        titleCellStyle.setBorderLeft(BorderStyle.THIN);
        titleCellStyle.setBorderBottom(BorderStyle.THIN);
        titleCellStyle.setBorderTop(BorderStyle.THIN);
        titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
        titleCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        for (int rowNum=0; rowNum <= 7; rowNum++){
            Row row = sheet1.createRow(rowNum);
            for (int colNum=1; colNum<=4; colNum++){
                if (0 == rowNum){
                    Cell cell00 = row.createCell(colNum);
                    cell00.setCellValue("大标题");
                    cell00.setCellStyle(titleCellStyle);
                    continue;
                }
                if (1<=rowNum && 3>=rowNum && 1==colNum){
                    Cell cell = row.createCell(colNum);
                    cell.setCellValue("哈哈");
                    cell.setCellStyle(cellStyle);
                    continue;
                }
                if (1<=rowNum && 2>=rowNum && 2<=colNum && 3>=colNum){
                    Cell cell = row.createCell(colNum);
                    cell.setCellValue("二标题");
                    cell.setCellStyle(cellStyle);
                    continue;
                }
                if (1<=rowNum && 3>=rowNum && 4==colNum){
                    Cell cell = row.createCell(colNum);
                    cell.setCellValue("嘻嘻");
                    cell.setCellStyle(cellStyle);
                    continue;
                }
                Cell cell = row.createCell(colNum);
                cell.setCellValue("单元格");
                cell.setCellStyle(cellStyle);
            }
        }
        sheet1.addMergedRegion(new CellRangeAddress(0, 0, 1, 4));
        sheet1.addMergedRegion(new CellRangeAddress(1, 3, 1, 1));
        sheet1.addMergedRegion(new CellRangeAddress(1, 2, 2, 3));
        sheet1.addMergedRegion(new CellRangeAddress(1, 3, 4, 4));

//      可以下载excel表格，如果浏览器设置了下载询问保存路径时，则会保存并设置保存的路径
        String fileName = new String("我的aa表格.xlsx".getBytes(), "ISO8859-1");
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
        response.addHeader("Pargam", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
        OutputStream os = response.getOutputStream();
        workbook.write(os);
        os.flush();
        os.close();


    }

    @GetMapping("/uploadExcelPage")
    public String uploadExcelPage(){
        return "uploadExcelPage";
    }

    @GetMapping("/uploadUploadImg")
    public String uploadUploadImg(){
        return "uploadImg";
    }

    @PostMapping("/uploadImg")
    public void uploadImg(MultipartFile imgFile) {
        System.out.println("*******************");
        System.out.println("*******************");
        System.out.println("*******************");
    }

    @PostMapping("/uploadExcel")
    public void uploadExcel(List<MultipartFile> excelFile, HttpServletRequest request)throws Exception{
        String contextPath = request.getContextPath();
        String excelFileName = excelFile.get(0).getOriginalFilename();
        InputStream inputStream = excelFile.get(0).getInputStream();
        String basePath = request.getSession().getServletContext().getRealPath("static/upload");
        resolverExcel(inputStream, excelFileName, basePath);
    }

    public void resolverExcel(InputStream inputStream, String excelFileName, String contextPath)throws Exception{
        InputStream is = inputStream;
        String basePath = "src\\main\\resources\\";
        File fileName = new File(basePath+excelFileName);
        OutputStream outputStream = new FileOutputStream(fileName);
        byte[] bytes = new byte[1024*5];
        int len = 0;
        while ((len = inputStream.read(bytes)) != -1){
            outputStream.write(bytes, 0, len);
        }
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheet("第一个sheet成功了");
        Row row0 = sheet.getRow(0);
        for (int col=0; col<row0.getLastCellNum(); col++){
            System.out.println("单元格:"+row0.getCell(col).getStringCellValue());
        }

        outputStream.flush();
        inputStream.close();
        outputStream.flush();
    }

    @SysLog("我的第一个注解成功了")
    @GetMapping("/sysLog")
    public void sysLog(){
        System.out.println("进来了");

    }

}
