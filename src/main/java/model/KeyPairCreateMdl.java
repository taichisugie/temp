package model;

import core.KeyGenerator;
import dao.RegistKeyPairDAO;
import dto.UserInfoDTO;

public class KeyPairCreateMdl {
	private UserInfoDTO user;

	public KeyPairCreateMdl(UserInfoDTO user) {
		// アドレス生成において必要な値はPKのIDのみ
		this.user = user;
	}
	
	public boolean create() throws Exception {
		RegistKeyPairDAO registor = new RegistKeyPairDAO(this.user.getId());
		KeyGenerator gen = new KeyGenerator();
		try {
			gen.create();
		} catch (Exception e) {
			// TODO とりあえず
			e.printStackTrace();
		}
		return registor.insert(gen.getPrivateKey(), gen.getPublicKey());
	}
}
