<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="utf-8">
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
</head>
<body>
<h1>${shoudao}</h1>

<table align="center">
    <%-- 得到当前页--%>
    <span id="dqPage" hidden="hidden" class="disabled1 current">${page}</span>
    <span id="pageCount" hidden="hidden" class="disabled1 current">${pageCount}</span>
    <%-- js控制的页码显示在这个div中--%>
    <div id="pageBtn" style="width: auto;display:inline-block !important;height: auto;">
    </div>

</table>

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
<script type="text/javascript">
    //页码显示
    $(function(){

        var dqPage = $("#dqPage").text();//得到当前页数
        dqPage = parseInt(dqPage);//得到的文本转成int
        var pageCount = $("#pageCount").text();//得到总页数
        pageCount = parseInt(pageCount);
        var i = 1;
        i = parseInt(i);
        var item="";
        var href = "这里是请求地址";
        if (pageCount <= 5 ) {//总页数小于五页，则加载所有页

            for (i; i <= pageCount; i++) {
                if (i == dqPage) {
                    item += "<span class='disabled'>"+i+"</span>";
                }else{
                    item += "<a href='"+href+i+"' >"+i+"</a>";
                }
            };
            $('#pageBtn').append(item);
            return;
        }else if (pageCount > 5) {//总页数大于五页，则加载五页
            if (dqPage < 5) {//当前页小于5，加载1-5页
                for (i; i <= 5; i++) {
                    if (i == dqPage) {
                        item += "<span class='disabled'>"+i+"</span>";
                    }else{
                        item += "<a href='"+href+i+"' >"+i+"</a>";
                    }
                };
                if (dqPage <= pageCount-2) {//最后一页追加“...”代表省略的页
                    item += "<span> . . . </span>";
                }
                $('#pageBtn').append(item);
                return;
            }else if (dqPage >= 5) {//当前页大于5页
                for (i; i <= 2; i++) {//1,2页码始终显示
                    item += "<a href='"+href+i+"' >"+i+"</a>";
                }
                item += "<span> . . . </span>";//2页码后面用...代替部分未显示的页码
                if (dqPage+1 == pageCount) {//当前页+1等于总页码
                    for(i = dqPage-1; i <= pageCount; i++){//“...”后面跟三个页码当前页居中显示
                        if (i == dqPage) {
                            item += "<span class='disabled'>"+i+"</span>";
                        }else{
                            item += "<a href='"+href+i+"' >"+i+"</a>";
                        }
                    }
                }else if (dqPage == pageCount) {//当前页数等于总页数则是最后一页页码显示在最后
                    for(i = dqPage-2; i <= pageCount; i++){//...后面跟三个页码当前页居中显示
                        if (i == dqPage) {
                            item += "<span class='disabled'>"+i+"</span>";
                        }else{
                            item += "<a href='"+href+i+"' >"+i+"</a>";
                        }
                    }
                }else{//当前页小于总页数，则最后一页后面跟...
                    for(i = dqPage-1; i <= dqPage+1; i++){//dqPage+1页后面...
                        if (i == dqPage) {
                            item += "<span class='disabled'>"+i+"</span>";
                        }else{
                            item += "<a href='"+href+i+"' >"+i+"</a>";
                        }
                    }
                    item += "<span> . . . </span>";
                }
                $('#pageBtn').append(item);
                return;
            }
        }

    });
</script>
</html>