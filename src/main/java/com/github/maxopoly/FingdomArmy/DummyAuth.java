package com.github.maxopoly.FingdomArmy;

import com.github.maxopoly.angeliacore.connection.login.AuthenticationHandler;
import java.io.IOException;
import org.apache.logging.log4j.Logger;

public class DummyAuth extends AuthenticationHandler {

	public DummyAuth(String name) throws IOException {
		super(name, (String) null, (Logger) null);
		this.playerName = name;
	}

	@Override
	public void authenticate(String userName, String password, Logger logger) throws IOException {
	}

	@Override
	public void refreshToken(Logger logger) throws IOException {

	}

	@Override
	public boolean validateToken(Logger logger) throws IOException {
		return true;
	}

	@Override
	public void authAgainstSessionServer(String sha, Logger logger) throws IOException {

	}
}
