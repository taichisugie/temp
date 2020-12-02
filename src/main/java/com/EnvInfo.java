package com;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * シングルトンで静的なプロパティ読み込みクラス
 */
public class EnvInfo {
	public enum EnvInfoSingleton {
		INSTANCE;
		EnvInfo envInfoInstance = new EnvInfo();
		public EnvInfo getInstance() {
			return this.envInfoInstance;
		}
	}

	private Properties properties;
	
	private EnvInfo() {
		this.properties = new Properties();
		try(InputStream is = this.getClass().getResourceAsStream("app.ini")) {
			properties.load(new InputStreamReader(is, "UTF-8"));
			is.close();
		} catch (IOException e) {
			System.out.println("共通設定ファイルの読み込みに失敗しました。");//TODO エラー処理
		}
	}
	
	/* 単数のgetter */
	public String getProperty(String key) {
		String rtnValue = this.properties.getProperty(key);
		return rtnValue == null ? "" : rtnValue;
	}
}
