package test;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.security.PublicKey;

import core.CryptUtils;
import core.KeyGenerator;

/**
 * トランザクションテスト
 * @author sugietaichi
 *
 */
public class TxTes {
	public static void main(String[] args) throws Exception {
		KeyGenerator g = new KeyGenerator();
		g.create();
		PublicKey k = g.getPublicKey();
		
		
		/*
		 *	 擬似的な承認済みトランザクション
		 */
		Tx t1 = new Tx();
		t1.txid = "genesis tx".getBytes();
		t1.pubKey = k;
		t1.sign = null;
		t1.outputIndex = 1;
		t1.amount = 10;
		
		/**
		 * 新しいトランザクションを追加
		 * 杉江視点で→伊藤に送る
		 */
		Tx t2 = new Tx();
		ByteArrayOutputStream baos= new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(t1);
		oos.close();
		
		byte[] bytes = baos.toByteArray();
		t2.txid = CryptUtils.sha256twice(bytes);
	}
}
