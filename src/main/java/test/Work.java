package test;

import static test.TU.H;
import static test.TU.H2;
import static test.TU.H256;
import static test.TU.R160;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

import javax.crypto.spec.SecretKeySpec;

import core.Base58;

public class Work {
	public static void main(String[] args) throws NoSuchAlgorithmException, Exception {
		byte[] pub;
		byte[] priv;
		
		/**
		 * ①秘密鍵・公開鍵を作成
		 */
		//param "EC" = Elliptic Curveアルゴリズム(楕円曲線暗号と思われる）の鍵ペアを生成
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
		// 乱数生成
		SecureRandom randomGen = SecureRandom.getInstance("SHA1PRNG");
		//鍵ペア生成のための初期設定
		//鍵のサイズと、ランダム文字列を引数にする
		keyGen.initialize(256, randomGen);
		//鍵ペア生成
		KeyPair keyPair = keyGen.generateKeyPair();
		// 秘密鍵
		PrivateKey priKey = keyPair.getPrivate();
		// 公開鍵
		PublicKey pubKey = keyPair.getPublic();
		
		priv = priKey.getEncoded();
		pub = pubKey.getEncoded();
		
		H("HEX 秘密鍵",priv);
		H("HEX 公開鍵",pub);
		
		/*
		 * ②公開鍵に対して、SHA256でハッシュ化
		 */
		byte[] sha256hashedPublicKey = H256(pub);
		H("HEX SHA256でハッシュ化された公開鍵",sha256hashedPublicKey);
		
		/**
		 * ③SHA256でハッシュ化した公開鍵を、RIPEMD160でハッシュ化(公開鍵ハッシュ PKH)
		 */
		byte[] ripemd160hashedPublicKey = R160(sha256hashedPublicKey);
		H("HEX RIPEMD160でハッシュ化された公開鍵(PKH)",ripemd160hashedPublicKey);
		
		/**
		 * ④作成したPKHに対して、バージョンをプレフィックスとして追加する（先頭1byte) 現在は0のみ
		 */
		byte[] resultAddVer = new byte[21];
		resultAddVer[0] = 0; //先頭1バイトにバージョン情報を追加
		System.arraycopy(ripemd160hashedPublicKey, 0, resultAddVer, 1, resultAddVer.length-1);
		H("HEX PKHに対して、プレフィックスとしてバージョン追加",resultAddVer);
		
		/**
		 * チェックサム取得処理
		 * ⑤バージョンを追加したPKHに対してSHA356ダブルハッシュを行う
		 */
		byte[] sha256FirstTime = H256(resultAddVer);
		byte[] sha256SecondTime = H256(sha256FirstTime);
		H("HEX バージョン付きPKHダブルハッシュ化", sha256SecondTime);
		
		/**
		 * チェックサム取得処理
		 * ⑥ダブルハッシュ化したバージョン付きPKHの先頭4byteを抽出する（のちに使用する）
		 */
		byte[] checksum = new byte[] {sha256SecondTime[0],sha256SecondTime[1],sha256SecondTime[2],sha256SecondTime[3],};
		H("HEX チェックサム", checksum);
		
		/**
		 * ⑦チェックサムを、④で作成したバージョン付きPKHの末尾に追加する
		 */
		byte[] addChecksum = new byte[25];
		System.arraycopy(resultAddVer, 0, addChecksum, 0, addChecksum.length-4);
		System.arraycopy(checksum, 0, addChecksum, addChecksum.length-4, checksum.length);
		H("チェックサムをあバージョン追加後のPKHに追加",addChecksum);
		
		/**
		 * ⑧Base58で符号化
		 */
		String hexBase58encodedAddress = Base58.encode(addChecksum);
		H2("バージョン付き、チェックサム付きPKHをBase58で符号化 (AOアドレス完成)",hexBase58encodedAddress);
		
		
		/* ------ここまでが公開鍵からアドレスを作成する手順------- */
		
		System.out.println("************************************************************************");
		System.out.println("************************************************************************");
		System.out.println("以上、アドレス生成手順終わり");
		System.out.println("以下、送り先のアドレスが正しいことの確認手順");
		System.out.println("************************************************************************");
		System.out.println("************************************************************************");		
		/* ------ここからは、送金リクエストをする前に、送り先のアドレスが正しいことの確認手順------- */
		
		/**
		 * ケース
		 * 杉江から伊藤に10AOC送金するリクエストが来た場合
		 * 
		 * 杉江の秘密鍵:3041020100301306072a8648ce3d020106082a8648ce3d03010704273025020101042061e6236bb7693b97065fac85976306efc4233db29e389a3868e4d6ceceb67aab
		 * 杉江の公開鍵:3059301306072a8648ce3d020106082a8648ce3d03010703420004c987a539bf3c5e68b3ce9b4870c4ace2a21b2bde56fb03fd08e9b3a219dceec5879f0a982d94aeed7c919d05116a40e34c431c36a53fad6e76e54f9443c82d35
		 * 杉江のAOアドレス:14TiTcphGxzfomijmExNWpYDzegTv9Lsms
		 * 
		 * 伊藤のAOアドレス:1NNmuPSi4BbCQbnNG18CrxMzkSWimgVSHt
		 * 
		 * まずは伊藤のAOアドレスから伊藤のPKHを算出する必要があるため、以下はその手順
		 */
		
		String itoAddress = "1NNmuPSi4BbCQbnNG18CrxMzkSWimgVSHt";
		H2("伊藤（送り先）の AOアドレス", itoAddress);
		
		/*
		 * まず、杉江は伊藤のAOアドレスから、公開鍵ハッシュ(PKH)を取得する必要がある
		 * ①伊藤のAOアドレスをBase58でデコードする
		 */
		byte[] base58decodedItoAddress = Base58.decode(itoAddress);
		H("伊藤のAOアドレスをBase58でデコード",base58decodedItoAddress);
		
		/**
		 * ②チェックサム（末尾4byte)を切り取り、チェックサムのbyte配列と、チェックサムを削除したアドレスの配列をそれぞれ作成する
		 */
		byte[] itoAddressChechsum = new byte[4]; //切り取った4byte収納用		
		System.arraycopy(base58decodedItoAddress, base58decodedItoAddress.length-4, itoAddressChechsum, 0, 4);//末尾4byteをコピー
		H("伊藤アドレスのチェックサム",itoAddressChechsum);//※自分が知っていると思っている、伊藤のアドレスをチェックサム
		
		byte[] itoAddressDeletedChecksum = new byte[21]; //末尾4byteを切り取られた伊藤のアドレス(PKH)
		System.arraycopy(base58decodedItoAddress, 0, itoAddressDeletedChecksum, 0, 21);//末尾4byteを削除
		H("チェックサム削除後の伊藤アドレス",itoAddressDeletedChecksum);
		
		/**
		 * ③チェックサム削除後の伊藤のアドレスをSHA256でダブルハッシュ化する
		 */		
		byte[] itoAddrSha256FirstTime = H256(itoAddressDeletedChecksum);
		byte[] itoAddrSha256SecondTime = H256(itoAddrSha256FirstTime);
		H("HEX チェックサム削除後の伊藤アドレスSHA25D", itoAddrSha256SecondTime);
		
		/**
		 * ④SHA256ダブルハッシュ化した伊藤アドレスの先頭4byteを取得する
		 * ※②で取得したチェックサムは、果たして本当に正しいのかを検証するために、この処理を行っている。
		 */
		byte[] itoConfirmChecksum = new byte[]{itoAddrSha256SecondTime[0],itoAddrSha256SecondTime[1],itoAddrSha256SecondTime[2],itoAddrSha256SecondTime[3],};
		H("確認用伊藤アドレスのチェックサム",itoConfirmChecksum);
		
		/**
		 * ⑤既知の伊藤アドレスのチェックサムと、実際に確認して抽出したチェックサムが一致した場合、入力ミスは無いことになる。
		 */
		if(Arrays.equals(itoAddressChechsum, itoConfirmChecksum)) {
			System.out.println("<<<<<<チェックサムが一致しました。入力ミスが無いことを確認しました。>>>>>>");
			H("既知のチェックサム:", itoAddressChechsum);
			H("算出したのチェックサム:", itoAddressChechsum);
		}
		
		/**
		 * ⑥チェックサムが一致することを確認したら、チェックサムを削除した伊藤アドレスのバージョンを削除する
		 * ＝REMEND160でハッシュ化した純粋な公開鍵ハッシュへと戻す
		 */
		byte[] verDeletedItoAddress = new byte[20];
		System.arraycopy(itoAddressDeletedChecksum, 1, verDeletedItoAddress, 0, verDeletedItoAddress.length);
		H("純粋な伊藤PKH これが伊藤のPKHであること（ミスは存在しないこと）が証明された", verDeletedItoAddress);
		
		
		/* ------送り先のアドレスが正しいことの確認手順------- */
		
		System.out.println("************************************************************************");
		System.out.println("************************************************************************");
		System.out.println("以上、送り先のアドレスが正しいことの確認手順終了");
		System.out.println("以下、送金リクエスト手順");
		System.out.println("************************************************************************");
		System.out.println("************************************************************************");		
		/* ------ここからは、送金リスエストが来た際に、確認する手順------- */
		
		/**
		 * 送金リクエスト手順
		 * 前提
		 * ・杉江が伊藤に10AOC送金するリクエストを管理者（プログラム）に送る
		 * 
		 * 必要な物
		 * ・メッセージ（10AOC送って！の意思表示)
		 * ・送金相手(伊藤)のアドレス（PKH)
		 * ・送金者の公開鍵
		 * ・送金者が、秘密鍵で取引を電子署名した署名
		 */
		
		/**
		 * ①メッセージ
		 * 管理者（Lisaとする)へのメッセージ
		 */
		String msg = "10";
		
		/**
		 * ②送金相手のPKH
		 */
		String receiverPKH = "ea78dc27a79b03240b1e3e84b99b0b4607f2730d";
		
		/**
		 * ③送金者の公開鍵(今回は人が見る前提のため16進数)
		 */
		String senderPubKey = "3059301306072a8648ce3d020106082a8648ce3d03010703420004c987a539bf3c5e68b3ce9b4870c4ace2a21b2bde56fb03fd08e9b3a219dceec5879f0a982d94aeed7c919d05116a40e34c431c36a53fad6e76e54f9443c82d35";
		
		/**
		 * ④上記①〜③までのデータの電子署名を行う
		 */
//		String requestData = msg + "\n" + receiverPKH + "\n" + senderPubKey;
		String[] requestData = new String[] {msg,receiverPKH,senderPubKey};
				
		//杉江の秘密鍵をPrivateKeyインスタンスに変換する
		/**
		 * 諦めた。これはおそらく外部保存になる。。
		 */
//		String sugiePrivateKeyStrHex = "3041020100301306072a8648ce3d020106082a8648ce3d03010704273025020101042061e6236bb7693b97065fac85976306efc4233db29e389a3868e4d6ceceb67aab";
//		byte[] sugieprivateKeyByte = sugiePrivateKeyStrHex.getBytes("UTF-8");
////		PrivateKey sugiePrivateKey = (PrivateKey) new SecretKeySpec(sugieprivateKeyByte, 0, sugieprivateKeyByte.length, "EC"); 
//		
//		KeyFactory kf = KeyFactory.getInstance("EC"); 
//		PrivateKey sugiePrivateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(pubKey.getEncoded()));
		
        // 署名生成アルゴリズムを指定する
        Signature dsa = Signature.getInstance("SHA1withECDSA");
        // 杉江の秘密鍵で初期化
        dsa.initSign(priKey);
        // 署名生成(オリジナル構成)
        String allData = requestData[0] + requestData[1] + requestData[2];
  
        dsa.update(allData.getBytes("UTF-8"));
        // 生成した署名を取り出す
        byte[] signature = dsa.sign();
		
        /** ------ここまでは送金者がリクエストを発行するための手順び------- */
        
        /** -------ここから、リクエストを受け取った管理者の視点----------*/
        
        /**
         * リクエストデータと署名を、送る(受け取ったと仮定）
         * 受け取った情報(ここからLisa視点)
         * リクエスト情報 requestData
         * 署名 signature
         */
        
        /*
         * 署名検証
         */
        // 初期化 受け取った公開鍵から、PublicKeyを生成
//		String sugiePublicKeyStrHex = "3041020100301306072a8648ce3d020106082a8648ce3d03010704273025020101042061e6236bb7693b97065fac85976306efc4233db29e389a3868e4d6ceceb67aab";
//		byte[] sugiePublicKeyByte = sugiePublicKeyStrHex.getBytes("UTF-8");
////		PublicKey sugiePublicKey = (PublicKey) new SecretKeySpec(sugiePublicKeyByte, 0, sugiePublicKeyByte.length, "EC"); 
//		KeyFactory kf2 = KeyFactory.getInstance("EC"); 
//		PublicKey sugiePublicKey = (PublicKey) kf2.generatePrivate(new PKCS8EncodedKeySpec(sugiePublicKeyByte));
        
        //諦めた
		
		
        dsa.initVerify(pubKey);
        // 署名検証する対象をセットする
        dsa.update(allData.getBytes("UTF-8"));
        // 署名検証
        boolean verifyResult = dsa.verify(signature);
        System.out.println("署名検証結果: " + verifyResult);
        
        //TODO 課題・・・KeyPairの永続化。。
        
        String amount = requestData[0];
        String receiverPKH2 = requestData[1];
        String senderPub = requestData[2];
        

        
        /**
         * 送金者の公開鍵をPKHにする SHA256d + RIPEMD160
         */
        byte[] senderPubSha256first = H256(senderPub.getBytes());
        byte[] senderPubSha256second = H256(senderPubSha256first);
        byte[] senderPKHresult = R160(senderPubSha256second);
        H("送り手のPKH", senderPKHresult);
        
        System.out.println("送金者：" + senderPKHresult);
        System.out.println("受取人：" + receiverPKH2);
        System.out.println("金額：" + amount);
        
	}
}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
			
//		pub = new BigInteger(pubKey.getEncoded());
//		priv = new BigInteger(priKey.getEncoded());
//		
//		System.out.println("<<秘密鍵2進数>>");
//		System.out.println(priv);
//		
//		System.out.println("<<公開鍵2進数>>");
//		System.out.println(pub);
//		
//		String pubHex = pub.toString(16);
//		String priHex = priv.toString(16);
//		
//		System.out.println("<<公開鍵16進数>>");
//		System.out.println(pubHex);
//		
//		System.out.println("<<秘密鍵16進数>>");
//		System.out.println(priHex);
//		
//		MessageDigest primd = MessageDigest.getInstance("SHA-256");
//		primd.update(priKey.getEncoded());
//		byte[] prib = primd.digest();
//		
//		MessageDigest pubmd = MessageDigest.getInstance("SHA-256");
//		pubmd.update(pubKey.getEncoded());
//		byte[] pubb = pubmd.digest();
//		
//		System.out.println("<<秘密鍵16進数>>");
//		for(byte b : prib) {
//			System.out.print(String.format("%02x",b));
//		}
//		
//		System.out.println("");
//		
//		System.out.println("<<公開鍵16進数>>");
//		for(byte b : pubb) {
//			System.out.print(String.format("%02x",b));
//		}
//		System.out.println("");
//		
//		RIPEMD160Digest digest = new RIPEMD160Digest();
//		digest.update(pubb, 0, pubb.length);
//		byte[] pubout = new byte[20];
//        digest.doFinal(pubout, 0);
//        
//		System.out.println("<<公開鍵RIPEMD 16進数>>");
//		for(byte b : pubout) {
//			System.out.print(String.format("%02x",b));
//		}System.out.println("");
//		
//		//チェックサム抽出用SHA256d
//		MessageDigest pubch = MessageDigest.getInstance("SHA-256");
//		pubch.update(pubKey.getEncoded());
//		pubch.update(pubKey.getEncoded());
//		byte[] pubchb = pubmd.digest();
//		
//		byte[] bytech = new byte[] {pubchb[0],pubchb[1],pubchb[2],pubchb[3],}; 
//		
//		System.out.println("<<公開鍵のチェックしてもらう用チェックサム>>");
//		for(byte b : bytech) {
//			System.out.print(String.format("%02x",b));
//		}
//		System.out.println("");
//		
//		//System.arraycopy(コピー元配列, コピー元配列のコピー開始位置, コピー先配列, コピー先配列の開始位置, コピーの個数)
//		//PKHにチェックサムとバージョンを追加
//    	byte[] readdr = new byte[25];
//    	readdr[0] = 0;//バージョン
//    	System.arraycopy(pubout, 0, readdr, 1, pubout.length);
//        System.arraycopy(bytech, 0, readdr, readdr.length-4, 4);
//		
//		System.out.println("<<アドレス>>");
//		for(byte b : readdr) {
//			System.out.print(String.format("%02x",b));
//		}
//		System.out.println("");
//        
//		String pubStr = Base58.encodeChecked(0, readdr);
//		System.out.println("<<公開鍵Base58Encode>>");
//		System.out.println(pubStr);
//		
//		
//		byte[] cpub = Base58.decode(pubStr);
//		
//		System.out.println("<<公開鍵Base58Decode>>");
//		for(byte b : cpub) {
//			System.out.print(String.format("%02x",b));
//		}
//		System.out.println("");
//		
////		//チェックサム削除(末尾4byte)
////		byte[] csum = new byte[] {cpub[cpub.length-4],cpub[cpub.length-3],cpub[cpub.length-2],cpub[cpub.length-1]};
////		System.out.println("<<チェックサム>>");
////		for(byte b : csum) {
////			System.out.print(String.format("%02x",b));
////		}
////		System.out.println("");
//		
//		//公開鍵を
//		byte[] pubby = pubKey.getEncoded();
//		MessageDigest pubbmd = MessageDigest.getInstance("SHA-256");
//		pubmd.update(pubKey.getEncoded());
//		pubby = pubbmd.digest();
//		
//		//160
//		RIPEMD160Digest p160 = new RIPEMD160Digest();
//		p160.update(pubby, 0, pubby.length);
//		byte[] p160d = new byte[20];
//        digest.doFinal(p160d, 0);
//		
//		
//		System.out.println("<<送り用RIPEMD16進数>>");
//		for(byte b : p160d) {
//			System.out.print(String.format("%02x",b));
//		}
//		System.out.println("");
//		
//		byte[] cdsum = new byte[] {pubby[0],pubby[1],pubby[2],pubby[3]};
//		
//		System.out.println("<<公開鍵のSHA256後先頭4bytes>>");
//		for(byte b : cdsum) {
//			System.out.print(String.format("%02x",b));
//		}
//		System.out.println("");
//		
//		if(Arrays.equals(bytech, cdsum)) {
//			System.out.println("**************");
//			System.out.println("**一致しました**");
//			System.out.println("**************");
//		}
//		
//		System.out.println("<<チェックサムOKなPKH>>");
//		for(byte b : pubby) {
//			System.out.print(String.format("%02x",b));
//		}
//		System.out.println("");
		
		
		



//ECKey key = new ECKey(SecureRandom.getInstance("SHA1PRNG"));
//BigInteger priv = key.priv;
//LazyECPoint pub = key.pub;