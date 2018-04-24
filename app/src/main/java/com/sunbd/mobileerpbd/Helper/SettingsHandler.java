package com.sunbd.mobileerpbd.Helper;



import android.content.SharedPreferences;


public final class SettingsHandler {

	private static final SettingsHandler instance = new SettingsHandler();

	public static SettingsHandler getInstance() {
		return instance;
	}


	private String access_token = null;
	private String username = null;
	private String password = null;
	private String base_url = null;



	private SettingsHandler() {

		setAccess_token(null);
		setUsername(null);
		setPassword(null);
		setBase_url(null);

	}




	public final void load(SharedPreferences prefs) {

		if (prefs == null) {
			throw new IllegalArgumentException("Invalid pref editor : null");
		}

		try {
			setAccess_token(prefs.getString("access_token", null));
		} catch (Throwable e) {
		}

		try {
			setUsername(prefs.getString("username", null));
		} catch (Throwable e) {
		}


		try {
			setPassword(prefs.getString("password", null));
		} catch (Throwable e) {
		}

		try {
			setBase_url(prefs.getString("base_url", null));
		} catch (Throwable e) {
		}




	}

	public void commit(SharedPreferences.Editor prefsEditor) {

		if (prefsEditor == null) {
			throw new IllegalArgumentException("Invalid pref editor : null");
		}
		prefsEditor.putString("access_token", getAccess_token())
				.putString("username", getUsername())
				.putString("password", getUsername())
				.putString("base_url",getBase_url())
				.commit();

	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBase_url() {
		return base_url;
	}

	public void setBase_url(String base_url) {
		this.base_url = base_url;
	}
}
