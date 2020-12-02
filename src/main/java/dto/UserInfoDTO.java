package dto;

import java.io.Serializable;
import java.util.List;

public class UserInfoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String username;
	private boolean loginResult;
	private List<String> addrList;
	
	
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUsername() {
		return this.username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public boolean getLoginResult() {
		return this.loginResult;
	}
	public void setLoginResult(boolean result) {
		this.loginResult = result;
	}
	
	public List<String> getAddrList() {
		return this.addrList;
	}
	public void setAddrList(List<String> pkhList2) {
		this.addrList = pkhList2;
	}

}
