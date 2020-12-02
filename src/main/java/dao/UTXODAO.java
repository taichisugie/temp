package dao;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import dto.TransactionDTO;

public class UTXODAO {
	private Map<String, TransactionDTO> TXMap = new HashMap<>();

	public List<TransactionDTO> getUTXO() throws Exception {
		String query = "SELECT * FROM transaction";
		try (Connection con = DBCom.getConnection()) {
			try (PreparedStatement ps = con.prepareStatement(query)) {
				try (ResultSet rs = ps.executeQuery()) {
					boolean r = rs.next();
					if (r) {
						String txid = rs.getString("tx_id");
						
						InputStream istx = rs.getBinaryStream("tx");
						ObjectInputStream obistx = new ObjectInputStream(istx);
						TransactionDTO tx = (TransactionDTO) obistx.readObject();
						
						this.TXMap.put(txid,tx);
					}
				}
			} catch (SQLException e) {
				con.rollback();
				throw e;
			}
		} catch (SQLException e) {
			e.printStackTrace();//TODO　エラー処理
		}
		
		return this.extractTX();
	}
	
	private List<TransactionDTO> extractTX() {
		List<TransactionDTO> txlist = new ArrayList<>(); 
		TransactionDTO tx;
		String txid;
		List<String> usedTx = new ArrayList<>();
		
		/*
		 * 全トランザクションの使用TXIDを取得（coinbaesどうする）TODO
		 */
		for (Entry<String,TransactionDTO> e : this.TXMap.entrySet()) {
		    tx = e.getValue();
		    usedTx.add(tx.getUseTxid());
		}
		
		/*
		 * 使われているTXIDがあるものはUTXOでは無い
		 */
		for (Entry<String,TransactionDTO> e : this.TXMap.entrySet()) {
			//使用済みのtxidの中に、取得した全トランザクション中のtxidが存在しない＝未使用の場合のみ
		    if(!usedTx.contains(e.getKey())) {
		    	txlist.add(e.getValue());
		    }
		}
		//TODO coinbase の場合。。。？
		
		return txlist;
	}
}
