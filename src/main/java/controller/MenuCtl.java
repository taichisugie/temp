package controller;


import static com.AOAttr.*;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.SessionUtils;

import dto.UserInfoDTO;
import model.AddrListMdl;

@WebServlet("/menu")
public class MenuCtl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/*
	 * Please refer to WEB-INF/jsp/menu.jsp 
	 * Please refer to pps
	 * 1:Display AO address list of login user
	 * 2:Create a new AO address
	 * 3:Display a list of all transactions
	 * 4:AO COIN transfer
	 * 5:Balance confirmation
	 */
	private static final int AGAIN 	= 0;
	private static final int ADDRESS_LIST 	= 1;
	private static final int CREATE_ADDRESS = 2;
	private static final int TX_HISTORY 	= 3;
	private static final int COIN_TRANSFER 	= 4;
	private static final int BALANCE 		= 5;
	
	@Override
	protected void doPost(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
		rq.setCharacterEncoding("UTF-8");
		String dist = ERR_PATH;
		String pps = rq.getParameter("purpose"); // ログイン画面で入力されたID
		
		//現在のログインユーザ情報をセッションスコープから取得
		HttpSession s = rq.getSession();
		UserInfoDTO beforeUser = (UserInfoDTO) s.getAttribute("user"); //必要な情報が欠落している状態
		
		switch(Integer.parseInt(pps)) {
		//空白(初期値)を選択した場合は、再度メニューページを表示
		case AGAIN:
			dist = MENU_PATH;
			break;
		//ログインユーザーのアドレス一覧を表示するページへ繊維
		case ADDRESS_LIST:
			AddrListMdl addrFindLogic = new AddrListMdl(beforeUser);
			UserInfoDTO afterUserInfo = null;
			try {
				afterUserInfo = addrFindLogic.find();
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SessionUtils.updateSessionScope(s, "user", afterUserInfo); //セッションスコープ上書き
			dist = ADDRESS_LIST_PATH;
			break;
		//ログインユーザーのアドレスを新規作成
		case CREATE_ADDRESS:
			dist = ADDRESS_CREATE_PATH;
			break;
		case TX_HISTORY:
			break;
		case COIN_TRANSFER:
			dist = TRANSFER_PATH;
			break;
		case BALANCE:
			break;
		}
		RequestDispatcher dp = rq.getRequestDispatcher(dist);
		dp.forward(rq, rs);
		
	}
}