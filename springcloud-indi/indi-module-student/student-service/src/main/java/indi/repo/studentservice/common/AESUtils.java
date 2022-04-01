package indi.repo.studentservice.common;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;

/**
 * Function: AES加解密. <br/>
 * Date: 2016年9月2日 上午10:08:30 <br/>
 *
 * @author jianghm
 * @version 1.0
 * @Copyright (c) 2016, 杭州市民卡有限公司 All Rights Reserved.
 */
public class AESUtils {

	private static Logger logger = LoggerFactory.getLogger(AESUtils.class);

	/**
	 * 加密用的Key可以用26个字母和数字组成 此处使用AES-128-CBC加密模式，key需要为16位
	 */
	private static String sKey = "abcdef0123456789";
	private static String ivParameter = "0123456789abcdef";
	private static AESUtils instance = null;

	private AESUtils() {

	}

	public static AESUtils getInstance() {
		if (instance == null) {
			instance = new AESUtils();
		}
		return instance;
	}

	// 加密
	public static String encrypt(String sSrc) {
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			byte[] raw = sKey.getBytes();
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
			return Base64.encodeBase64String(encrypted);
		}
		catch (Exception e) {
			logger.error("AES加密异常：", e);
		}
		return "";
	}

	// 解密
	public String decrypt(String sSrc) {
		try {
			byte[] raw = sKey.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] encrypted1 = Base64.decodeBase64(sSrc);// 先用base64解密
			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original, "utf-8");
			return originalString;
		}
		catch (Exception e) {
			logger.error("AES解密异常：", e);
		}
		return null;
	}

	/**
	 * 加密
	 *
	 * @param content
	 *            需要加密的内容
	 * @param password
	 *            加密密码
	 * @return
	 */
	public static byte[] encrypt(String content, String password) {
		try {
			// 这里是128bit密钥，所以是 16 byte,如果是其它长度的 除以8 ，修改，这里简单演示一下
			byte[] newkey = new byte[16];
			for (int i = 0; i < newkey.length && i < password.getBytes().length; i++) {
				newkey[i] = password.getBytes()[i];
			}

			SecretKeySpec key = new SecretKeySpec(newkey, "AES");
			// 创建密码器，AES默认是/ECB/PKCS5Padding
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			byte[] byteContent = content.getBytes("utf-8");
			// 初始化
			cipher.init(Cipher.ENCRYPT_MODE, key);
			// 加密
			byte[] result = cipher.doFinal(byteContent);
			return result;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 加密成16进制字符串
	 *
	 * @param content
	 *            需要加密的内容
	 * @param password
	 *            加密密码
	 * @return
	 */
	public static String encryptToHex(String content, String password) {
		return parseByte2HexStr(encrypt(content, password));
	}

	/**
	 * 解密
	 *
	 * @param content
	 *            待解密内容
	 * @param password
	 *            解密密钥
	 * @return
	 */
	public static String decrypt(byte[] content, String password) {
		try {
			// 这里是128bit密钥，所以是 16 byte,如果是其它长度的 除以8 ，修改，这里简单演示一下
			byte[] newkey = new byte[16];
			for (int i = 0; i < newkey.length && i < password.getBytes().length; i++) {
				newkey[i] = password.getBytes()[i];
			}

			SecretKeySpec key = new SecretKeySpec(newkey, "AES");
			// 创建密码器
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			// 初始化
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] result = cipher.doFinal(content);
			// 加密
			return new String(result, Charset.forName("UTF-8"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 从16进制内容中解密
	 *
	 * @param hexContent
	 *            16进制的加密内容
	 * @param password
	 *            密码
	 * @return
	 */
	public static String decryptFromHex(String hexContent, String password) {
		return decrypt(parseHexStr2Byte(hexContent), password);
	}

	/**
	 * 将二进制转换成16进制
	 *
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 *
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

}
