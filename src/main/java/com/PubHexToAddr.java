//package com;
//
//import core._Base58;
//import core.CryptUtils;
//
////16進数の公開鍵を、アドレスに変換する
//public class PubHexToAddr {
//	public static String conv(String pubHex) throws Exception {
//		//16進数の文字列の公開鍵からバイト配列に変換したものをSHA256とREPEMD160でハッシュ化
//		byte[] sha256 = CryptUtils.digestByteSha256(pubHex.getBytes());
//		byte[] rip160 = CryptUtils.digestRIPEMD160(sha256);
//		//バージョン情報を先頭に追加
//		byte[] addVer = CryptUtils.addVer(rip160, (byte) 00);
//		//ダブルSHA256でハッシュ化
//		byte[] double256 = CryptUtils.double256(addVer);
//		//チェックサム取得
//		byte[] checksum = CryptUtils.getCheckSum(double256);
//		//チェックサムを先頭に追加
//		byte[] checkResult = CryptUtils.addCheckSum(addVer, checksum);
//		//Base58符号化
//		String addr = _Base58.encode(checkResult);
//		
//		return addr;
//	}
//}
