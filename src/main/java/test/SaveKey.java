package test;

import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;

public class SaveKey implements Serializable{
	private static final long serialVersionUID = 3525241124L;
	
	private PrivateKey privateKey;
	
	private PublicKey publicKey;
	

	public PrivateKey getPrivateKey() {
		return this.privateKey;
	}

	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

	public PublicKey getPublicKey() {
		return this.publicKey;
	}

	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}
}
