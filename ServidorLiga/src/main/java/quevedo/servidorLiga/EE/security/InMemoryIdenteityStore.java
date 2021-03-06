package quevedo.servidorLiga.EE.security;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import quevedo.common.errores.ApiError;
import quevedo.common.utils.ConstantesCommon;
import quevedo.servidorLiga.EE.utils.ConstantesRest;
import quevedo.servidorLiga.dao.modelos.Usuario;
import quevedo.servidorLiga.service.UsuarioService;

import java.util.Set;

import static jakarta.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;

@Singleton
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
        if (credential instanceof UsernamePasswordCredential) {
            UsernamePasswordCredential user = (UsernamePasswordCredential) credential;

            Either<ApiError, Usuario> resultado = usuarioService.doLogin(user.getCaller(), user.getPasswordAsString());
            if (resultado.isRight()) {
                if (resultado.get().getIdTipoUsuario().equals(ConstantesRest.DOS)) {
                    credentialValidationResult = new CredentialValidationResult(resultado.get().getUserName(), Set.of(ConstantesCommon.ADMIN, ConstantesCommon.NORMAL));
                } else {
                    credentialValidationResult = new CredentialValidationResult(resultado.get().getUserName(), Set.of(ConstantesCommon.NORMAL));
                }
            }
        }
        return credentialValidationResult;
    }
}
