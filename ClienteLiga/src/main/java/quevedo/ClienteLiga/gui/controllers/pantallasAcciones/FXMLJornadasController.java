package quevedo.ClienteLiga.gui.controllers.pantallasAcciones;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;
import quevedo.ClienteLiga.gui.controllers.FXMLPrincipalController;
import quevedo.ClienteLiga.gui.utils.ConstantesGUI;
import quevedo.ClienteLiga.service.ServiceJornadas;
import quevedo.common.modelos.Jornada;

import javax.inject.Inject;
import java.util.List;

public class FXMLJornadasController {
    private final Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private final ServiceJornadas serviceJornadas;
    @FXML
    private DatePicker dpFecha;
    @FXML
    private ListView<Jornada> lvJornada;
    private FXMLPrincipalController principal;

    @Inject
    public FXMLJornadasController(ServiceJornadas serviceJornadas) {
        this.serviceJornadas = serviceJornadas;
    }

    public void setPrincipal(FXMLPrincipalController fxmlPrincipalController) {
        this.principal = fxmlPrincipalController;
    }

    public void loadJornadas() {

        Single<Either<String, List<Jornada>>> single = serviceJornadas.getAll()
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> principal.getRoot().setCursor(Cursor.DEFAULT));

        single.subscribe(result -> result
                        .peek(list -> lvJornada.getItems().setAll(list))
                        .peekLeft(error -> {
                            alert.setContentText(error);
                            alert.showAndWait();
                        }),
                throwable -> {
                    alert.setContentText(throwable.getMessage());
                    alert.showAndWait();
                });

        principal.getRoot().setCursor(Cursor.WAIT);
    }

    public void insertJornada(ActionEvent actionEvent) {


        Single<Either<String, Jornada>> single = serviceJornadas.insertJornada(new Jornada(dpFecha.getValue()))
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> principal.getRoot().setCursor(Cursor.DEFAULT));

        single.subscribe(result -> result
                        .peek(jornada -> lvJornada.getItems().add(jornada))
                        .peekLeft(error -> {
                            alert.setContentText(error);
                            alert.showAndWait();
                        }),
                throwable -> {
                    alert.setContentText(throwable.getMessage());
                    alert.showAndWait();
                });

        principal.getRoot().setCursor(Cursor.WAIT);
    }

    public void showData() {
        Jornada jornada = lvJornada.getSelectionModel().getSelectedItem();
        dpFecha.setValue(jornada.getFechaJornada());
    }

    public void updateJornada() {

        Jornada jornada = lvJornada.getSelectionModel().getSelectedItem();
        if (jornada != null) {
            jornada.setFechaJornada(dpFecha.getValue());
            int index = lvJornada.getItems().indexOf(jornada);
            Single<Either<String, Jornada>> single = serviceJornadas.updateJornada(jornada)
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> principal.getRoot().setCursor(Cursor.DEFAULT));

            single.subscribe(result -> result
                            .peek(jornadaUpdate -> lvJornada.getItems().set(index, jornadaUpdate))
                            .peekLeft(error -> {
                                alert.setContentText(error);
                                alert.showAndWait();
                            }),
                    throwable -> {
                        alert.setContentText(throwable.getMessage());
                        alert.showAndWait();
                    });

            principal.getRoot().setCursor(Cursor.WAIT);
        } else {
            alert.setContentText(ConstantesGUI.SELECCIONA_UN_EQUIPO);
            alert.showAndWait();
        }
    }

    public void deleteJornada() {

        Jornada jornada = lvJornada.getSelectionModel().getSelectedItem();
        if (jornada != null) {
            Single<Either<String, String>> single = serviceJornadas.deleteJornada(jornada.getIdJornada())
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> principal.getRoot().setCursor(Cursor.DEFAULT));

            single.subscribe(result -> result
                            .peek(id -> lvJornada.getItems().removeIf(jornada1 -> jornada1.getIdJornada().equals(id)))
                            .peekLeft(error -> {
                                alert.setContentText(error);
                                alert.showAndWait();
                            }),
                    throwable -> {
                        alert.setContentText(throwable.getMessage());
                        alert.showAndWait();
                    });

            principal.getRoot().setCursor(Cursor.WAIT);
        }
    }


}
