package dao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import core.CryptUtils;
import dto.TransactionDTO;

public class Reward {
	public static void main(String[] args) throws Exception {
		String r_addr = "1BmNwMMfyk8RSS8B3U7tNUBtKaSuLewamL"; //testの一番上
		final String receiver_pkh = CryptUtils.addressFromPKH(r_addr);
		
		final int amount = 100;
		
		//擬似的なマイナー報酬をとりあえず実装
		//指定のアドレスに1000AOC贈呈
		String query = "insert into transaction values (?,?)";
		try (Connection con = DBCom.getConnection()) {
			try (PreparedStatement ps = con.prepareStatement(query)) {
				TransactionDTO tx = new TransactionDTO();
				ByteBuffer b = ByteBuffer.allocate(4);
				b.putInt(0x0000000000);
				tx.setUseTxid(new String(b.array())); //0 10こ ここおかしすぎるw TODO
				tx.setOutputIdx(-1);
				tx.setSign(null);
				tx.setPubKey(null);
				tx.setAmount(7200);
				
				ByteArrayOutputStream baos= new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos);
				oos.writeObject(tx);
				oos.close();
				byte[] byTx = baos.toByteArray();
				byte[] byTxid = CryptUtils.sha256twice(byTx); //運用上、TX自体のsha256dもDBに保存しておく
				String txid = CryptUtils.byteToHex(byTxid);
				ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
				ps.setString(1, txid);
				ps.setBinaryStream(2, bais);
				int r = ps.executeUpdate();
				if(r == 1) {
					con.commit();
					System.out.println(receiver_pkh + "に対してマイナー報酬（擬似）を" + amount + "AOC送金完了");
				}
			} catch (SQLException e) {
				con.rollback();
				throw e;
			}
		} catch (SQLException e) {
			e.printStackTrace();//TODO　エラー処理
		}
	}
}