package quevedo.ClienteLiga.service;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import quevedo.ClienteLiga.dao.DAOUsuarios;
import quevedo.common.modelos.ApiRespuesta;
import quevedo.common.modelos.UsuarioDTO;
import quevedo.common.modelos.UsuarioRegistroDTO;
import quevedo.common.modelos.UsuarioUpdateDTO;

import javax.inject.Inject;
import java.util.List;

public class ServiceUsuarios {

    private final DAOUsuarios daoUsuarios;

    @Inject
    public ServiceUsuarios(DAOUsuarios daoUsuarios) {
        this.daoUsuarios = daoUsuarios;
    }

    public Single<Either<String, UsuarioDTO>> doLogin(String user, String pass) {
        return daoUsuarios.doLogin(user, pass);
    }

    public Single<Either<String, String>> doLogout() {
        return daoUsuarios.doLogout();
    }

    public Single<Either<String, List<UsuarioDTO>>> getAll() {
        return daoUsuarios.getAll();
    }

    public Single<Either<String, UsuarioDTO>> saveUsuario(UsuarioRegistroDTO usuarioRegistroDTO) {
        return daoUsuarios.saveUsuario(usuarioRegistroDTO);
    }

    public Single<Either<String, UsuarioDTO>> saveAdmin(UsuarioRegistroDTO usuarioRegistroDTO) {
        return daoUsuarios.saveAdmin(usuarioRegistroDTO);
    }

    public Single<Either<String, ApiRespuesta>> reenviarCorreo(String username) {
        return daoUsuarios.reenviarCorreo(username);
    }

    public Single<Either<String, UsuarioDTO>> cambiarPass(UsuarioDTO usuarioDTO) {
        return daoUsuarios.cambiarPass(usuarioDTO);
    }

    public Single<Either<String, UsuarioDTO>> updateUsuario(UsuarioUpdateDTO usuarioUpdateDTO) {
        return daoUsuarios.updateUsuario(usuarioUpdateDTO);
    }

    public Single<Either<String, String>> deleteUsuario(String id) {
        return daoUsuarios.deleteUsuario(id);
    }
}
