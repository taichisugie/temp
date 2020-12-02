package model;

import java.io.IOException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import core.CryptUtils;
import dao.FindPubKeyDAO;
import dto.UserInfoDTO;

public class AddrListMdl {
	private UserInfoDTO user;

	public AddrListMdl(UserInfoDTO user) {
		// アドレス生成において必要な値はPKのIDのみ
		this.user = user;
	}

	public UserInfoDTO find() throws ClassNotFoundException, IOException {
		// IDしか使用しないため、UserInfoからIDを取得して引き渡す
		FindPubKeyDAO pubKeyFinder = new FindPubKeyDAO(this.user.getId());
		List<PublicKey> pubList = pubKeyFinder.find(); // 公開鍵リスト
		List<String> pkhList = null;
		try {
			pkhList = this.pubKeyToAddr(pubList);
		} catch (Exception e) {
			// TODO とりあえず
			e.printStackTrace();
		}
		this.user.setAddrList(pkhList);
		
		//アドレスを格納したUserInfoインスタンスを返却
		return this.user;
	}

	private List<String> pubKeyToAddr(List<PublicKey> pubList) throws Exception {
		// アドレス格納リスト
		List<String> addrList = new ArrayList<>();

		Iterator<PublicKey> i = pubList.iterator();
		while (i.hasNext()) {
			String PKH = CryptUtils.pubKeyFromAddress(i.next());
			addrList.add(PKH);
		}
		return addrList;
	}
}
