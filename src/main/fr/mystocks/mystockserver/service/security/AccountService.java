package fr.mystocks.mystockserver.service.security;

import fr.mystocks.mystockserver.data.security.Account;

public interface AccountService {

	Account authenticate(String user, String password);

	Account authenticateWithToken(String token);

}
