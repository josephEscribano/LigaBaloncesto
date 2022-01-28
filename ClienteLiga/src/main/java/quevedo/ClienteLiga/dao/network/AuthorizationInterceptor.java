package quevedo.ClienteLiga.dao.network;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import quevedo.ClienteLiga.dao.utils.ConstantesDAO;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Objects;

public class AuthorizationInterceptor implements Interceptor {
    private final CacheDataUser cache;

    @Inject
    public AuthorizationInterceptor(CacheDataUser cache) {
        this.cache = cache;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request request;
        if (cache.getToken() == null) {
            request = original.newBuilder()
                    .header(ConstantesDAO.AUTHORIZATION, Credentials.basic(cache.getUserName(), cache.getPass())).build();
        } else {
            request = original.newBuilder().header(ConstantesDAO.AUTHORIZATION, ConstantesDAO.BEARER + " " + cache.getToken()).build();
        }

        Response response = chain.proceed(request);
        if (response.header(ConstantesDAO.AUTHORIZATION) != null) {
            cache.setToken(response.header(ConstantesDAO.AUTHORIZATION));
        }
        if (!response.isSuccessful() && Objects.equals(response.header(ConstantesDAO.EXPIRES), ConstantesDAO.TOKEN_EXPIRADO)) {
            response.close();
            request = original.newBuilder().header(ConstantesDAO.AUTHORIZATION, Credentials.basic(cache.getUserName(), cache.getPass())).build();
            response = chain.proceed(request);
            if (response.header(ConstantesDAO.AUTHORIZATION) != null) {
                cache.setToken(response.header(ConstantesDAO.AUTHORIZATION));
            }
        }
        return response;
    }
}
