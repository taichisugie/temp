package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateUserDAO {
	private String username;
	private String pwd;
	
	public CreateUserDAO(String username, String pwd) {
		this.username = username;
		this.pwd = pwd;
	}

	public boolean insert() {
		String query = "insert into login_user (username, pwd) values (?,?)";
		try (Connection con = DBCom.getConnection()) {
			try (PreparedStatement ps = con.prepareStatement(query)) {
				ps.setString(1, this.username);
				ps.setString(2, this.pwd);
				int r = ps.executeUpdate();
				if(r == 1) {
					con.commit();
					return true;
				}
			} catch (SQLException e) {
				con.rollback();
				throw e;
			}
		} catch (SQLException e) {
			e.printStackTrace();//TODO　エラー処理
		}
		return false;
	}
}
