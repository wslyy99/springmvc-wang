<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getScheme() + "://" + request.getServerName()
            + ":" + request.getServerPort() + request.getContextPath()
            + "/";
    session.setAttribute("path", path);
%>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title></title>
    <meta charset="utf-8">
   
</head>
<body>
${errorMsg}
</body></html>
<html>
