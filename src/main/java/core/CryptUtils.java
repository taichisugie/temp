package core;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

import org.apache.catalina.tribes.util.Arrays;
import org.bouncycastle.crypto.digests.RIPEMD160Digest;

import test.TU;

public class CryptUtils {
	private CryptUtils() {}
	
	/**
	 * バイト配列を受け取り、16進数の文字列を返却する
	 * @param b　変換対象のバイト配列
	 * @return 16進数の文字列
	 */
	public static String byteToHex(byte[] b) {
		StringBuilder sb = new StringBuilder();
		for (byte bb : b) {
			sb.append(String.format("%02x", bb));
		}
		return sb.toString();
	}
	
	/**
	 * バイト配列のSHA256ハッシュを返却
	 * @param b ハッシュ対象のバイト配列
	 * @return SHA256ハッシュ
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] sha256(byte[] b) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(b);
		return md.digest();
	}
	
	/**
	 * バイト配列のSHA256ダブルハッシュを返却
	 * @param b ハッシュ対象のバイト配列
	 * @return SHA256ダブルハッシュ
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] sha256twice(byte[] b) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(b);
		md.update(b);
		return md.digest();
	}
	
	/**
	 * バイト配列のRIPEMD160ハッシュを返却
	 * @param b ハッシュ対象のバイト配列
	 * @return RIPEMD160ハッシュ
	 */
	public static byte[] ripemd160(byte[] b) {
		RIPEMD160Digest d = new RIPEMD160Digest();
		d.update(b, 0, b.length);
		byte[] p = new byte[20];
        d.doFinal(p, 0);
        return p;
	}
	
	/**
	 * 20byteのPKHのプレフィックスに1byteのバージョン情報を付与
	 * @param v 1byteのバージョン
	 * @param b 20byteのPKH
	 * @return バージョン付与後の21bytePKH
	 */
	public static byte[] addVer(byte v, byte[] b) {
		byte[] r = new byte[21];
		r[0] = v; //先頭1バイトにバージョン情報を追加
		System.arraycopy(b, 0, r, 1, r.length-1);
		return r;
	}
	
	/**
	 * 先頭1byteのバージョン情報を削除したbyte配列を取得
	 * @param b バージョン情報削除対象byte配列(21byte想定)
	 * @return バージョン情報削除後のbyte配列(20byte)
	 */
	public static byte[] removeVer(byte[] b) {
		byte[] r = new byte[20];
		System.arraycopy(b, 1, r, 0, r.length);
		return r;
	}
	
	/**
	 * バイト配列の先頭4byteをチェックサムとして取得
	 * @param b チェックサム取得対象のbyte配列
	 * @return チェックサム
	 */
	public static byte[] getChecSum(byte[] b) {
		return new byte[] {b[0],b[1],b[2],b[3]};
	}
	
	/**
	 * 4byteのチェックサムをbyte配列の末尾に追加する
	 * @param b チェックサム追加対象byte配列
	 * @param checksum 4byteのチェックサム
	 * @return 25byteのチェックサム追加後のbyte配列
	 */
	public static byte[] addChecksumEnd(byte[] b, byte[] checksum){
		byte[] r = new byte[25];
		System.arraycopy(b, 0, r, 0, r.length-4);
		System.arraycopy(checksum, 0, r, r.length-4, checksum.length);
		return r;
	}
	
	/**
	 * byte配列から、末尾4byteのチェックサムを取得
	 * @param b チェックサム取得対象のbyte配列
	 * @return 4byteのチェックサム
	 */
	public static byte[] getCheckSumEnd(byte[] b) {
		byte[] r = new byte[4]; //切り取った4byte収納用		
		System.arraycopy(b, b.length-4, r, 0, 4);
		return r;
	}
	
	/**
	 * 末尾4byteのチェックサムを削除したbyte配列を取得(バージョン情報あり想定)
	 * @param b チェックサム削除対象のbyte配列
	 * @return 21byteのチェックサム削除後のbyte配列
	 */
	public static byte[] delCheckSumEnd(byte[] b) {
		byte[] r = new byte[21]; //末尾4byteを切り取られた伊藤のアドレス(PKH)
		System.arraycopy(b, 0, r, 0, 21);//末尾4byteを削除
		return r;
	}
	
	/**
	 * 先頭4byteのチェックサムを取得する
	 * @param b チェックサム取得対象のbyte配列
	 * @return 4byteのチェックサム
	 */
	public static byte[] getCheckSumHead(byte[] b) {
		return new byte[]{b[0],b[1],b[2],b[3]};
	}
	
	/**
	 * base58checkをクリアした場合、引数のアドレスからPKHを算出する
	 * @param s base58check対象のアドレス
	 * @return 16進数のPKH
	 * @throws Exception
	 */
	public static String addressFromPKH(String s) throws Exception {
		System.out.println(s);
		/* Base58Check */
		byte[] b = Base58.decode(s);			//Base58decode
		TU.H("",b);
		byte[] cs = getCheckSumEnd(b);			//チェックサム取得
		TU.H("",cs);
		byte[] delCS = delCheckSumEnd(b);		//チェックサム削除
		TU.H("",delCS);
		byte[] s256d = sha256twice(delCS);		//SHA256ダブル
		TU.H("",s256d);
		byte[] tgtCS = getCheckSumHead(s256d);	//SHA256d後のチェックサム取得
		TU.H("",tgtCS);
		
		//Base58checkがfalseの場合
		if(!Arrays.equals(cs, tgtCS)) 
			throw new Exception("base58check is discord."); //TODO
		
		byte[] pkh = removeVer(delCS);			//先頭byteからバージョンを削除
		return byteToHex(pkh);
	}
	
	/**
	 * PublicKeyインスタンスから16進数のPKHを作成する
	 * @param priv 公開鍵
	 * @return PKH
	 * @throws NoSuchAlgorithmException 
	 */
	public static String pubKeyFromPKH(PublicKey pub) throws NoSuchAlgorithmException {
		byte[] pb = pub.getEncoded();
		byte[] sha256 = sha256(pb);
		byte[] pkh = ripemd160(sha256);
		return byteToHex(pkh);
	}
	
	public static String pubKeyFromAddress(PublicKey pub) throws NoSuchAlgorithmException {
		byte[] pb = pub.getEncoded();
		TU.H("",pb);
		byte[] sha256 = sha256(pb);
		TU.H("",sha256);
		byte[] pkh = ripemd160(sha256);
		TU.H("",pkh);
		byte[] addVer = addVer((byte)0, pkh);
		TU.H("",addVer);
		byte[] d256 = sha256twice(addVer);
		TU.H("",d256);
		byte[] cs = getCheckSumHead(d256);
		TU.H("",cs);
		byte[] addCS = addChecksumEnd(addVer, cs);
		TU.H("",addCS);
		String addr = Base58.encode(addCS);
		System.out.println(addr);
		
		return addr;
	}
}
