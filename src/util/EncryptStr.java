package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptStr {
	public String encryptionStr(String pwd) {
		String encStr = null;
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			
			md.update(pwd.getBytes()); // 암호화할 변수 값을 등록
			byte[] resultData = md.digest(); // 실제 암호화 된 값(정수byte 배열)
			
			// resultData에 있는 암호화된 정수 값을 16진수로 바꾸어야 한다
			StringBuffer sb = new StringBuffer();
			for (byte b : resultData) {
				String t = Integer.toString((b & 0xff) + 0x100, 16).substring(1);
				sb.append(t);			
			}
			
			encStr = sb.toString();
			
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return encStr;
	}
}
