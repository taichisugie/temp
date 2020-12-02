package test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.bouncycastle.crypto.digests.RIPEMD160Digest;

/**
 * テスト用ユーティリティ
 * @author sugietaichi
 *
 */
public class TU {
	public static void H(String t, byte[] b) { //hexで表示
		System.out.println("");
		System.out.println("<< "+ t +" >>");
		System.out.println("");
		for(byte n : b) {
			System.out.print(String.format("%02x",n));
		}
		System.out.println("");
		System.out.println("");
	}
	
	public static void H2(String t, String s) { //hexで表示
		System.out.println("");
		System.out.println("<< "+ t +" >>");
		System.out.println("");
		System.out.println(s);	
		System.out.println("");
	}
	
	public static byte[] H256(byte[] b) {
		MessageDigest pubmd = null;
		try {
			pubmd = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pubmd.update(b);
		byte[] r = pubmd.digest();
		return r;
	}
	
	public static byte[] R160(byte[] b) {
		RIPEMD160Digest d = new RIPEMD160Digest();
		d.update(b, 0, b.length);
		byte[] p = new byte[20];
        d.doFinal(p, 0);
        return p;
	}
}
