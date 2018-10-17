package com.training.test;

import com.alibaba.fastjson.JSONObject;
import com.training.common.Const;
import com.training.domain.Member;
import com.training.entity.StaffEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

public class JwtTest {

    private static String profiles = "prod";

    /**
     * 由字符串生成加密key
     * @return
     */
    public static SecretKey generalKey(){
        String stringKey = profiles+ Const.JWT_SECRET;
        byte[] encodedKey = Base64.decodeBase64(stringKey);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 创建jwt
     * @param id
     * @param subject
     * @param ttlMillis
     * @return
     * @throws Exception
     */
    public String createJWT(String id, String subject, long ttlMillis) throws Exception {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey key = generalKey();
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .signWith(signatureAlgorithm, key);
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + Const.JWT_TTL;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    /**
     * 解密jwt
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception{
        SecretKey key = generalKey();
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt).getBody();
        return claims;
    }

    /**
     * 生成subject信息
     * @param member
     * @return
     */
    public static String generalSubject(String openId,Member member){
        JSONObject jo = new JSONObject();
        jo.put("openId", openId);
        jo.put("memberId", member.getMemberId());
        jo.put("type",member.getType());
        return jo.toJSONString();
    }

    /**
     * 生成subject信息
     * @param staffEntity
     * @return
     */
    public static String generalAdminSubject(StaffEntity staffEntity){
        JSONObject jo = new JSONObject();
        jo.put("staffId", staffEntity.getStaffId());
        jo.put("username", staffEntity.getUsername());
        jo.put("roleId",staffEntity.getRoleId());
        return jo.toJSONString();
    }

    public static void main(String[] args) {
        String code = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJqd3QiLCJpYXQiOjE1Mzk2NDMxMTYsInN1YiI6IntcInJvbGVJZFwiOlwiMTUzMjc0MzYzNzk1NDIwNjhiNGUyZDgwNzQ1NzNiOTM0N2RjNzY3MTc5ODk3XCIsXCJzdGFmZklkXCI6XCIxNTMyNzA4NTM0NjM4MzRhMzEyMTYzMTM3NGRmZmFhYTE1ZmI2YTM0OWU5MWFcIixcInVzZXJuYW1lXCI6XCIxODMxMTQ3NTk5MFwifSJ9.pgWYpw2IleBtPUW_jS3tPIpwGNR77D1XEaIlk6QNPlo";

        code = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJqd3QiLCJpYXQiOjE1Mzk2NTQyODMsInN1YiI6IntcIm9wZW5JZFwiOlwib2RFUm81QTU2Qi1xcGNJd2JjSngzUEhTMXBHb1wiLFwidHlwZVwiOlwiQ1wiLFwibWVtYmVySWRcIjpcIjE1MzgxMDA0MDAxMThiNWFiYzAwNDBiMjY0NWU5OTAzOWNlNzkxNjU3NDRkY1wifSJ9.TfPPuZ7U8I_hNZUHW-0QQADItVa04CIqj40HRIl2CxI";
        Claims claims = null;
        try {
            claims = parseJWT(code);
            String json = claims.getSubject();
            System.out.println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}