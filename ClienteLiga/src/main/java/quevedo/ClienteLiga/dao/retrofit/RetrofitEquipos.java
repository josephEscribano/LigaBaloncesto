package quevedo.ClienteLiga.dao.retrofit;

import io.reactivex.rxjava3.core.Single;
import quevedo.ClienteLiga.dao.utils.ConstantesPath;
import quevedo.common.modelos.Equipo;
import retrofit2.http.*;

import java.util.List;

public interface RetrofitEquipos {

    @GET(ConstantesPath.PATH_API_EQUIPOS)
    Single<List<Equipo>> getEquipos();

    @POST(ConstantesPath.PATH_INSERT_EQUIPOS)
    Single<Equipo> insertEquipo(@Path(ConstantesPath.PATH_PARAMETER_NAME) String nombre);

    @PUT(ConstantesPath.PATH_API_EQUIPOS)
    Single<Equipo> updateEquipo(@Body Equipo equipo);

    @DELETE(ConstantesPath.PATH_DELETE_EQUIPOS)
    Single<String> deleteEquipo(@Path(ConstantesPath.PATH_PARAMETER_ID) String id);
}
