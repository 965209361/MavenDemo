<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>SpringMVC Demo 首页</title>

    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style>
        ul {
            height: 100%;
            list-style-type: none;
        }

        li {
            line-height: 40px;
            float: left;
        }

        .page_btn {
            border-radius: 4px;
            border: 1px solid #e5e9ef;
            background: #fff;
            margin-right: 10px;
            text-align: center;
            width: 38px;
            height: 38px;
            line-height: 8px;
            margin-top: 6px;
            outline: 0;
        }

        .page_btn:hover {
            border: 1px solid #4f90fb;
            color: #4f90fb;
        }

        span.pages_span {
            margin-right: 10px;
            width: 38px;
            height: 38px;
            position: relative;
            top: 10px;
        }
    </style>
</head>
<body>
<h1>${shoudao}</h1>

<table align="center">
    <span id="dqPage" hidden="hidden" class="disabled1 current">${page}</span>
    <span id="pageCount" hidden="hidden" class="disabled1 current">${pageCount}</span>
    <ul th:align="center">
        <li class="page_li">
            <button class="page_btn" style="width:100px" id="prePage">上一页</button>
        </li>
        <li class="page_li">
            <button class="page_btn" id="page_btn1">1</button>
        </li>
        <li class="page_li">
            <span class="pages_span" id="prePoint">...</span>
        </li>
        <li class="page_li">
            <button class="page_btn" id="page_btn2"></button>
        </li>
        <li class="page_li">
            <button class="page_btn" id="page_btn3"></button>
        </li>
        <li class="page_li">
            <button class="page_btn" id="page_btn4"></button>
        </li>
        <li class="page_li">
            <button class="page_btn" id="page_btn5"></button>
        </li>
        <li class="page_li">
            <button class="page_btn" id="page_btn6"></button>
        </li>
        <li class="page_li">
            <span class="pages_span" id="sufPoint">...</span>
        </li>
        <li class="page_li">
            <button class="page_btn" id="page_btn7"></button>
        </li>
        <li class="page_li">
            <button class="page_btn" style="width:100px" id="sufPage">下一页</button>
        </li>
    </ul>
</table>
<div class="col-sm-5">
    <table width="600" border="1" align="center" cellspacing="0" id="table">
        <tr>
            <th>id</th>
            <th>listUrl</th>
            <th>title</th>
            <%--<th>content</th>--%>
            <%--<th>fbsj</th>--%>
            <%--<th>comment</th>--%>
            <%--<th>commentCount</th>--%>
        </tr>
        <tr th:each="user,start:${rows}">
            <td th:text="${user.id}"></td>
            <td th:text="${user.listUrl}"></td>
            <td th:text="${start.size}"></td>
            <%--<td th:text="${domain.content}"></td>--%>
            <%--<td th:text="${domain.fbsj}"></td>--%>
            <%--<td th:text="${domain.comment}"></td>--%>
            <%--<td th:text="${domain.commentCount}"></td>--%>
        </tr>
        <span th:each="user,start:${rows}"><h2 th:text="${start.size}"></h2></span>
    </table>
</div>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
<script type="text/javascript">
    $(function () {
        var currentPage = $("#dqPage").text();//得到当前页数
        currentPage = parseInt(currentPage);//得到的文本转成int
        var pageNum = $("#pageCount").text();//得到当前页数
        pageNum = parseInt(pageNum);//得到的文本转成int
        $("#page_btn2").text(currentPage - 2);
        $("#page_btn3").text(currentPage - 1);
        $("#page_btn4").text(currentPage);
        $("#page_btn5").text(currentPage + 1);
        $("#page_btn6").text(currentPage + 2);
        $("#page_btn7").text(pageNum);


        $("#page_btn4").css("background-color", "#4f90fb");
        $("#page_btn4").css("border", "1px solid #ddd");
        $("#page_btn4").css("color", "#fff");


        if (currentPage == 1) {
            $("#prePage").hide();
        }

        if (currentPage == pageNum) {
            $("#sufPage").hide();
        }


        if (currentPage <= 3) {
            $("#prePoint").hide();
            $("#page_btn1").hide();
        }
        else if (currentPage == 4) {
            $("#prePoint").hide();
        }

        if (currentPage == 1) {
            $("#page_btn2").hide();
            $("#page_btn3").hide();
        }
        else if (currentPage == 2) {
            $("#page_btn2").hide();
        }

        if (currentPage >= pageNum - 2) {
            $("#sufPoint").hide();
            $("#page_btn7").hide();
        }
        else if (currentPage == pageNum - 3) {
            $("#sufPoint").hide();
        }

        if (currentPage == pageNum) {
            $("#page_btn5").hide();
            $("#page_btn6").hide();
        }

        if (currentPage == pageNum - 1) {
            $("#page_btn6").hide();
        }
    });
</script>
</html>