package dao;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.TransactionDTO;
import dto.UserInfoDTO;

public class CountBalanceDAO {
	private UserInfoDTO user;
	private List<String> UTXO_txidList;

	public CountBalanceDAO(UserInfoDTO user, List<String> UTXO_txid) {
		this.user = user;
		this.UTXO_txidList = UTXO_txid;
	}


	public List<TransactionDTO> find() throws Exception {	
		/**
		 * ここSQL作る処理多分死んでる
		 */
		StringBuilder sb = new StringBuilder();
		sb.append("select * from transaction where");
		int i = 0;
		for(;i<this.UTXO_txidList.size(); i++) {
			sb.append(" txid = " + this.UTXO_txidList.get(i));
			if(i != this.UTXO_txidList.size()-1) {
				sb.append(" and");
			}
		}
		
		for(String s : this.UTXO_txidList) {
			sb.append(" txid = " + s);
			sb.append(" and");
		}

		List<TransactionDTO> txList = new ArrayList<>();
		
		String query = sb.toString();
		try (Connection con = DBCom.getConnection()) {
			try (PreparedStatement ps = con.prepareStatement(query)) {
				for(int j=0;j<i;j++) {
					ps.setString(j+1, this.UTXO_txidList.get(j));
				}
				try (ResultSet rs = ps.executeQuery()) {
					//公開鍵を取得
					while(rs.next()) {
						InputStream is = rs.getBinaryStream("tx");
						ObjectInputStream obis = new ObjectInputStream(is);
						TransactionDTO tx = (TransactionDTO) obis.readObject();
						txList.add(tx);
					}
				}
			} catch (SQLException e) {
				con.rollback();
				throw e;
			}
		} catch (SQLException e) {
			e.printStackTrace();//TODO　エラー処理
		}
		return txList;
	}
	
	
}
