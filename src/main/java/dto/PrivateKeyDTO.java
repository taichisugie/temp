package dto;

import java.io.Serializable;
import java.security.PrivateKey;

public class PrivateKeyDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private PrivateKey priv;

	public PrivateKey getPriv() {
		return this.priv;
	}

	public void setPriv(PrivateKey pub) {
		this.priv = pub;
	}

}
