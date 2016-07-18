<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登陆</title>
</head>
<body>

    <form action="${pageContext.request.contextPath }/LoginServlet" method="get">
                    用户名：<input name="username"><br>
                    密码：<input name="password"><br>
            <button value="提交">提交</button>
    </form>

</body>
</html>