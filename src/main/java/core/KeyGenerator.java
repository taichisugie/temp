package core;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

public class KeyGenerator {
	private PrivateKey priKey;
	private PublicKey pubKey;
	private static String CRYPT_ALGORITHM = "EC";
	private static int KEY_SIZE = 256;
	private static String RANDOM_ALGORITHM = "SHA1PRNG";

	public void create() throws Exception {
		//param "EC" = Elliptic Curveアルゴリズムの鍵ペアを生成
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance(CRYPT_ALGORITHM);
		// 乱数生成
		SecureRandom randomGen = SecureRandom.getInstance(RANDOM_ALGORITHM);
		//鍵ペア生成のための初期設定
		keyGen.initialize(KEY_SIZE, randomGen);
		//鍵ペア生成
		KeyPair keyPair = keyGen.generateKeyPair();
		// 秘密鍵
		this.priKey = keyPair.getPrivate();
		// 公開鍵
		this.pubKey = keyPair.getPublic();
	}

	public PrivateKey getPrivateKey() {
		return this.priKey;
	}

	public PublicKey getPublicKey() {
		return this.pubKey;
	}

	public byte[] getBytePrivateKey() {
		return this.priKey.getEncoded();
	}

	public byte[] getBytePublicKey() {
		return this.pubKey.getEncoded();
	}


	public String getStrPrivateKey() {
		StringBuilder priSb = new StringBuilder();
		byte[] bytePriKey = priKey.getEncoded();
		for (byte b : bytePriKey) {
			priSb.append(String.format("%02x", b));
		}
		return priSb.toString();
	}

	public String getStrPublicKey() {
		StringBuilder pubSb = new StringBuilder();
		byte[] bytePubKey = pubKey.getEncoded();
		for (byte b : bytePubKey) {
			pubSb.append(String.format("%02x", b));
		}
		return pubSb.toString();
	}
}