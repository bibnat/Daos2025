package daos.tp.centroasistencias.seguridad;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationTime;

    // Genera token a partir de un usuario autenticado
    public String generarToken(Authentication authentication) {

        String username = authentication.getName();
        Date fechaExpiracion = new Date(new Date().getTime() + jwtExpirationTime);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(fechaExpiracion)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    // Obtiene el username desde el token
    public String obtenerUserNameFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // Valida que el token sea correcto
    public boolean validarToken(String token) {
        Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
        return true;
    }
}
