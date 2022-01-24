package quevedo.ClienteLiga.service;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import quevedo.ClienteLiga.dao.DAOPartidos;
import quevedo.common.modelos.Partido;

import javax.inject.Inject;
import java.util.List;

public class ServicePartidos {
    private final DAOPartidos daoPartidos;

    @Inject
    public ServicePartidos(DAOPartidos daoPartidos) {
        this.daoPartidos = daoPartidos;
    }

    public Single<Either<String, List<Partido>>> getAll() {
        return daoPartidos.getAll();
    }

    public Single<Either<String, List<Partido>>> filtroEquipos(String equipo) {
        return daoPartidos.filtroEquipos(equipo);
    }

    public Single<Either<String, List<Partido>>> filtrosJornadas(String jornada) {
        return daoPartidos.filtrosJornadas(jornada);
    }

    public Single<Either<String, Partido>> savePartido(Partido partido) {
        return daoPartidos.savePartido(partido);
    }

    public Single<Either<String, Partido>> updatePartido(Partido partido) {
        return daoPartidos.updatePartido(partido);
    }

    public Single<Either<String, String>> deletePartido(String id) {
        return daoPartidos.deletePartido(id);
    }
}
