package io.youcham.common.utils;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;

public class Encrypt {
	private final static String DES = "DES";
	private final static String MD5 = "MD5";
	// private static String dockKey = "";
	public static String eportKey = "0f1d510b-57fc-41e5-8338-7f9524fb16b9";

	// static {
	// try {
	// dockKey = "ac79a922"; //南昌码头的加密密钥
	// eportKey = "0f1d510b-57fc-41e5-8338-7f9524fb16b9"; //地方电子口岸加密密钥
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

	public static void main(String[] args) throws Exception {
		// String data =
		// "<ETA_ARRIVAL_TRAF><ARRIVAL_NO /><TRAF_NAME>dd</TRAF_NAME><VOYAGE_NO>d</VOYAGE_NO><TRAF_MODE>2</TRAF_MODE><TRN_MODE>3</TRN_MODE><TRAF_WAY>2</TRAF_WAY><ARRIVE_FLAG>Y</ARRIVE_FLAG><ARRIVE_TIME>20140724024850</ARRIVE_TIME><UNLOAD_CODE>4001</UNLOAD_CODE><PLACE /><OP_MODE>ADD</OP_MODE><GAT_ID></GAT_ID></ETA_ARRIVAL_TRAF><ETA_ARRIVAL_CONTA><CONTA_ID>TGHU0299460</CONTA_ID><BILL_NO>EGLV152420151434</BILL_NO><CONTA_TYPE>40HC</CONTA_TYPE><CONTA_STATUS>F</CONTA_STATUS><SEAL_NO /><TARE_WEIGHT /><CARGO /><PACK_NO /><REMARKS /><SEQNO /><CLIENT_SEQNO>201503251641280001</CLIENT_SEQNO></ETA_ARRIVAL_CONTA>";
		// String dat2 =
		// "<ETA_ARRIVAL_TRAF><ARRIVAL_NO /><TRAF_NAME>dd</TRAF_NAME><VOYAGE_NO>d</VOYAGE_NO><TRAF_MODE>2</TRAF_MODE><TRN_MODE>3</TRN_MODE><TRAF_WAY>2</TRAF_WAY><ARRIVE_FLAG>Y</ARRIVE_FLAG><ARRIVE_TIME>20140724024850</ARRIVE_TIME><UNLOAD_CODE>4001</UNLOAD_CODE><PLACE /><OP_MODE>ADD</OP_MODE><GAT_ID /></ETA_ARRIVAL_TRAF><ETA_ARRIVAL_CONTA><CONTA_ID>TGHU0299460</CONTA_ID><BILL_NO>EGLV152420151434</BILL_NO><CONTA_TYPE>40HC</CONTA_TYPE><CONTA_STATUS>F</CONTA_STATUS><SEAL_NO /><TARE_WEIGHT /><CARGO /><PACK_NO /><REMARKS /><SEQNO /><CLIENT_SEQNO>201503251641280001</CLIENT_SEQNO></ETA_ARRIVAL_CONTA>";
		// // String key = "cfe4a605-5280-4625-9d6b-b9e18c628c2e";
		// String key = dockKey;
		//
		// data = getMD5(data);
		// System.out.println("length:" + data.length() + ", " + data);
		// String encryptStr = encrypt(data, key);
		// System.out.println("length:" + encryptStr.length() + ", " +
		// encryptStr);
		// System.out.println(decrypt(encryptStr, key));

	}

	/**
	 * 验证报文摘要有没有经过篡改
	 * 
	 * @param content
	 *            报文内容CONTENT节点内容
	 * @param verifySign
	 *            报文头中hashSign密文值
	 * @return
	 * @throws Exception
	 */
	public static boolean verifyCertificate(String content, String verifySign)
			throws Exception {
		content = getMD5(content);
		String encryptStr = encrypt(content, eportKey);
		if (encryptStr.equals(verifySign)) {
			return true;
		}
		return false;
	}

	/**
	 * 获取物流平台对报文CONTENT节点经过DES算法加密后的字符串
	 * 
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static String getNcwlptEncrypt(String content) throws Exception {
		content = getMD5(content);
		return encrypt(content, eportKey);
	}

	/**
	 * 获取报文摘要MD5运算后的字符串
	 * 
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static String getMD5(String content) throws Exception {
		MessageDigest md = MessageDigest.getInstance(MD5);
		// TODO  Base64 2019-03-19
//		BASE64Encoder base = new BASE64Encoder();
//		return base.encode(md.digest(content.getBytes()));
		return Base64.encodeBase64String(md.digest(content.getBytes()));
	}

	/**
	 * Description 根据密钥进行加密
	 * 
	 * @param data
	 * @param key
	 *            加密键
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String data, String key) throws Exception {
		byte[] bt = encrypt(data.getBytes(), key.getBytes());
		
		// TODO  Base64 2019-03-19
//		String strs = new BASE64Encoder().encode(bt);
		String strs = Base64.encodeBase64String(bt);
		
		return strs;
	}

	/**
	 * Description 根据密钥对数据进行解密
	 * 
	 * @param data
	 * @param key
	 *            加密键
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static String decrypt(String data, String key) throws IOException,
			Exception {
		if (data == null)
			return null;
		
		// TODO  Base64 2019-03-19
//		BASE64Decoder decoder = new BASE64Decoder();
//		byte[] buf = decoder.decodeBuffer(data);
		byte[] buf = Base64.decodeBase64(data); 
		
		byte[] bt = decrypt(buf, key.getBytes());
		return new String(bt);
	}

	/**
	 * Description 根据键值进行加密
	 * 
	 * @param data
	 * @param key
	 *            加密键byte数组
	 * @return
	 * @throws Exception
	 */
	private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		// 生成一个可信任的随机数源
		SecureRandom sr = new SecureRandom();

		// 从原始密钥数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);

		// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);

		SecretKey securekey = keyFactory.generateSecret(dks);

		// Cipher对象实际完成加密操作
		Cipher cipher = Cipher.getInstance(DES);

		// 用密钥初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

		return cipher.doFinal(data);
	}

	/**
	 * Description 根据键值进行解密
	 * 
	 * @param data
	 * @param key
	 *            加密键byte数组
	 * @return
	 * @throws Exception
	 */
	private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		// 生成一个可信任的随机数源
		SecureRandom sr = new SecureRandom();

		// 从原始密钥数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);

		// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);

		SecretKey securekey = keyFactory.generateSecret(dks);

		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance(DES);

		// 用密钥初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

		return cipher.doFinal(data);
	}
}
