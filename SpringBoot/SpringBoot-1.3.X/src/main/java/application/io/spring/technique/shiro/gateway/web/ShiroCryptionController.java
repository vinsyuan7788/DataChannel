package application.io.spring.technique.shiro.gateway.web;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 	This is a class to test Shiro cryption (mainly encryption)
 * 
 * @author vinsy
 *
 */
@SuppressWarnings({"unused"})
@RestController
@RequestMapping("/shiro/cryption")
public class ShiroCryptionController {

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	@RequestMapping(value = "/testCryption", method = RequestMethod.GET)
	public Map<String, Object> testCryption() throws Exception {
		
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		
		String originalData = "Hello Shiro!";
		result.put("originalData", originalData);
		
		String salt = "abcde12345";
		result.put("salt", salt);
		
		// Here performs MD5 encryption
		String dataEncryptedByMd5 = new Md5Hash(originalData, salt, 1).toString();
		result.put("dataEncryptedByMd5", dataEncryptedByMd5);
		
		// Here performs SHA-256-Hash encryption
		String dataEncryptedBySha256Hash = new Sha256Hash(originalData, salt, 1).toString();
		result.put("dataEncryptedBySha256Hash", dataEncryptedBySha256Hash);
		
		// Here performs encryption by defaultHashService: the following setting values are all default values
		DefaultHashService defaultHashService = new DefaultHashService();
		defaultHashService.setHashAlgorithmName("SHA-512");
		defaultHashService.setHashIterations(1);
		defaultHashService.setGeneratePublicSalt(false);
		defaultHashService.setPrivateSalt(null);
		defaultHashService.setRandomNumberGenerator(new SecureRandomNumberGenerator());
		
		HashRequest hashRequest = new HashRequest.Builder()
				.setAlgorithmName("MD5")
	            .setSource(ByteSource.Util.bytes(originalData))  
	            .setSalt(ByteSource.Util.bytes("123"))
	            .setIterations(2)
	            .build();
		
		String dataEncryptedByDefaultHashService = defaultHashService.computeHash(hashRequest).toString();
		result.put("dataEncryptedByDefaultHashService", dataEncryptedByDefaultHashService);
		
		// Here performs AES encryption
		AesCipherService aesCipherService = new AesCipherService();
		aesCipherService.setKeySize(128);
		Key key = aesCipherService.generateNewKey();
		
		String dataEncryptedByAes = aesCipherService.encrypt(originalData.getBytes(), key.getEncoded()).toHex();
		result.put("dataEncryptedByAes", dataEncryptedByAes);
		
		String dataDecryptedByAes = new String(aesCipherService.decrypt(Hex.decode(dataEncryptedByAes), key.getEncoded()).getBytes());
		result.put("dataDecryptedByAes", dataDecryptedByAes);
		
		// Return data
		data.put("status", 1);
		data.put("msg", "success");
		data.put("result", result);
		return data;
	}
}
