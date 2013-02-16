package com.hyperactivity.android_app.core;

import java.util.Date;
import java.util.UUID;

import android.graphics.Bitmap;

public class ServerLink {
	private Error	errorCode;
	private Engine	engine;

	public ServerLink(Engine engine) {
		this.engine = engine;
		errorCode = Error.NO_ERROR;
	}

	public Account login(String username, String password) {
		// TODO: make server auth request.
		boolean auth = true;

		if (auth) {
			// Populate an account with dummy data for now
			Account account = new Account(UUID.randomUUID().toString());
			account.setUsername(username);
			account.setProfile(getProfile(account.getId()));
			account.setLoaded(true);

			errorCode = Error.NO_ERROR;
			return account;
		}
		else {
			errorCode = Error.LOGIN_FAILED;
			return null;
		}
	}

	public Profile getProfile(String id) {
		// TODO: make server auth request.
		boolean exist = true;

		if (exist) {
			// Populate an profile with dummy data for now
			Profile profile = new Profile(UUID.randomUUID().toString());
			profile.setAvatar(Bitmap.createBitmap(32, 32, Bitmap.Config.ARGB_8888));
			profile.setBirthdate(new Date());
			profile.setDescription("Im a dummy user :)");
			profile.setLoaded(true);

			errorCode = Error.NO_ERROR;
			return profile;
		}
		else {
			errorCode = Error.PROFILE_NOT_FOUND;
			return null;
		}
	}

	public Profile uploadProfile(Profile profile) {
		if (profile.isLoaded()) {
			// Make sure profile and currently logged in user have the same id.
			if (Utils.sameId(engine.getAccount(), profile)) {
				// TODO: make the server request
				boolean success = true;

				if (success) {
					errorCode = Error.NO_ERROR;
					return profile;
				}
				else {
					errorCode = Error.PROFILE_UPLOAD_DENIED;
					return null;
				}
			}
			else {
				errorCode = Error.ID_MISMATCH;
				return null;
			}
		}
		else {
			errorCode = Error.NOT_LOADED;
			return null;
		}
	}

	public boolean changePassword(String password) {
		if (engine.getAccount().isLoaded()) {
			// TODO: make the password change remote
			boolean success = true;

			if (success) {
				errorCode = Error.NO_ERROR;
				return true;
			}
			else {
				errorCode = Error.CHANGE_PASSWORD_DENIED;
				return false;
			}
		}
		else {
			errorCode = Error.NOT_LOADED;
			return false;
		}
	}

	public Error getErrorCode() {
		return errorCode;
	}
}
