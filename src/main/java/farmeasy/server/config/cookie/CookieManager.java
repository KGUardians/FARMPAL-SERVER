package farmeasy.server.config.cookie;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class CookieManager {
    public void putRefreshTokenInCookie(HttpServletResponse response, String refreshToken){
        addJwtInCookie(response, refreshToken);
    }

    public void addJwtInCookie(HttpServletResponse response, String token){
        Cookie cookie = new Cookie("refreshToken", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);
    }
}
