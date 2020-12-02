package controller;

import static com.AOAttr.*;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CreateUserMdl;

@WebServlet("/registUser")
public class RegistUserCtl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
		rq.setCharacterEncoding("UTF-8");
		//ユーザー作成ページへ
		RequestDispatcher dp = rq.getRequestDispatcher(ADDRESS_REGIST_USER_PATH);
		dp.forward(rq, rs);
	}

	@Override
	protected void doPost(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
		rq.setCharacterEncoding("UTF-8");
		
		String newUsername = rq.getParameter("username");
		String newPwd = rq.getParameter("pwd");
		CreateUserMdl creator = new CreateUserMdl(newUsername, newPwd);
		boolean r = creator.regist();
		
		RequestDispatcher dp = rq.getRequestDispatcher(r ? REGIST_SUCCESS_PATH:REGIST_FAILED_PATH);
		dp.forward(rq, rs);
	}
}