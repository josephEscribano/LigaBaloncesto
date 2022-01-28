package quevedo.ClienteLiga.dao.retrofit;

import io.reactivex.rxjava3.core.Single;
import quevedo.ClienteLiga.dao.utils.ConstantesPath;
import quevedo.common.modelos.Jornada;
import retrofit2.http.*;

import java.util.List;

public interface RetrofitJornadas {

    @GET(ConstantesPath.PATH_API_JORNADAS)
    Single<List<Jornada>> getJornadas();

    @POST(ConstantesPath.PATH_API_JORNADAS)
    Single<Jornada> insertJornada(@Body Jornada jornada);

    @PUT(ConstantesPath.PATH_API_JORNADAS)
    Single<Jornada> updateJornada(@Body Jornada jornada);

    @DELETE(ConstantesPath.PATH_DELETE_JORNADAS)
    Single<String> deleteJornada(@Path(ConstantesPath.PATH_PARAMETER_ID) String id);


}
