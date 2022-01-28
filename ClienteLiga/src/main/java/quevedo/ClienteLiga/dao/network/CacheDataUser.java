package quevedo.ClienteLiga.dao.network;

import lombok.Data;

import javax.inject.Singleton;

@Singleton
@Data
public class CacheDataUser {
    private String userName;
    private String pass;
    private String token;
}
