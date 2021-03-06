package com.ohadr.authentication.token;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ohadr.authentication.config.AuthProperties;
import com.ohadr.crypto.interfaces.KeystoreService;



@Component
public class SignedTokenGenerator
{
    private static final Logger log = Logger.getLogger(SignedTokenGenerator.class);

	@Autowired
	private AuthProperties authProperties;

	@Autowired
	private KeystoreService keystoreService;

	private String encodeQueryParams(Map<String, String> parameters) throws UnsupportedEncodingException
	{
		String result = "";
		for (String paramName : parameters.keySet())
		{
			result += URLEncoder.encode(paramName, "UTF-8");
			result += "=" + URLEncoder.encode(parameters.get(paramName), "UTF-8") + "&";
		}
		return result;
	}

	private String createSignature(String data) throws NoSuchAlgorithmException,
	                                           InvalidKeyException,
	                                           SignatureException
	{
		log.info("creating signature for " + data);		//TODO debug
		Signature signature = Signature.getInstance("SHA1withDSA");
		signature.initSign(keystoreService.getPrivateKey());
		signature.update(data.getBytes());
		byte[] raw = signature.sign();
		return Base64.encodeBase64String(raw);
	}

	public String generateToken(String userEmail, String issuer, String deviceName, String clientPublicKey,
	                            int secondsToExpire) throws InvalidKeyException,
	                                                NoSuchAlgorithmException,
	                                                SignatureException,
	                                                UnsupportedEncodingException
	{
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("user", userEmail);
		parameters.put("issuer", issuer);
		parameters.put("expires", String.valueOf(new Date().getTime() / 1000 + secondsToExpire));
		if (deviceName != null)
		{
			parameters.put("device", deviceName);
		}
		if (clientPublicKey != null)
		{
			parameters.put("pubkey", clientPublicKey);
		}
		String data = encodeQueryParams(parameters);
		if(authProperties.isCryptoEnabled())
		{
			String signature = createSignature(data);
			data += ":" + signature;
		}
		return data;
	}
}
