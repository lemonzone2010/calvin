package com.apusic.ebiz.smartorg.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.util.StringUtils;

import com.apusic.ebiz.model.user.User;

public class UserPasswordCredentialPolicyManagerImpl implements
		UserPasswordCredentialPolicyManager {

	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	private String algorithm;

	private String characterEncoding;

	private boolean salt;

	public UserPasswordCredentialPolicyManagerImpl(String algorithm,
			boolean salt) throws NoSuchAlgorithmException {
		this.algorithm = algorithm;
		this.salt = salt;
	}

	public String encrypt(User user) {
		if (salt) {
			return encode(user.getPassword() + user.getName());
		} else {
			return encode(user.getPassword());
		}
	}

	private String encode(final String password) {
		if (password == null) {
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest
					.getInstance(this.algorithm);
			if (StringUtils.hasText(this.characterEncoding)) {
				messageDigest.update(password.getBytes(this.characterEncoding));
			} else {
				messageDigest.update(password.getBytes());
			}
			final byte[] digest = messageDigest.digest();
			return getFormattedText(digest);
		} catch (final NoSuchAlgorithmException e) {
			throw new SecurityException(e);
		} catch (final UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Takes the raw bytes from the digest and formats them correct.
	 *
	 * @param bytes
	 *            the raw bytes from the digest.
	 * @return the formatted bytes.
	 */
	private String getFormattedText(byte[] bytes) {
		final StringBuilder buf = new StringBuilder(bytes.length * 2);

		for (int j = 0; j < bytes.length; j++) {
			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString();
	}

	public void setCharacterEncoding(String characterEncoding) {
		this.characterEncoding = characterEncoding;
	}
}
