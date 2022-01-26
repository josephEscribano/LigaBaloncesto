package quevedo.servidorLiga.EE.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import quevedo.servidorLiga.EE.utils.ConstantesRest;

import java.security.Key;

public class KeyProvider {

    @Produces
    @Singleton
    @Named(ConstantesRest.JWT)
    public Key key() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }
}
