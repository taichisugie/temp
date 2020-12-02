package dto;

import java.io.Serializable;
import java.security.PublicKey;

public class PublicKeyDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private PublicKey pub;

	public PublicKey getPub() {
		return this.pub;
	}

	public void setPub(PublicKey pub) {
		this.pub = pub;
	}

}
