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
import quevedo.common.modelos.Jornada;
import quevedo.common.utils.ConstantesCommon;
import quevedo.servidorLiga.EE.utils.ConstantesRest;
import quevedo.servidorLiga.service.JornadaService;

import java.util.List;

@Path(ConstantesRest.PATH_JORNADAS)
@Produces({MediaType.APPLICATION_JSON})
@Consumes(MediaType.APPLICATION_JSON)
@Log4j2

public class RestJornadas {

    private final JornadaService jornadaService;

    @Inject
    public RestJornadas(JornadaService jornadaService) {
        this.jornadaService = jornadaService;
    }

    @GET
    @RolesAllowed({ConstantesCommon.NORMAL, ConstantesCommon.ADMIN})
    public Response getAll() {
        Response response;
        Either<ApiError, List<Jornada>> resultado = jornadaService.getAll();

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
    public Response saveJornada(Jornada jornada) {
        Response response;
        Either<ApiError, Jornada> resultado = jornadaService.saveJornada(jornada);
        if (resultado.isRight()) {
            response = Response.status(Response.Status.CREATED)
                    .entity(resultado.get())
                    .build();
        } else {
            response = Response.status(Response.Status.NOT_MODIFIED)
                    .entity(resultado.getLeft())
                    .build();
        }

        return response;
    }

    @PUT
    @RolesAllowed(ConstantesCommon.ADMIN)
    public Response updateJornada(Jornada jornada) {
        Response response;
        Either<ApiError, Jornada> resultado = jornadaService.updateJornada(jornada);
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

    @DELETE
    @Path(ConstantesRest.PATH_ID)
    @RolesAllowed(ConstantesCommon.ADMIN)
    public Response deleteJornada(@PathParam(ConstantesRest.PARAM_ID) String id) {
        Response response;
        Either<ApiError, String> resultado = jornadaService.delteJornada(id);
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
