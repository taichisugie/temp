package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.UserInfoDTO;

public class FindUserDAO {
	private String username;
	private String pwd;

	public FindUserDAO(String username, String pwd) {
		this.username = username;
		this.pwd = pwd;
	}

	public UserInfoDTO find() {
		UserInfoDTO user = new UserInfoDTO();
		String query = "SELECT * FROM login_user WHERE username = ? and pwd = ?";
		try (Connection con = DBCom.getConnection()) {
			try (PreparedStatement ps = con.prepareStatement(query)) {
				ps.setString(1, this.username);
				ps.setString(2, this.pwd);
				try (ResultSet rs = ps.executeQuery()) {
					boolean r = rs.next();
					if (r) {
						user.setId(rs.getInt("id"));
						user.setUsername(rs.getString("username"));
						user.setLoginResult(r);
					}
				}
			} catch (SQLException e) {
				con.rollback();
				throw e;
			}
		} catch (SQLException e) {
			e.printStackTrace();//TODO　エラー処理
		}
		//Whether login is possible or impossible by the member variable of the returned instance.
		return user;
	}
}
