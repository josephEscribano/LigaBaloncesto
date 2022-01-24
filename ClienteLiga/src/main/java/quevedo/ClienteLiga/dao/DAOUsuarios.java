package quevedo.ClienteLiga.dao;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import quevedo.ClienteLiga.dao.retrofit.RetrofitUsuarios;
import quevedo.common.modelos.ApiRespuesta;
import quevedo.common.modelos.UsuarioDTO;
import quevedo.common.modelos.UsuarioRegistroDTO;
import quevedo.common.modelos.UsuarioUpdateDTO;

import javax.inject.Inject;
import java.util.List;

public class DAOUsuarios extends DAOGenerics {

    private final RetrofitUsuarios retrofitUsuarios;

    @Inject
    public DAOUsuarios(RetrofitUsuarios retrofitUsuarios) {
        this.retrofitUsuarios = retrofitUsuarios;
    }

    public Single<Either<String, UsuarioDTO>> doLogin(String user, String pass) {
        return safeSingleApicall(retrofitUsuarios.doLogin(user, pass)).subscribeOn(Schedulers.io());
    }

    public Single<Either<String, String>> doLogout() {
        return safeSingleApicall(retrofitUsuarios.doLogout()).subscribeOn(Schedulers.io());
    }

    public Single<Either<String, List<UsuarioDTO>>> getAll() {
        return safeSingleApicall(retrofitUsuarios.getUsuarios()).subscribeOn(Schedulers.io());
    }

    public Single<Either<String, UsuarioDTO>> saveUsuario(UsuarioRegistroDTO usuarioRegistroDTO) {
        return safeSingleApicall(retrofitUsuarios.saveUsuario(usuarioRegistroDTO)).subscribeOn(Schedulers.io());
    }

    public Single<Either<String, UsuarioDTO>> saveAdmin(UsuarioRegistroDTO usuarioRegistroDTO) {
        return safeSingleApicall(retrofitUsuarios.saveAdmin(usuarioRegistroDTO)).subscribeOn(Schedulers.io());
    }

    public Single<Either<String, ApiRespuesta>> reenviarCorreo(String username) {
        return safeSingleApicall(retrofitUsuarios.reenviarCorreo(username)).subscribeOn(Schedulers.io());
    }

    public Single<Either<String, UsuarioDTO>> cambiarPass(UsuarioDTO usuarioDTO) {
        return safeSingleApicall(retrofitUsuarios.cambiarPass(usuarioDTO));
    }

    public Single<Either<String, UsuarioDTO>> updateUsuario(UsuarioUpdateDTO usuarioUpdateDTO) {
        return safeSingleApicall(retrofitUsuarios.updateUsuario(usuarioUpdateDTO)).subscribeOn(Schedulers.io());
    }

    public Single<Either<String, String>> deleteUsuario(String id) {
        return safeSingleApicall(retrofitUsuarios.deleteUsuario(id)).subscribeOn(Schedulers.io());
    }


}
