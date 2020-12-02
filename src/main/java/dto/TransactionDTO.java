package dto;

import java.io.Serializable;
import java.security.PublicKey;
import java.security.Signature;

public class TransactionDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String useTxid; //hex
	private int outputIdx;
	private Signature sign;
	private PublicKey pubKey;
	private int amount;
	private String destPKH;
	
	//hex
	public String getUseTxid() {
		return useTxid;
	}
	//hex
	public void setUseTxid(String txid) {
		this.useTxid = txid;
	}
	public int getOutputIdx() {
		return outputIdx;
	}
	public void setOutputIdx(int outputIdx) {
		this.outputIdx = outputIdx;
	}
	public Signature getSign() {
		return sign;
	}
	public void setSign(Signature sign) {
		this.sign = sign;
	}
	public PublicKey getPubKey() {
		return pubKey;
	}
	public void setPubKey(PublicKey pubKey) {
		this.pubKey = pubKey;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getDestPKH() {
		return destPKH;
	}
	public void setDestPKH(String destPKH) {
		this.destPKH = destPKH;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	                   
	
	
}
