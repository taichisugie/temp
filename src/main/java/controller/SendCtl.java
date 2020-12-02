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
import model.CreateTxMdl;

/**
 * 
 * 処理するだけ。メニューに戻る。
 *
 */

@WebServlet("/send")
public class SendCtl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
		rq.setCharacterEncoding("UTF-8");
		String senderPubHash = rq.getParameter("sender_addr");
		String receiverAddr = rq.getParameter("receiver_addr");
		String amount = rq.getParameter("amount");
		
		UserInfoDTO user = (UserInfoDTO) rq.getSession().getAttribute("user");
		
		CreateTxMdl createTx = new CreateTxMdl(user);
		
		RequestDispatcher dp = rq.getRequestDispatcher(MENU_PATH);
		dp.forward(rq, rs);
	}
}