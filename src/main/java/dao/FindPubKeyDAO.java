package dao;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.security.Key;
import java.security.PublicKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FindPubKeyDAO {
		private int userID;
		
		public FindPubKeyDAO(int userID) {
			this.userID = userID;
		}

		public List<PublicKey> find() throws IOException, ClassNotFoundException {
			List<PublicKey> pubKeys = new ArrayList<>();
			String query = "select * from login_user u inner join key_pair k on u.id = k.id where u.id = ?";
			try (Connection con = DBCom.getConnection()) {
				try (PreparedStatement ps = con.prepareStatement(query)) {
					ps.setInt(1, this.userID);
					try (ResultSet rs = ps.executeQuery()) {
						//公開鍵を取得
						while(rs.next()) {
							InputStream is = rs.getBinaryStream("public_key");
							ObjectInputStream obis = new ObjectInputStream(is);
							Key pub = (Key) obis.readObject();
							pubKeys.add((PublicKey) pub);
						}
					}
				} catch (SQLException e) {
					con.rollback();
					throw e;
				}
			} catch (SQLException e) {
				e.printStackTrace();//TODO　エラー処理
			}
			return pubKeys;
		}
	}
