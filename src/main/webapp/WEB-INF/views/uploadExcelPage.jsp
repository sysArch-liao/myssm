<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/10/31
  Time: 20:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%
        pageContext.setAttribute("APP_PATH", request.getContextPath());
    %>
</head>
<body>
    <h2>excel的上传文件</h2>
    <form enctype="multipart/form-data" method="post" action="${APP_PATH}/employeeDisplay/uploadExcel">
        文件上传: <input type="file" name="excelFile" multiple="multiple">
        <input type="submit" name="submit" value="提交">
    </form>
</body>
</html>
