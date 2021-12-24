package quevedo.servidorLiga.dao.modelos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    private String idUsuario;
    private String userName;
    private String correo;
    private String pass;
    private String codActivacion;
    private boolean confirmacion;
    private LocalDateTime fechaLimite;
    private String idTipoUsuario;

    public Usuario(String userName, String correo, String pass, String codActivacion, boolean confirmacion, LocalDateTime fechaLimite, String idTipoUsuario) {
        this.userName = userName;
        this.correo = correo;
        this.pass = pass;
        this.codActivacion = codActivacion;
        this.confirmacion = confirmacion;
        this.fechaLimite = fechaLimite;
        this.idTipoUsuario = idTipoUsuario;
    }
}
