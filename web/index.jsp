<%--
  Created by IntelliJ IDEA.
  User: ABoK4Do
  Date: 25.12.16
  Time: 2:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="classes.DataBaseWorker" %>
<%@ page import="java.util.ArrayList" %>
<html>
<head>
    <title>Restaurant</title>
    <style>
        .showAll {
            border: black 1px solid;
            background-color: greenyellow;
        }
        th{
                border: black 2px solid;

            }
        body {
            background-color: #fdffd5;
        }
    </style>
</head>
<body>
<h3>Тут таблица:</h3>
<table class="showAll">
        <%
        ArrayList listTable = DataBaseWorker.showDB();
        int columns = ((ArrayList)listTable.get(0)).size();
        for (Object s : listTable){
        %>

        <tr>
            <%
            for (int i = 0; i < columns; i++) {
            %>

            <th><%=(String)((ArrayList)s).get(i)%></th>
            <%}%>
        </tr>
        <%}%>
    </table>
<div class="refresh"><a href="index.jsp">UPDATE</a> <</div>

<a href="/start1.html">CLICK</a>
  </body>
</html>
