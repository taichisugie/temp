package test;

import java.io.Serializable;
import java.security.PublicKey;
import java.security.Signature;

public class Tx implements Serializable{
	private static final long serialVersionUID = 1L;
	public byte[] txid; //使用する承認済みトランザクションのSHA256d
	public int outputIndex;
	public Signature sign;
	public PublicKey pubKey;	
	public int amount;
}
