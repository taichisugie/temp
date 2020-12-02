package dao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistKeyPairDAO {
	private int userID;
	
	public RegistKeyPairDAO(int userID) {
		this.userID = userID;
	}

	public boolean insert(PrivateKey priv, PublicKey pub) throws Exception {
		String query = "insert into key_pair values (?,?,?)";
		
		/* 秘密鍵インスタンス直列化用 */
		ByteArrayOutputStream baosPri = new ByteArrayOutputStream();
		ObjectOutputStream oosPri = new ObjectOutputStream(baosPri);
		oosPri.writeObject(priv);
		ByteArrayInputStream baisPri = new ByteArrayInputStream(baosPri.toByteArray());
		
		/* 公開鍵インスタンス直列化用 */
		ByteArrayOutputStream baosPub = new ByteArrayOutputStream();
		ObjectOutputStream oosPub = new ObjectOutputStream(baosPub);
		oosPub.writeObject(pub);
		ByteArrayInputStream baisPub = new ByteArrayInputStream(baosPub.toByteArray());
		
		try (Connection con = DBCom.getConnection()) {
			try (PreparedStatement ps = con.prepareStatement(query)) {
				ps.setInt(1, this.userID);
				ps.setBinaryStream(2, baisPri, baosPri.toByteArray().length);
				ps.setBinaryStream(3, baisPub, baosPub.toByteArray().length);

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


