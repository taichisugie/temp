package controller;

import static com.AOAttr.LOGIN_REFUSAL_PATH;
import static com.AOAttr.MENU_PATH;
import static com.AOAttr.TOPPAGE_PATH;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dto.UserInfoDTO;
import model.LoginMdl;

/**
 * トップページハンドラ
 */
@WebServlet("/")
public class LoginCtl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
		UserInfoDTO user = (UserInfoDTO) rq.getSession().getAttribute("user");
		String dist = TOPPAGE_PATH;
		//既にログイン済みの場合
		if(user != null && user.getLoginResult()) {
			dist = MENU_PATH;
		}
		RequestDispatcher dp = rq.getRequestDispatcher(dist);
		dp.forward(rq, rs);
	}

	@Override
	protected void doPost(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
		rq.setCharacterEncoding("UTF-8");
		String typedUsername = rq.getParameter("username"); // ログイン画面で入力されたID
		String typedPwd = rq.getParameter("pwd"); 			// ログイン画面で入力されたPWD
		String dist = LOGIN_REFUSAL_PATH; 					//遷移先画面URLの初期値はログイン失敗画面

		// ログイン処理
		LoginMdl loginLogic = new LoginMdl(typedUsername, typedPwd);
		UserInfoDTO user = loginLogic.findByLogin();
		//ログインの可否
		boolean r = user.getLoginResult();
		if(r) {
			//セッションスコープへ格納
			HttpSession s = rq.getSession();
			s.setAttribute("user", user);
			//メニュー画面へ遷移
			dist = MENU_PATH;
		}
		RequestDispatcher dp = rq.getRequestDispatcher(dist);
		dp.forward(rq, rs);
	}
}
