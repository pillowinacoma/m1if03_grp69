package utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Classe qui centralise les opérations de validation et de génération d'un token "métier", c'est-à-dire dédié à cette application.
 *
 * @author Lionel Médini
 */
public class PresenceUcblJwtHelper {
    private static final String SECRET = "monbeausecret";
    private static final String ISSUER = "Présence UCBL";
    private static final long LIFETIME = 1800000; // Durée de vie d'un token : 30 minutes ; vous pouvez le modifier pour tester
    private static final Algorithm algorithm = Algorithm.HMAC256(SECRET);
    private static final JWTVerifier adminVerifier = JWT.require(algorithm).withClaim("admin", true).build(); // Reusable verifier instance

    /**
     * Vérifie l'authentification d'un utilisateur grâce à un token JWT
     *
     * @param token le token à vérifier
     * @param req   la requête HTTP (nécessaire pour vérifier si l'origine de la requête est la même que celle du token
     * @return un booléen qui indique si le token est bien formé et valide (pas expiré) et si l'utilisateur est authentifié
     */
    public static String verifyToken(String token, @NotNull HttpServletRequest req) throws NullPointerException, JWTVerificationException {
        JWTVerifier authenticationVerifier = JWT.require(algorithm)
                .withIssuer(ISSUER)
                .withAudience(getOrigin(req)) // Non-reusable verifier instance
                .build();

        authenticationVerifier.verify(token); // Lève une NullPointerException si le token n'existe pas, et une JWTVerificationException s'il est invalide
        DecodedJWT jwt = JWT.decode(token); // Pourrait lever une JWTDecodeException mais comme le token est vérifié avant, cela ne devrait pas arriver
        return jwt.getClaim("sub").asString();
    }

    /**
     * Vérifie dans le token si un user est administrateur
     *
     * @param token le token à vérifier
     * @return un booléen indiquant si le token contient un booléen admin à true
     */
    public static boolean verifyAdmin(String token) {
        try {
            adminVerifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    /**
     * Crée un token avec les caractéristiques de l'utilisateur
     *
     * @param subject le login de l'utilisateur
     * @param admin   si l'utilisateur est admin ou non
     * @param req     la requête HTTP pour pouvoir en extraire l'origine avec getOrigin()
     * @return le token signé
     * @throws JWTCreationException si les paramètres ne permettent pas de créer un token
     */
    public static String generateToken(String subject, boolean admin, HttpServletRequest req) throws JWTCreationException {
        return JWT.create()
                .withIssuer(ISSUER)
                .withSubject(subject)
                .withAudience(getOrigin(req))
                .withClaim("admin", admin)
                .withExpiresAt(new Date(new Date().getTime() + LIFETIME))
                .sign(algorithm);
    }

    /**
     * Renvoie l'URL d'origine du client, en fonction des headers de proxy (si existants) ou de l'URL de la requête sinon
     *
     * @param request la requête HTTP
     * @return une String qui sera passée aux éléments de l'application pour générer les URL absolues.
     */
    private static String getOrigin(@NotNull HttpServletRequest request) {
        String origin = String.valueOf(request.getRequestURL()).substring(0, request.getRequestURL().lastIndexOf(request.getRequestURI()));
        if (request.getHeader("X-Forwarded-Host") != null && request.getHeader("X-Forwarded-Proto") != null && request.getHeader("X-Forwarded-Path") != null) {
            switch (request.getHeader("X-Forwarded-Proto")) {
                case "http":
                    origin = request.getHeader("X-Forwarded-Proto") + "://" + (request.getHeader("X-Forwarded-Host").endsWith(":80") ? request.getHeader("X-Forwarded-Host").replace(":80", "") : request.getHeader("X-Forwarded-Host"));
                    break;
                case "https":
                    origin = request.getHeader("X-Forwarded-Proto") + "://" + (request.getHeader("X-Forwarded-Host").endsWith(":443") ? request.getHeader("X-Forwarded-Host").replace(":443", "") : request.getHeader("X-Forwarded-Host"));
            }
            origin = origin + request.getHeader("X-Forwarded-Path");
        }
        return origin + request.getContextPath();
    }

}