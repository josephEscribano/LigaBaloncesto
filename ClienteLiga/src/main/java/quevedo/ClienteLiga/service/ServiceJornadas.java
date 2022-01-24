package quevedo.ClienteLiga.service;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import quevedo.ClienteLiga.dao.DAOJornadas;
import quevedo.common.modelos.Jornada;

import javax.inject.Inject;
import java.util.List;

public class ServiceJornadas {

    private final DAOJornadas daoJornadas;

    @Inject
    public ServiceJornadas(DAOJornadas daoJornadas) {
        this.daoJornadas = daoJornadas;
    }

    public Single<Either<String, List<Jornada>>> getAll() {
        return daoJornadas.getAll();
    }

    public Single<Either<String, Jornada>> insertJornada(Jornada jornada) {
        return daoJornadas.insertJornada(jornada);
    }

    public Single<Either<String, Jornada>> updateJornada(Jornada jornada) {
        return daoJornadas.updateJornada(jornada);
    }

    public Single<Either<String, String>> deleteJornada(String id) {
        return daoJornadas.deleteJornada(id);
    }
}
