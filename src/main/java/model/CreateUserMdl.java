package model;

import java.sql.SQLException;

import dao.CreateUserDAO;

public class CreateUserMdl {
	private String username;
	private String pwd;
	
	public CreateUserMdl(String username, String pwd) {
		this.username = username;
		this.pwd = pwd;
	}
	
	/**
	 * ログイン結果
	 * @return ログイン可否
	 * @throws SQLException 
	 */
	public boolean regist() {
		CreateUserDAO register = new CreateUserDAO(this.username, this.pwd);
		return register.insert();
	}
}


