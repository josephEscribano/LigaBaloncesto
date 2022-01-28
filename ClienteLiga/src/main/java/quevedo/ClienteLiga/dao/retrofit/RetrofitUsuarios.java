package quevedo.ClienteLiga.dao.retrofit;

import io.reactivex.rxjava3.core.Single;
import quevedo.ClienteLiga.dao.utils.ConstantesDAO;
import quevedo.ClienteLiga.dao.utils.ConstantesPath;
import quevedo.common.modelos.ApiRespuesta;
import quevedo.common.modelos.UsuarioDTO;
import quevedo.common.modelos.UsuarioRegistroDTO;
import quevedo.common.modelos.UsuarioUpdateDTO;
import retrofit2.http.*;

import java.util.List;

public interface RetrofitUsuarios {

    @GET(ConstantesPath.PATH_DO_LOGIN)
    Single<UsuarioDTO> doLogin(@Query(ConstantesDAO.PARAMETER_USER) String user);

    @PUT(ConstantesPath.PATH_REENVIAR_CORREO)
    Single<ApiRespuesta> reenviarCorreo(@Query(ConstantesPath.PATH_PARAMETER_USER) String userName);

    @GET(ConstantesPath.PATH_API_USUARIOS)
    Single<List<UsuarioDTO>> getUsuarios();

    @POST(ConstantesPath.PATH_API_USUARIOS)
    Single<UsuarioDTO> saveUsuario(@Body UsuarioRegistroDTO usuarioRegistroDTO);

    @POST(ConstantesDAO.PATH_API_USUARIOS_INSERADMIN)
    Single<UsuarioDTO> saveAdmin(@Body UsuarioRegistroDTO usuarioRegistroDTO);

    @PUT(ConstantesPath.PATH_API_USUARIOS)
    Single<UsuarioDTO> updateUsuario(@Body UsuarioUpdateDTO usuarioUpdateDTO);

    @DELETE(ConstantesPath.PATH_UPDATE_USUARIOS)
    Single<String> deleteUsuario(@Path(ConstantesPath.PATH_PARAMETER_ID) String id);

    @PUT(ConstantesPath.PATH_API_USUARIOS_CAMBIOPASS)
    Single<UsuarioDTO> cambiarPass(@Body UsuarioDTO usuarioDTO);


}
