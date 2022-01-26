package quevedo.servidorLiga.EE.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import quevedo.common.utils.ConstantesCommon;
import quevedo.servidorLiga.EE.utils.ConstantesRest;

import java.security.Key;
import java.util.Base64;

@ApplicationScoped
public class JWTAuth implements HttpAuthenticationMechanism {

    private final InMemoryIdenteityStore identity;
    private final Key key;

    @Inject
    public JWTAuth(InMemoryIdenteityStore identity,@Named(ConstantesRest.JWT) Key key) {
        this.identity = identity;
        this.key = key;
    }

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse,
                                                HttpMessageContext httpMessageContext) throws AuthenticationException {

        CredentialValidationResult c = CredentialValidationResult.INVALID_RESULT;


        String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null) {
            String[] valores = header.split(" ");

            if (valores[0].equalsIgnoreCase(ConstantesCommon.BASIC)) {
                String userPass = new String(Base64.getUrlDecoder().decode(valores[1]));
                String[] userPassSeparado = userPass.split(ConstantesCommon.DOS_PUNTOS);
                c = identity.validate(new UsernamePasswordCredential(userPassSeparado[0], userPassSeparado[1]));

                if (c.getStatus() == CredentialValidationResult.Status.VALID) {
                    httpServletRequest.getSession().setAttribute(ConstantesCommon.LOGIN_PARAMETER, c);
                }
            }else{
                Jws<Claims> jws = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(header);


            }

        } else
        {
            if (httpServletRequest.getSession().getAttribute(ConstantesCommon.LOGIN_PARAMETER)!=null)
                c = (CredentialValidationResult)httpServletRequest.getSession().getAttribute(ConstantesCommon.LOGIN_PARAMETER);
        }

        if (c.getStatus().equals(CredentialValidationResult.Status.INVALID))
        {
            return httpMessageContext.doNothing();
        }
        return httpMessageContext.notifyContainerAboutLogin(c);

    }
}
