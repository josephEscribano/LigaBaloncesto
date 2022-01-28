package quevedo.servidorLiga.EE.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.HttpHeaders;
import lombok.extern.log4j.Log4j2;
import quevedo.common.utils.ConstantesCommon;
import quevedo.servidorLiga.EE.utils.ConstantesRest;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Singleton
@Log4j2
public class JWTAuth implements HttpAuthenticationMechanism {

    private final InMemoryIdenteityStore identity;
    private final Key key;

    @Inject
    public JWTAuth(InMemoryIdenteityStore identity, @Named(ConstantesRest.JWT) Key key) {
        this.identity = identity;
        this.key = key;
    }

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse,
                                                HttpMessageContext httpMessageContext) throws AuthenticationException {

        CredentialValidationResult credentialValidationResult;

        AuthenticationStatus authenticationStatus = httpMessageContext.doNothing();
        String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null) {
            String[] valores = header.split(" ");

            if (valores[0].equalsIgnoreCase(ConstantesCommon.BASIC)) {
                String userPass = new String(Base64.getUrlDecoder().decode(valores[1]));
                String[] userPassSeparado = userPass.split(ConstantesCommon.DOS_PUNTOS);
                credentialValidationResult = identity.validate(new UsernamePasswordCredential(userPassSeparado[0], userPassSeparado[1]));

                if (credentialValidationResult.getStatus() == CredentialValidationResult.Status.VALID) {
                    httpServletResponse.setHeader(HttpHeaders.AUTHORIZATION, generarToken(credentialValidationResult));
                    authenticationStatus = httpMessageContext.notifyContainerAboutLogin(credentialValidationResult);

                }


            } else if (valores[0].equalsIgnoreCase(ConstantesCommon.BEARER)) {
                try {
                    Jws<Claims> jws = Jwts.parserBuilder()
                            .setSigningKey(key)
                            .build()
                            .parseClaimsJws(valores[1]);
                    List<String> groups = (List<String>) jws.getBody().get(ConstantesRest.GROUP);
                    credentialValidationResult = new CredentialValidationResult(jws.getBody().get(ConstantesRest.PARAM_USER).toString(), new HashSet<>(groups));
                    authenticationStatus = httpMessageContext.notifyContainerAboutLogin(credentialValidationResult);
                } catch (ExpiredJwtException ex) {
                    log.error(ex.getMessage(), ex);
                    httpServletResponse.setHeader(HttpHeaders.EXPIRES, ConstantesCommon.TOKEN_EXPIRADO);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }

            }

        }


        return authenticationStatus;

    }

    private String generarToken(CredentialValidationResult credentialValidationResult) {
        return Jwts.builder()
                .setExpiration(Date.from(LocalDateTime.now().plusMinutes(1).atZone(ZoneId.systemDefault())
                        .toInstant()))
                .claim(ConstantesRest.PARAM_USER, credentialValidationResult.getCallerPrincipal().getName())
                .claim(ConstantesRest.GROUP, credentialValidationResult.getCallerGroups())
                .signWith(key)
                .compact();
    }

}
