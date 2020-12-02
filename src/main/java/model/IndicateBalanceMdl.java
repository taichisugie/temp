package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.UTXODAO;
import dto.TransactionDTO;
import dto.UserInfoDTO;

public class IndicateBalanceMdl {
	private UserInfoDTO user;

	public IndicateBalanceMdl(UserInfoDTO user) {
		this.user = user;
	}
	
	public Map<String, Integer> find() throws Exception {
		
		//まだアドレス一覧を取得していない場合
		if(this.user.getAddrList() == null) {
			AddrListMdl addrListMdl = new AddrListMdl(this.user);
			//アドレス一覧を追加したログインユーザーに更新
			this.user = addrListMdl.find();
		}
		
		//ログインユーザのキーペア取得
//		CountBalanceDAO balance = new CountBalanceDAO(this.user);
		
		Map<String, Integer> balanceMap = new HashMap<>();
		
		UTXODAO UTXODAO = new UTXODAO();
		List<TransactionDTO> UTXO_tx = UTXODAO.getUTXO();
		
		for(TransactionDTO tx : UTXO_tx) {
			String rcvAddr = tx.getDestPKH();
			for(String addr : this.user.getAddrList()) {
				if(rcvAddr.equals(addr)) {
					balanceMap.put(addr,tx.getAmount());
				}
			}
		}
		
		return balanceMap;
	}
	
}
