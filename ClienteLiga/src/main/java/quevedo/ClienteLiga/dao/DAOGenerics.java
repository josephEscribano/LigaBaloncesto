package quevedo.ClienteLiga.dao;

import com.google.gson.Gson;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import okhttp3.MediaType;
import quevedo.ClienteLiga.dao.utils.ConstantesDAO;
import quevedo.common.errores.ApiError;
import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.Response;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Objects;

@Log4j2
abstract class DAOGenerics {
    @Inject
    private Gson gson;

    public <T> Either<String, T> safeApiCall(Call<T> call) {
        Either<String, T> resultado;

        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                if (Objects.equals(response.errorBody().contentType(), MediaType.get(ConstantesDAO.APPLICATION_JSON)) || Objects.equals(response.errorBody().contentType(), MediaType.get(ConstantesDAO.APPLICATION_JSON_CHARSET_ISO_8859_1))) {
                    ApiError apiError = gson.fromJson(response.errorBody().string(), ApiError.class);
                    resultado = Either.left(apiError.getMessage());
                } else {
                    resultado = Either.left(new ApiError(ConstantesDAO.ERROR_AL_CONECTARSE).getMessage());
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(new ApiError(ConstantesDAO.ERROR_SERVIDOR).getMessage());
        }

        return resultado;
    }

    public <T> Single<Either<String, T>> safeSingleApicall(Single<T> call) {
        return call.map(t -> Either.right(t).mapLeft(Object::toString))
                .subscribeOn(Schedulers.io())
                .onErrorReturn(throwable -> {
                    Either<String, T> error = Either.left(ConstantesDAO.ERROR_AL_CONECTARSE);
                    if (throwable instanceof HttpException) {
                        if (Objects.equals(((HttpException) throwable).response().errorBody().contentType(), MediaType.get(ConstantesDAO.APPLICATION_JSON)) || Objects.equals(((HttpException) throwable).response().errorBody().contentType(), MediaType.get(ConstantesDAO.APPLICATION_JSON_CHARSET_ISO_8859_1))) {
                            ApiError apiError = gson.fromJson(((HttpException) throwable).response().errorBody().string(), ApiError.class);
                            error = Either.left(apiError.getMessage());
                        } else {
                            error = Either.left(new ApiError(ConstantesDAO.ERROR_AL_CONECTARSE).getMessage());
                        }
                    }
                    return error;
                });


    }
}
