package quevedo.ClienteLiga.dao;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import quevedo.ClienteLiga.dao.retrofit.RetrofitPartidos;
import quevedo.common.modelos.Partido;

import javax.inject.Inject;
import java.util.List;

public class DAOPartidos extends DAOGenerics {
    private final RetrofitPartidos retrofitPartidos;

    @Inject
    public DAOPartidos(RetrofitPartidos retrofitPartidos) {
        this.retrofitPartidos = retrofitPartidos;
    }

    public Single<Either<String, List<Partido>>> getAll() {
        return safeSingleApicall(retrofitPartidos.getPartidos()).subscribeOn(Schedulers.io());
    }

    public Single<Either<String, List<Partido>>> filtroEquipos(String equipo) {
        return safeSingleApicall(retrofitPartidos.filtroEquipos(equipo)).subscribeOn(Schedulers.io());
    }

    public Single<Either<String, List<Partido>>> filtrosJornadas(String jornada) {
        return safeSingleApicall(retrofitPartidos.filtroJornadas(jornada)).subscribeOn(Schedulers.io());
    }

    public Single<Either<String, Partido>> savePartido(Partido partido) {
        return safeSingleApicall(retrofitPartidos.savePartido(partido)).subscribeOn(Schedulers.io());
    }

    public Single<Either<String, Partido>> updatePartido(Partido partido) {
        return safeSingleApicall(retrofitPartidos.updatePartido(partido)).subscribeOn(Schedulers.io());
    }

    public Single<Either<String, String>> deletePartido(String id) {
        return safeSingleApicall(retrofitPartidos.deletePartido(id)).subscribeOn(Schedulers.io());
    }

}
