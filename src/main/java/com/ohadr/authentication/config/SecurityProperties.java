package com.ohadr.authentication.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecurityProperties
{
	@Value("${com.ohadr.oauth2.sign.keyStore}")
	private String keystore;

	@Value("${com.ohadr.oauth2.sign.storePass}")
	private String storepass;

	@Value("${com.ohadr.oauth2.sign.keyAlias}")
	private String keysToken;

	public String getKeystore()
	{
		return keystore;
	}

	public void setKeystore(String keystore)
	{
		this.keystore = keystore;
	}

	public String getStorepass()
	{
		return storepass;
	}

	public void setStorepass(String storepass)
	{
		this.storepass = storepass;
	}

	public String getKeysToken()
	{
		return keysToken;
	}

	public void setKeysToken(String keysToken)
	{
		this.keysToken = keysToken;
	}

}
