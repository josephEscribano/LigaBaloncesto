package quevedo.servidorLiga.EE;

import jakarta.annotation.security.DeclareRoles;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import quevedo.common.utils.ConstantesCommon;
import quevedo.servidorLiga.EE.utils.ConstantesRest;

@ApplicationPath(ConstantesRest.PATH_API)
@DeclareRoles({ConstantesCommon.NORMAL,ConstantesCommon.ADMIN})
public class JAXRSAplication extends Application {
}
