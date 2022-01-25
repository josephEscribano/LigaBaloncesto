package quevedo.servidorLiga.EE.security;

import io.vavr.control.Either;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import quevedo.common.errores.ApiError;
import quevedo.servidorLiga.EE.utils.ConstantesRest;
import quevedo.servidorLiga.dao.modelos.Usuario;
import quevedo.servidorLiga.service.UsuarioService;

import java.util.Collections;

import static jakarta.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;

@ApplicationScoped
public class InMemoryIdenteityStore implements IdentityStore {
    private final UsuarioService usuarioService;

    @Inject
    public InMemoryIdenteityStore(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public int priority() {
        return 10;
    }

    @Override
    public CredentialValidationResult validate(Credential credential) {
        CredentialValidationResult credentialValidationResult = INVALID_RESULT;
        if (credential instanceof UsernamePasswordCredential){
            UsernamePasswordCredential user = UsernamePasswordCredential.class.cast(credential);

            Either<ApiError, Usuario> resultado =usuarioService.doLogin(user.getCaller(),user.getPasswordAsString());
            if(resultado.isRight()){
                if (resultado.get().getIdTipoUsuario().equals(ConstantesRest.DOS)){
                    credentialValidationResult = new CredentialValidationResult("admin", Collections.singleton("admin"));
                }else{
                    credentialValidationResult = new CredentialValidationResult("normal",Collections.singleton("normal"));
                }
            }
        }
        return credentialValidationResult;
    }
}
