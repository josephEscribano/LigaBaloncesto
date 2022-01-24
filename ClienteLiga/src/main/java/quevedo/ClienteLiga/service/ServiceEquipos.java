package quevedo.ClienteLiga.service;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import quevedo.ClienteLiga.dao.DAOEquipos;
import quevedo.common.modelos.Equipo;

import javax.inject.Inject;
import java.util.List;

public class ServiceEquipos {
    private final DAOEquipos daoEquipos;

    @Inject
    public ServiceEquipos(DAOEquipos daoEquipos) {
        this.daoEquipos = daoEquipos;
    }

    public Single<Either<String, List<Equipo>>> getAll() {
        return daoEquipos.getAll();
    }

    public Single<Either<String, Equipo>> insertEquipo(String nombre) {
        return daoEquipos.insertEquipo(nombre);
    }

    public Single<Either<String, Equipo>> updateEquipo(Equipo equipo) {
        return daoEquipos.updateEquipo(equipo);
    }

    public Single<Either<String, String>> deleteEquipo(String id) {
        return daoEquipos.deleteEquipo(id);
    }
}
