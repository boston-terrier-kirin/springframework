package com.example;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;

import java.text.ParseException;

/**
 * https://baubaubau.hatenablog.com/entry/2021/01/10/131925
 */
public class JWS_RS256 {
    public static void main(String[] args) throws JOSEException, ParseException {
        // RSAの鍵ペアをJWKの形式で生成
        // 鍵の長さは2048以上が推奨されている
        RSAKey rsaJWK = new RSAKeyGenerator(2048)
                .keyID("123")
                .generate();
        System.out.println("RSA private key (JWK) : " + rsaJWK.toJSONString());

        RSAKey rsaPublicJWK = rsaJWK.toPublicJWK();
        System.out.println("RSA public key (JWK) : " + rsaPublicJWK.toJSONString());

        // JWT を作成
        JWSObject jwsObject = new JWSObject(
                new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(rsaJWK.getKeyID()).build(),
                new Payload("In RSQ we trust!")
        );

        // RSAのプライベート鍵で署名
        JWSSigner signer = new RSASSASigner(rsaJWK);
        jwsObject.sign(signer);
        String s = jwsObject.serialize();

        System.out.println("Generated JWS signed RS256 : " + s);

        // RSAの公開鍵で検証
        JWSObject parsedJWSObject = JWSObject.parse(s);
        RSAKey parsedRsaPublicJWK = RSAKey.parse(rsaPublicJWK.toJSONString());
        JWSVerifier verifier = new RSASSAVerifier(parsedRsaPublicJWK);

        System.out.println(parsedJWSObject.verify(verifier));
        System.out.println(parsedJWSObject.getPayload().toString());
    }
}