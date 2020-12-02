package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.EnvInfo;
import com.EnvInfo.EnvInfoSingleton;

class DBCom {
	
	/**
	 * 共通設定ファイルから情報を読み込み、コネクションインスタンスを生成・返却する
	 */
	protected static Connection getConnection() throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		EnvInfo envInfo = EnvInfoSingleton.INSTANCE.getInstance();
		Connection con =  DriverManager.getConnection(
				envInfo.getProperty("DB.URL"),
				envInfo.getProperty("DB.USER"),
				envInfo.getProperty("DB.PWD"));
		con.setAutoCommit(false);
		return con;
	}
}
