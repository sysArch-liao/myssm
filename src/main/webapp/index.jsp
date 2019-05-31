<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>员工列表</title>
    <%
        pageContext.setAttribute("APP_PATH", request.getContextPath());
    %>
    <!-- web路径：
    不以/开始的相对路径，找资源，以当前资源的路径为基准，经常容易出问题。
    以/开始的相对路径，找资源，以服务器的路径为标准(http://localhost:3306)；需要加上项目名
            http://localhost:3306/crud
     -->
    <script type="text/javascript"
            src="${APP_PATH}/static/scripts/jquery-1.12.4.min.js"></script>
    <link
            href="${APP_PATH}/static/bootstrap-3.3.7-dist/css/bootstrap.css"
            rel="stylesheet">
    <script
            src="${APP_PATH}/static/bootstrap-3.3.7-dist/js/bootstrap.js"></script>
</head>
<body>


<!-- 编辑员工的模态框 start -->
<div class="modal fade" id="update_emp_div" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="update_h4">编辑员工</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label for="empName_add_input" class="col-sm-2 control-label">员工名字</label>
                        <div class="col-sm-10">
                            <p class="form-control-static" id="empName_update_input"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="email_add_input" class="col-sm-2 control-label">邮箱</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="email" id="email_update_input" >
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">性别</label>
                        <div class="col-sm-10">
                            <label class="radio-inline">
                                <input type="radio" checked="checked" name="gender" id="gender1_update_input" value="M"> 男
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="gender2_update_input" value="F"> 女
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">部门名称</label>
                        <div class="col-sm-4">
                            <select class="form-control" name="dId" id="dept_update_select">

                            </select>
                        </div>
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="emp_update_btn">编辑</button>
            </div>
        </div>
    </div>
</div>
<!-- 编辑员工的模态框 end -->

<!-- 新增员工的模态框 start -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Modal title</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label for="empName_add_input" class="col-sm-2 control-label">员工名字</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="empName" id="empName_add_input" placeholder="请填写员工名字">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="email_add_input" class="col-sm-2 control-label">邮箱</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="email" id="email_add_input" placeholder="请填写邮箱">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">性别</label>
                        <div class="col-sm-10">
                            <label class="radio-inline">
                                <input type="radio" checked="checked" name="gender" id="gender1_add_input" value="M"> 男
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="gender2_add_input" value="F"> 女
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">部门名称</label>
                        <div class="col-sm-4">
                            <select class="form-control" name="dId" id="dept_add_select">

                            </select>
                        </div>
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="emp_save_btn">保存</button>
            </div>
        </div>
    </div>
</div>
<!-- 新增员工的模态框 end -->

<!-- 搭建显示页面 -->
<div class="container">
    <!-- 标题 -->
    <div class="row">
        <div class="col-md-12">
            <h1>SSM-CRUD</h1>
            <%--验证码--%>
            <img src="${APP_PATH}/employeeDisplay/captcha.jpg" onclick="this.src='${APP_PATH}/employeeDisplay/captcha.jpg'" id="captcha" alt="aaa">
        </div>
    </div>
    <!-- 按钮 -->
    <div class="row">
        <div class="col-md-4 col-md-offset-8">
            <button class="btn btn-primary" id="emp_add_modal_btn">新增</button>
            <button class="btn btn-danger" id="emp_delete_all_btn">删除</button>
        </div>
    </div>
    <!-- 显示表格数据 -->
    <div class="row">
        <div class="col-md-12">
            <table class="table table-hover" id="emps_table">
                <thead>
                <tr>
                    <th>
                        <input type="checkbox" id="check_all"/>
                    </th>
                    <th>#</th>
                    <th>empName</th>
                    <th>gender</th>
                    <th>email</th>
                    <th>deptName</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>

                </tbody>
            </table>
        </div>
    </div>

    <!-- 显示分页信息 -->
    <div class="row">
        <!--分页文字信息  -->
        <div class="col-md-6" id="page_info_area"></div>
        <!-- 分页条信息 -->
        <div class="col-md-6" id="page_nav_area">

        </div>
    </div>
</div>

<script>
    var totalRecord;
    var currentPage;
    $(function () {
        //  第一页
        toPage(1)
    })

    function toPage(pn) {
        $.ajax({
            url:"${APP_PATH}/employeeDisplay/emps",
            data:"pn=" + pn,
            type:"GET",
            success:function(result){
                //1、解析并显示员工数据
                build_emps_table(result);
                //2、解析并显示分页信息
                build_page_info(result);
                //3、解析显示分页条数据
                build_page_nav(result);
            }
        })
    }

    function build_emps_table(result){
        //清空table表格
        $("#emps_table tbody").empty();
        var emps = result.extend.pageInfo.list;
        $.each(emps, function (index, item) {
            var cheak_item = $("<td><input type='checkbox' class='check_item'/></td>");
            var empIdTd = $("<td></td>").append(item.empId);
            var empNameTd = $("<td></td>").append(item.empName);
            var genderTd = $("<td></td>").append(item.gender == 'M' ? "男" : "女");
            var emailTd = $("<td></td>").append(item.email);
            var deptNameTd = $("<td></td>").append(item.department.deptName);
            var editBtn = $("<button></button>").addClass("btn btn-primary btn-sm update_btn").append($("<span></span>").append("编辑").addClass("glyphicon glyphicon-pencil"));
            //为编辑按钮添加一个自定义的属性，来表示当前员工id
            editBtn.attr("update_id", item.empId);
            var delBtn = $("<button></button>").addClass("btn btn-danger btn-sm delete_btn").append($("<span></span>").append("删除").addClass("glyphicon glyphicon-trash"));
            //为删除按钮添加一个自定义的属性来表示当前删除的员工id
            delBtn.attr("delete_id", item.empId);
            var operationBtn = $("<td></td>").append(editBtn).append(" ").append(delBtn);
            //append方法执行完成以后还是返回原来的元素,所以可以一直.append操作
            $("<tr></tr>").append(cheak_item)
                .append(empIdTd)
                .append(empNameTd)
                .append(genderTd)
                .append(emailTd)
                .append(deptNameTd)
                .append(operationBtn)
                .appendTo("#emps_table tbody");

        })
    }

    //解析显示分页信息
    function build_page_info(result){
        $("#page_info_area").empty();
        $("#page_info_area").append("当前"+result.extend.pageInfo.pageNum+"页,总"+
            result.extend.pageInfo.pages+"页,总"+
            result.extend.pageInfo.total+"条记录");
        totalRecord = result.extend.pageInfo.total;
        currentPage = result.extend.pageInfo.pageNum;
    }

    //解析显示分页条，点击分页要能去下一页....
    function build_page_nav(result){
        $("#page_nav_area").empty();
        var ul = $("<ul></ul>").addClass("pagination");
        //构建元素
        var firstPageLi = $("<li></li>").append($("<a></a>").append("首页").attr("href", "#"));
        var prePageLi = $("<li></li>").append($("<a></a>").append("&laquo;"));
        if(result.extend.pageInfo.hasPreviousPage == false){
            prePageLi.addClass("disabled");
            firstPageLi.addClass("disabled");
        }else{
            //为元素添加点击翻页的事件
            firstPageLi.click(function () {
                toPage(1)
            });
            prePageLi.click(function () {
                toPage(result.extend.pageInfo.pageNum - 1);
            });
        }

        ul.append(firstPageLi).append(prePageLi);
        var nextPageLi = $("<li></li>").append($("<a></a>").append("&raquo;"));
        var lastPageLi = $("<li></li>").append($("<a></a>").append("尾页").attr("href", "#"));

        if(result.extend.pageInfo.hasNextPage == false){
            nextPageLi.addClass("disabled");
            lastPageLi.addClass("disabled");
        }else{
            lastPageLi.click(function () {
                toPage(result.extend.pageInfo.pages)
            });
            nextPageLi.click(function () {
                toPage(result.extend.pageInfo.pageNum + 1);
            });
        }
        $.each(result.extend.pageInfo.navigatepageNums, function (index, item) {
            var numLi = $("<li></li>").append($("<a></a>").append(item));
            if(result.extend.pageInfo.pageNum == item){
                numLi.addClass("active");
            }
            numLi.click(function () {
                toPage(item);
            });
            ul.append(numLi);
        });
        ul.append(nextPageLi).append(lastPageLi);
        var navEle = $("<nav></nav>").append(ul);
        navEle.appendTo("#page_nav_area");
    }

    //清空表单样式及内容
    function reset_form(element) {
        $(element)[0].reset();
        $(element).find("*").removeClass("has-error has-success");
        $(element).find(".help-block").text("");
    }

    //  点击新增按钮弹出模态框
    $("#emp_add_modal_btn").click(function () {

        reset_form("#myModal form");
        // $("#myModal form")[0].reset();
        //  发送ajax,查出部门信息，显示在下拉列表中
        getDepts("#myModal select");

        //  弹出模态框
         $("#myModal").modal({
             backdrop:"static",
             keyboard:false
         });
    })

    //  查出所有的部门信息并显示在下拉列表中
    function getDepts(element) {
        $(element).empty();
        $.ajax({
            url:"${APP_PATH}/department/getDepts",
            type:"GET",
            success:function (result) {
                $("#myModal select").empty();
                //显示部门信息在下拉列表中
                $.each(result.extend.listDepts,function () {
                    var option = $("<option></option>").attr("value",this.deptId).append(this.deptName);
                    option.appendTo(element);
                })
            }
        })
    }

    //  校验用户名是否可用
    $("#empName_add_input").change(function () {
        //发送ajax请求校验用户名是否可用
        var empName = $(this).val();
        $.ajax({
            url:"${APP_PATH}/employeeDisplay/checkEmpName",
            data:"empName="+empName,
            type:"post",
            success:function (result) {
                console.log(result);
                if(result.code == 100){
                    show_validate_msg("#empName_add_input", "success", "用户名可用");
                    $("#emp_save_btn").attr("mydata", "success");
                }else{
                    show_validate_msg("#empName_add_input", "error", result.extend.va_msg);
                    $("#emp_save_btn").attr("mydata", "error");
                }
            }
        })
    })

    //校验表单数据
    function validate_add_form() {
        //1、拿到要校验的数据，使用正则表达式
        var empName = $("#empName_add_input").val();
        var regName = /(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})/;
        if(!regName.test(empName)){
            show_validate_msg("#empName_add_input", "error", "有户名只能是2到5个中文或者6个英文和数字的组合");
            return false;
        }else {
            show_validate_msg("#empName_add_input", "success", "");
        }
        //2、校验邮箱信息
        var email = $("#email_add_input").val();
        var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
        if(!regEmail.test(email)){
            //应该清空这个元素之前的样式
            show_validate_msg("#email_add_input", "error", "邮箱格式不正确");
            return false;
        }else {
            show_validate_msg("#email_add_input", "success", "");
        }
        return true;
    }

    //显示校验结果的提示信息
    function show_validate_msg(element, status, msg) {
        //清除当前元素的校验状态
        $(element).parent().removeClass("has-error has-success")
        $(element).next("span").text("");
        if(status == "error"){
            $(element).parent().addClass("has-error");
            $(element).next("span").text(msg);
        }else if(status == "success"){
            $(element).parent().addClass("has-success");
            $(element).next("span").text(msg);
        }
    }

    //点击保存，保存员工。
    $("#emp_save_btn").click(function () {
        //1、模态框中填写的表单数据提交给服务器进行保存
        //1、先对要提交给服务器的数据进行校验
        if(!validate_add_form()){
            // return false;
        }
        //1、判断之前的ajax用户名校验是否成功。如果成功。
        var flag = $("#emp_save_btn").attr("mydata");
        if(flag  == "error"){
            return false;
        }
        //  模态框填写表单的信息提交给服务器进行保存，发送ajax请求保存员工
        var datas = $("#myModal form").serialize();
        //2、发送ajax请求保存员工
        $.ajax({
            url:"${APP_PATH}/employeeDisplay/emp",
            type:"POST",
            data:datas,
            dataType:"json",
            success:function (result) {
                if(result.code == 100){
                    //员工保存成功；
                    //1、关闭模态框
                    $("#myModal").modal("hide");
                    <!--当传入的页码大于最大的页数时，插件会自动返回最后一页，所以传入总记录数肯定会大于总页数，使得查询的是最后一页-->
                    <!-- 但是必须要在myBatis有这个配置： 分页参数合理化<property name="reasonable" value="true"/>  -->
                    toPage(totalRecord);
                }else{
                    //显示失败信息
                    //console.log(result);
                    //有哪个字段的错误信息就显示哪个字段的；
                    if(undefined != result.extend.errorFields.email){
                        //显示邮箱错误信息
                        show_validate_msg("#email_add_input", "error", result.extend.errorFields.email);
                    }
                    if(undefined != result.extend.errorFields.empName){
                        //显示员工名字的错误信息
                        show_validate_msg("#empName_add_input", "error", result.extend.errorFields.empName);
                    }
                }
            }
        })
    });

    //1、我们是按钮创建之前就绑定了click，所以绑定不上。
    //1）、可以在创建按钮的时候绑定。    2）、绑定点击.live()
    //jquery新版没有live，使用on进行替代
    $(document).on("click", ".update_btn", function () {
        //1、查出部门信息，并显示部门列表
        getDepts("#update_emp_div select");
        //2、查出员工信息，显示员工信息
        getEmpById($(this).attr("update_id"));
        $("#emp_update_btn").attr("edit_id", $(this).attr("update_id"));
        //  弹出模态框
        $("#update_emp_div").modal({
            backdrop:"static",
            keyboard:false
        });
    })

    function getEmpById(id) {
        $.ajax({
            url:"${APP_PATH}/employeeDisplay/getEmpById/"+id,
            type:"get",
            success:function (result) {
               $("#empName_update_input").text(result.extend.emp.empName);
               $("#email_update_input").val(result.extend.emp.email);
               $("#update_emp_div input[name=gender]").val([result.extend.emp.gender]);
               $("#update_emp_div select").val([result.extend.emp.dId]);
            }
        })
    }

    //点击更新，更新员工信息
    $("#emp_update_btn").click(function () {
        //验证邮箱是否合法
        //1、校验邮箱信息
        var email = $("#email_update_input").val();
        var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
        if(!regEmail.test(email)){
            show_validate_msg("#email_update_input", "error", "邮箱格式不正确");
            return false;
        }else {
            show_validate_msg("#email_update_input", "success", "");
        }
        //2、发送ajax请求保存更新的员工数据
        $.ajax({
            url:"${APP_PATH}/employeeDisplay/updateEmpById/"+$(this).attr("edit_id"),
            type:"put",
            data:$("#update_emp_div form").serialize(),
            success:function (result) {
                //alert(result.msg);
                //1、关闭对话框
                $("#update_emp_div").modal("hide");
                //2、回到本页面
                toPage(currentPage);
            }
        })

    });

    //单个删除
    $(document).on("click", ".delete_btn", function () {
        var empName = $(this).parents("tr").find("td:eq(2)").text();
        if(confirm("确定要删除【"+empName+"】吗？")){
            //确认，发送ajax请求删除即可
            $.ajax({
                url:"${APP_PATH}/employeeDisplay/deleteEmpById/"+$(this).attr("delete_id"),
                type:"delete",
                success:function (result) {
                    alert(result.msg);
                    toPage(currentPage);
                }
            })
        }
    })

    //完成全选/全不选功能
    $("#check_all").click(function () {
        //attr获取checked是undefined;
        //我们这些dom原生的属性；attr获取自定义属性的值；
        //prop修改和读取dom原生属性的值
        $(".check_item").prop("checked", $(this).prop("checked"));
    });

    $(document).on("click", ".check_item", function () {
        // 判断当前选中的元素是否等于所有行
        var flag = $(".check_item:checked").length == $(".check_item").length;
        $("#check_all").prop("checked", flag);
    })

    //点击全部删除，就批量删除
    $("#emp_delete_all_btn").click(function () {
        var empNames = "";
        var ids = "";
        $.each($(".check_item:checked"), function () {
            empNames += $(this).parents("tr").find("td:eq(2)").text()+",";
            //组装员工id字符串
            ids += $(this).parents("tr").find("td:eq(1)").text()+"-";
        });
        //去除empNames多余的,
        empNames = empNames.substring(0, empNames.length - 1);
        //去除删除的id多余的-
        ids = ids.substring(0, ids.length - 1);
        if(confirm("确定删除【"+empNames+"】吗？")){
            //发送ajax请求删除
            $.ajax({
                url:"${APP_PATH}/employeeDisplay/deleteEmpById/"+ ids,
                type:"delete",
                success:function (result) {
                    alert(result.msg);
                    //回到当前页面
                    toPage(currentPage);
                }
            })
        }
    });

</script>

</body>
</html>