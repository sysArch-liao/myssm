<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/9/18
  Time: 21:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
测试测试

<script src="<%=request.getContextPath()%>/static/scripts/jquery-1.12.4.min.js"></script>
<script>
    $(document).ready(function () {
        var day = new Date();
        var seperator = "-";
        var year = day.getFullYear();
        var month = day.getMonth() + 1;
        var today = day.getDate();
        if(month >=1 && month <= 9){
            month = "0" + month;
        }
        if(today >=1 && today <= 9){
            today = "0" + today;
        }

        //  今天
        var formatDay = year + seperator + month + seperator + today;

        //  当月第一天
        var formatToday = year + seperator + month + seperator + "01";

        //  当月最后一天
        var lastday = new Date(year, month, 0);
        var formatLastday = year + seperator + month + seperator + lastday.getDate();
        alert(formatLastday);
    });
</script>
</body>
</html>
