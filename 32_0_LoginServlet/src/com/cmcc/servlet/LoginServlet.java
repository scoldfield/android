package com.cmcc.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    String username = request.getParameter("username");
	    String pwd = request.getParameter("password");
	    
	    if("abc".equals(username) && "123".equals(pwd)) {
	        System.out.println("��½�ɹ���");
	        response.getOutputStream().write("�ɹ�".getBytes("utf-8"));
	    }else {
	        System.out.println("��½ʧ�ܣ�");
	        response.getOutputStream().write("fail".getBytes("utf-8"));
	    }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
