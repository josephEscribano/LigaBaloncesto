package quevedo.ClienteLiga.dao;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import quevedo.ClienteLiga.dao.retrofit.RetrofitEquipos;
import quevedo.common.modelos.Equipo;

import javax.inject.Inject;
import java.util.List;

public class DAOEquipos extends DAOGenerics {
    private final RetrofitEquipos retrofitEquipos;

    @Inject
    public DAOEquipos(RetrofitEquipos retrofitEquipos) {
        this.retrofitEquipos = retrofitEquipos;
    }


    public Single<Either<String, List<Equipo>>> getAll() {
        return safeSingleApicall(retrofitEquipos.getEquipos()).subscribeOn(Schedulers.io());
    }

    public Single<Either<String, Equipo>> insertEquipo(String nombre) {
        return safeSingleApicall(retrofitEquipos.insertEquipo(nombre)).subscribeOn(Schedulers.io());
    }

    public Single<Either<String, Equipo>> updateEquipo(Equipo equipo) {
        return safeSingleApicall(retrofitEquipos.updateEquipo(equipo)).subscribeOn(Schedulers.io());
    }

    public Single<Either<String, String>> deleteEquipo(String id) {
        return safeSingleApicall(retrofitEquipos.deleteEquipo(id)).subscribeOn(Schedulers.io());
    }

}
