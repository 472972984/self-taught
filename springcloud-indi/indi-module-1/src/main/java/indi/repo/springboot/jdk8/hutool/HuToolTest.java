package indi.repo.springboot.jdk8.hutool;


import cn.hutool.core.builder.CompareToBuilder;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.extra.expression.engine.spel.SpELEngine;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.HMacJWTSigner;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.poi.word.Word07Writer;
import cn.hutool.poi.word.WordUtil;
import indi.repo.springboot.common.log.util.OperatorLogUtils;
import indi.repo.springboot.entity.Student;
import indi.repo.springboot.module.dto.StudentDTO;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ChenHQ
 * @date 2022/7/20 14:13
 */
public class HuToolTest {

    public static void main(String[] args) throws Exception {

        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        List<Integer> list1 = Arrays.asList(4, 4, 4, 4, 5, 6, 7);

        System.out.println(CollectionUtil.union(list, list1));
        System.out.println(CollectionUtil.unionDistinct(list, list1));
        System.out.println(CollectionUtil.unionAll(list, list1));
        System.out.println(list.stream().map(String::valueOf).collect(Collectors.joining(",","[","]")));

    }

    private static void reflect() {
        StudentDTO studentDTO1 = StudentDTO.builder().id(1L).sex("1").username("chq").build();
        StudentDTO studentDTO2 = StudentDTO.builder().id(2L).sex("2").username("test1").build();

        System.out.println(CompareToBuilder.reflectionCompare(studentDTO1, studentDTO2));

        System.out.println(OperatorLogUtils.compareObject(studentDTO1, studentDTO2));
    }

    private static void rsaSecret() throws IOException {
        KeyPair rsa = SecureUtil.generateKeyPair("RSA");

        PublicKey aPublic = rsa.getPublic();
        PrivateKey aPrivate = rsa.getPrivate();
        RSA r = new RSA(aPrivate, aPublic);
        byte[] encrypt = r.encrypt("hello world", KeyType.PublicKey);
        System.out.print("加密后的字节码：");
        System.out.println("" + new String(encrypt, StandardCharsets.UTF_8));

        byte[] decrypt = r.decrypt(encrypt, KeyType.PrivateKey);
        System.out.println("使用字节码解密：" + new String(decrypt, StandardCharsets.UTF_8));


        String encryStr = new BASE64Encoder().encodeBuffer(encrypt);
        System.out.println("对加密后的字节码转字符串处理：" + encryStr);

        byte[] decryStr = new BASE64Decoder().decodeBuffer(encryStr);
        byte[] bytes = r.decrypt(decryStr, KeyType.PrivateKey);
        System.out.println("字符串转字节解密后的结果：" + new String(bytes, StandardCharsets.UTF_8));


        //字符串形式 RSA
        String privateKey = new BASE64Encoder().encodeBuffer(aPrivate.getEncoded());
        String publicKey = new BASE64Encoder().encodeBuffer(aPublic.getEncoded());

//        System.out.println(publicKey);
//        System.out.println(privateKey);
    }

    private static void spelExpression() {
        Student student = new Student();
        student.setId(1L);
        student.setUsername("chq");

        SpELEngine engine = new SpELEngine();

        Map<String, Object> map = new HashMap<>(16);
        map.put("student", student);

        Object eval = engine.eval("#student.id", map);
        System.out.println("eval = " + eval);
    }

    private static void generatorJwtToken() {
        JWTSigner jwtSigner = new HMacJWTSigner("HmacSHA256", "123456".getBytes(StandardCharsets.UTF_8));

        Map<String, Object> payLoad = new HashMap<>();
        payLoad.put("userId", "123");
        payLoad.put("username", "chenhuiqi");
        payLoad.put("userType", "1");
        payLoad.put("1", "2");
        payLoad.put("3", "4");
        payLoad.put("expireTime", DateUtil.now());

        String token = JWTUtil.createToken(payLoad, jwtSigner);
        System.out.println(token);
        System.out.println(JWTUtil.verify(token, jwtSigner));

        JWT jwt = JWTUtil.parseToken(token);
        System.out.println();
    }

    private static void writeDoc() {
        Word07Writer writer = WordUtil.getWriter(new File("/Users/admin/Downloads/a.doc"));
        writer.addText(new Font(null), "测试word写入");
        writer.close();
    }


}
