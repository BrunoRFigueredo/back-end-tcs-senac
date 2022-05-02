package com.senac.projetosocial.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Service
@AllArgsConstructor
public class TokenKeys {
    public Algorithm getAlgorithm(String publicKey, String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return privateKey.isEmpty() ?
                Algorithm.RSA384(this.getPublicKey(publicKey), null) :
                Algorithm.RSA384(this.getPublicKey(publicKey), this.getPrivateKey(privateKey));
    }

    private RSAPublicKey getPublicKey(String pk) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Base64.Decoder b64 = Base64.getDecoder();
        byte[] decoded = b64.decode(pk);

        X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) kf.generatePublic(spec);
    }

    private RSAPrivateKey getPrivateKey(String pk) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Base64.Decoder b64 = Base64.getDecoder();
        byte[] decoded = b64.decode(pk);

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey) kf.generatePrivate(spec);
    }
}
