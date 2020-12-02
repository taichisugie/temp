//package test;
//
//import java.util.Arrays;
//
//import core._Base58;
//import core.CryptUtils;
//
////暫定的マイニング報酬の疑似
//public class Mining_TEMP {
//	public static void main(String[] args) throws Exception {
//		var lisa_addr = "15eUozfiPy1GHkESu48do58qdobBnMpiRC";
//		//デコード
//		byte[] base58decode = _Base58.decode(lisa_addr);
//		System.out.println(CryptUtils.changeByteToHex(base58decode));
//		
//		String s = _Base58.encode(base58decode);
//		
//		System.out.println(CryptUtils.changeByteToHex(s.getBytes()));
//		
//		
//		System.out.println("@@" + CryptUtils.changeByteToHex(lisa_addr.getBytes()));
//		System.out.println("@@" + CryptUtils.changeByteToHex(s.getBytes()));
//		
//		
//		//チェックサム削除
//		byte[] deletedChecksum = new byte[20];
//		System.arraycopy(base58decode, 0, deletedChecksum, 0, base58decode.length -4); //後半4byte削除
//		System.out.println(CryptUtils.changeByteToHex(deletedChecksum));
//		//チェックサム取得
//		byte[] rcvchecksum = Arrays.copyOfRange(base58decode, base58decode.length-4, base58decode.length);//チェックサム取得（最終4バイト）
//		byte[] double256 = CryptUtils.double256(deletedChecksum);
//		byte[] checksum = Arrays.copyOfRange(double256, 0, 4);//チェックサム取得（最終4バイト）
//		
//		System.out.println(CryptUtils.changeByteToHex(checksum));
//		System.out.println(CryptUtils.changeByteToHex(rcvchecksum));
//
//		if(CryptUtils.changeByteToHex(checksum).equals(CryptUtils.changeByteToHex(rcvchecksum))) {
//			System.out.println("【チェックサムの確認の結果、一致しました。正規のアドレスとして認識します。】");
//			System.out.println("【】");
//			System.out.println("【checksum1】：" + CryptUtils.changeByteToHex(checksum));
//			System.out.println("【checksum2】：" + CryptUtils.changeByteToHex(rcvchecksum));
//		}else {
////			throw new Exception("Base58Checkの結果、一致しませんでした。確認してください。");
//		}
//		
//		//先頭のバージョンを削除
//		byte[] deletedVer = Arrays.copyOfRange(deletedChecksum, 1, deletedChecksum.length); //バージョン削除
//		System.out.println("【バージョン削除（PHK復元）】：" + CryptUtils.changeByteToHex(deletedVer));
//		
//	}
//}
