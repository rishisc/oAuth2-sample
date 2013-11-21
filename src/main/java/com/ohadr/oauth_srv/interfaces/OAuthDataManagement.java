package com.ohadr.oauth_srv.interfaces;

import com.ohadr.oauth_srv.types.AuthenticationPolicy;
import com.ohadr.oauth_srv.types.OauthAccountState;


/**
 * manages the data like user's account lock, or number of login-retries, etc.
 * @author OhadR
 *
 */
public interface OAuthDataManagement
{
	public boolean setLoginSuccessForUser(String username);

	public void createUser(String email, String encodedPassword,
			String secretQuestion, String encodedAnswer);

	public AuthenticationPolicy getAuthenticationSettings();

	public OauthAccountState isAccountLocked(String email);

	public void sendPasswordRestoreMail(String email, String redirectUri);

	public String getSecretAnswer(String email);

	public boolean setLoginFailureForUser(String email);

	public void sendUnlockAccountMail(String email, String redirectUri);

	public void setPassword(String email, String encodedPassword);

	public void changePassword(String email, String encodedCurrentPassword,
			String encodedNewPassword);

	public String getSecretQuestion(String email);
}