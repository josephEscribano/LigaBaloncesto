package quevedo.ClienteLiga.gui.controllers;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;
import quevedo.ClienteLiga.dao.network.CacheDataUser;
import quevedo.ClienteLiga.service.ServiceUsuarios;
import quevedo.common.modelos.ApiRespuesta;
import quevedo.common.modelos.UsuarioDTO;

import javax.inject.Inject;

public class FXMLLoginController {
    private final Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private final ServiceUsuarios serviceUsuarios;
    private final CacheDataUser cache;
    @FXML
    private TextField tfUser;
    @FXML
    private TextField tfPass;
    @FXML
    private Label errorBox;
    private FXMLPrincipalController principal;

    @Inject
    public FXMLLoginController(ServiceUsuarios serviceUsuarios, CacheDataUser cache) {
        this.serviceUsuarios = serviceUsuarios;
        this.cache = cache;
    }

    public void setPrincipal(FXMLPrincipalController fxmlPrincipalController) {
        this.principal = fxmlPrincipalController;
    }

    public void doLogin() {
        cache.setUserName(tfUser.getText());
        cache.setPass(tfPass.getText());
        Single<Either<String, UsuarioDTO>> single = serviceUsuarios.doLogin(tfUser.getText())
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> principal.getRoot().setCursor(Cursor.DEFAULT));

        single.subscribe(result -> result
                        .peek(usuarioDTO -> {
                            principal.setUsuarioDTO(usuarioDTO);
                            principal.chargeWelcome();
                        })
                        .peekLeft(error -> errorBox.setText(error)),
                throwable -> {
                    alert.setContentText(throwable.getMessage());
                    alert.showAndWait();
                });

        principal.getRoot().setCursor(Cursor.WAIT);

    }

    public void reenviarCorreo() {
        Single<Either<String, ApiRespuesta>> single = serviceUsuarios.reenviarCorreo(tfUser.getText())
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> principal.getRoot().setCursor(Cursor.DEFAULT));

        single.subscribe(result -> result
                        .peek(apiRespuesta -> {
                            alert.setContentText(apiRespuesta.getMessage());
                            alert.showAndWait();
                        })
                        .peekLeft(error -> errorBox.setText(error)),
                throwable -> {
                    alert.setContentText(throwable.getMessage());
                    alert.showAndWait();
                });

        principal.getRoot().setCursor(Cursor.WAIT);
    }


}
