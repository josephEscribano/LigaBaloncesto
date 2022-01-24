package quevedo.ClienteLiga.dao;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import quevedo.ClienteLiga.dao.retrofit.RetrofitJornadas;
import quevedo.common.modelos.Jornada;

import javax.inject.Inject;
import java.util.List;

public class DAOJornadas extends DAOGenerics {

    private final RetrofitJornadas retrofitJornadas;

    @Inject
    public DAOJornadas(RetrofitJornadas retrofitJornadas) {
        this.retrofitJornadas = retrofitJornadas;
    }

    public Single<Either<String, List<Jornada>>> getAll() {
        return safeSingleApicall(retrofitJornadas.getJornadas()).subscribeOn(Schedulers.io());
    }

    public Single<Either<String, Jornada>> insertJornada(Jornada jornada) {
        return safeSingleApicall(retrofitJornadas.insertJornada(jornada)).subscribeOn(Schedulers.io());
    }

    public Single<Either<String, Jornada>> updateJornada(Jornada jornada) {
        return safeSingleApicall(retrofitJornadas.updateJornada(jornada)).subscribeOn(Schedulers.io());
    }

    public Single<Either<String, String>> deleteJornada(String id) {
        return safeSingleApicall(retrofitJornadas.deleteJornada(id)).subscribeOn(Schedulers.io());
    }
}
