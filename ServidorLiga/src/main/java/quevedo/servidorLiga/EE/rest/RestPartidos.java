package quevedo.servidorLiga.EE.rest;


import io.vavr.control.Either;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.log4j.Log4j2;
import quevedo.common.errores.ApiError;
import quevedo.common.modelos.Partido;
import quevedo.common.utils.ConstantesCommon;
import quevedo.servidorLiga.EE.utils.ConstantesRest;
import quevedo.servidorLiga.service.PartidosService;

import java.util.List;

@Path(ConstantesRest.PATH_PARTIDOS)
@Produces({MediaType.APPLICATION_JSON})
@Consumes(MediaType.APPLICATION_JSON)
@Log4j2
public class RestPartidos {
    private final PartidosService partidosService;

    @Inject
    public RestPartidos(PartidosService partidosService) {
        this.partidosService = partidosService;
    }


    @GET
    @RolesAllowed({ConstantesCommon.NORMAL, ConstantesCommon.ADMIN})
    public Response getAll() {
        Response response;
        Either<ApiError, List<Partido>> resultado = partidosService.getAll();

        if (resultado.isRight()) {
            response = Response.status(Response.Status.OK)
                    .entity(resultado.get())
                    .build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(resultado.getLeft())
                    .build();
        }

        return response;
    }

    @GET
    @Path(ConstantesRest.PATH_FILTRAR_EQUIPO)
    @RolesAllowed(ConstantesCommon.NORMAL)
    public Response filtrarEquipo(@QueryParam(ConstantesRest.PATH_PARAMETER_EQUIPO) String equipo) {
        Response response;
        Either<ApiError, List<Partido>> resultado = partidosService.filtrarEquipo(equipo);
        if (resultado.isRight()) {
            response = Response.status(Response.Status.OK)
                    .entity(resultado.get())
                    .build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(resultado.getLeft())
                    .build();
        }

        return response;
    }

    @GET
    @Path(ConstantesRest.PATH_FILTRAR_JORNADA)
    @RolesAllowed(ConstantesCommon.NORMAL)
    public Response filtrarJornada(@QueryParam(ConstantesRest.PATH_PARAMETER_JORNADA) String jornada) {
        Response response;
        Either<ApiError, List<Partido>> resultado = partidosService.filtrarJornada(jornada);
        if (resultado.isRight()) {
            response = Response.status(Response.Status.OK)
                    .entity(resultado.get())
                    .build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(resultado.getLeft())
                    .build();
        }

        return response;
    }

    @POST
    @RolesAllowed(ConstantesCommon.ADMIN)
    public Response savePartido(Partido partido) {
        Response response;
        Either<ApiError, Partido> resultado = partidosService.savePartido(partido);
        if (resultado.isRight()) {
            response = Response.status(Response.Status.OK)
                    .entity(resultado.get())
                    .build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(resultado.getLeft())
                    .build();
        }

        return response;
    }

    @PUT
    @RolesAllowed(ConstantesCommon.ADMIN)
    public Response updatePartido(Partido partido) {
        Response response;
        Either<ApiError, Partido> resultado = partidosService.updatePartido(partido);
        if (resultado.isRight()) {
            response = Response.status(Response.Status.CREATED)
                    .entity(resultado.get())
                    .build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(resultado.getLeft())
                    .build();
        }

        return response;
    }

    @DELETE
    @Path(ConstantesRest.PATH_ID)
    @RolesAllowed(ConstantesCommon.ADMIN)
    public Response deletePartido(@PathParam(ConstantesRest.PARAM_ID) String id) {
        Response response;
        Either<ApiError, String> resultado = partidosService.deletePartido(id);
        if (resultado.isRight()) {
            response = Response.status(Response.Status.OK)
                    .entity(resultado.get())
                    .build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(resultado.getLeft())
                    .build();
        }

        return response;
    }


}
