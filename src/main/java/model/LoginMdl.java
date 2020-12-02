package model;

import java.sql.SQLException;

import dao.FindUserDAO;
import dto.UserInfoDTO;

public class LoginMdl {
	private String username;
	private String pwd;
	
	public LoginMdl(String username, String pwd) {
		this.username = username;
		this.pwd = pwd;
	}
	
	/**
	 * ログイン結果
	 * @return ログイン可否
	 * @throws SQLException 
	 */
	public UserInfoDTO findByLogin() {
		FindUserDAO searcher = new FindUserDAO(this.username, this.pwd);
		return searcher.find();
	}
}


