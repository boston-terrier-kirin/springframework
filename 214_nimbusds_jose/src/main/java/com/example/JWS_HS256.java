package com.example;

/**
 * https://baubaubau.hatenablog.com/entry/2021/01/10/131925
 */
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;

import java.security.SecureRandom;
import java.text.ParseException;

public class JWS_HS256 {
    public static void main(String[] args) throws JOSEException, ParseException {
        // JWTを作成
        // header と payload の値をセットする
        JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload("hello, world!"));

        // 共有鍵の作成
        // 32byte = 256bit
        byte[] sharedKey = new byte[32];
        new SecureRandom().nextBytes(sharedKey);

        // 共有鍵で署名
        jwsObject.sign(new MACSigner(sharedKey));

        // JWSを出力
        System.out.println(jwsObject.serialize());

        // JWSを共有鍵を用いて検証
        String s = jwsObject.serialize();
        JWSObject parsedJWSObject = JWSObject.parse(s);
        MACVerifier verifier = new MACVerifier(sharedKey);
        System.out.println(parsedJWSObject.verify(verifier));

        System.out.println(parsedJWSObject.getHeader().toString());
        System.out.println(parsedJWSObject.getPayload().toString());
        System.out.println(parsedJWSObject.getSignature());
    }
}