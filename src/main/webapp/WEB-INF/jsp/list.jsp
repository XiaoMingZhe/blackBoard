
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="common/head.jsp" %>

<%-- <h1>
<%= request.getAttribute("list")%>
</h1> --%>
  <body>
    <table>
       <tr>
        <td>Book ID</td>
        <td>NAME</td>
        <td>Number</td>
       </tr>
       <c:forEach items="${list}" var="list">
       <tr>
       <td>${list.bookId}</td>
       <td>${list.name}</td>
       <td>${list.number}</td>
       </tr>
       </c:forEach>
    </table>
  </body>