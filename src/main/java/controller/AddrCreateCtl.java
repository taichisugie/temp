package controller;

import static com.AOAttr.MENU_PATH;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.UserInfoDTO;
import model.KeyPairCreateMdl;

/**
 * 
 * 処理するだけ。メニューに戻る。
 *
 */

@WebServlet("/create")
public class AddrCreateCtl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
		rq.setCharacterEncoding("UTF-8");
		UserInfoDTO userInfo = (UserInfoDTO) rq.getSession().getAttribute("user");
		KeyPairCreateMdl creator = new KeyPairCreateMdl(userInfo);
		try {
			creator.create();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		RequestDispatcher dp = rq.getRequestDispatcher(MENU_PATH);
		dp.forward(rq, rs);
	}
}