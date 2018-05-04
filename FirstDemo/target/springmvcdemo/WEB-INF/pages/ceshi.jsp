<%--
  Created by IntelliJ IDEA.
  User: zengqt
  Date: 2018/5/2
  Time: 15:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Title</title>
</head>
<body>
<h1>${shoudao}</h1>
<table align="center" border="1">
    <thead>
    <tr>
        <th>id</th>
        <th>listUrl</th>
        <%--<th>title</th>--%>
        <%--<th>content</th>--%>
        <%--<th>fbsj</th>--%>
        <%--<th>comment</th>--%>
        <%--<th>commentCount</th>--%>
    </tr>
    <tr th:each="HeFeiBBS_Domain,iterStat:${list}" th:class="${iterStat.odd}? 'odd'">
        <td th:text="${HeFeiBBS_Domain.id}">1</td>
        <td th:text="${HeFeiBBS_Domain.listUrl}">网址</td>
        <%--<td th:text="${domain.title}"></td>--%>
        <%--<td th:text="${domain.content}"></td>--%>
        <%--<td th:text="${domain.fbsj}"></td>--%>
        <%--<td th:text="${domain.comment}"></td>--%>
        <%--<td th:text="${domain.commentCount}"></td>--%>
    </tr>
    </thead>
</table>
</body>
<script type="text/javascript">
    $(function () {

    });
</script>
</html>
