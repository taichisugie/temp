package test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.PrivateKey;

import core.KeyGenerator;

public class SaveTest {
	static String filename = "/Users/sugietaichi/eclipse-workspace/aobcp/keyholder/test.kh";
	static PrivateKey p = null;
	static PrivateKey p2 = null;
	public static void main(String[] args) throws Exception {
		//永続化
		SaveKey saver = new SaveKey();
		KeyGenerator g = new KeyGenerator();
		g.create();
		p2 = g.getPrivateKey();
		saver.setPrivateKey(p2);
		saver.setPublicKey(g.getPublicKey());
		
		try(ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream(filename))){
			
			oos.writeObject(saver);
			oos.flush();
		}
		
			
		//復元
		try(ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream(filename))){
			
			SaveKey k = (SaveKey) ois.readObject();
			p = k.getPrivateKey();
		}
		
		TU.H("p", p.getEncoded());
		TU.H("p2", p2.getEncoded());
	}
}
