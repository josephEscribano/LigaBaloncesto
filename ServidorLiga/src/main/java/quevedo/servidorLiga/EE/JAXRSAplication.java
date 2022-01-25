package quevedo.servidorLiga.EE;

import jakarta.annotation.security.DeclareRoles;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import quevedo.servidorLiga.EE.utils.ConstantesRest;

@ApplicationPath(ConstantesRest.PATH_API)
@DeclareRoles({"normal","admin"})
public class JAXRSAplication extends Application {
}
