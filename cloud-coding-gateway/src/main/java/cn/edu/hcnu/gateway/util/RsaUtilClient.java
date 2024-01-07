package cn.edu.hcnu.gateway.util;

import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import java.net.URLDecoder;
import java.security.*;
import java.security.spec.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class RsaUtilClient {
    public static final String KEY_ALGORITHM = "RSA";
    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";
    private static volatile ConcurrentHashMap<String, Key> KEY_MAP = new ConcurrentHashMap<>(2);


    /**
     * base64解码
     *
     * @param content
     * @return byte[]
     * @author compass
     * @date 2022/9/1 17:12
     * @since 1.0.0
     **/
    public static byte[] decryptBASE64(String content) {
        return org.springframework.util.Base64Utils.decode(content.getBytes());
    }

    /**
     * base64编码
     *
     * @param bytes 字符byte数组
     * @return java.lang.String
     * @author compass
     * @date 2022/9/1 17:12
     * @since 1.0.0
     **/
    public static String encryptBASE64(byte[] bytes) {
        return new String(org.springframework.util.Base64Utils.encode(bytes));
    }


    /**
     * 通过私钥解密
     *
     * @param data       加密的byte数组
     * @param privateKey 私钥
     * @return byte[]
     * @author compass
     * @date 2022/9/1 17:14
     * @since 1.0.0
     **/
    public static byte[] decryptByPrivateKey(byte[] data, String privateKey)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(privateKey);
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privatizationKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privatizationKey);
        return cipher.doFinal(data);

    }

    /**
     * 私钥解密前端jsencrypt分段加密的长文本内容,
     * 前端加密的字符串需要使用encodeURIComponent编码,后端使用URLDecoder.decode解码来解决中文字符串无法解密的问题
     *
     * @param content    加密的内容
     * @param privateKey 私钥
     * @return java.lang.String
     * @author compass
     * @date 2022/9/1 16:53
     * @since 1.0.0
     **/
    public static String jsencryptDecryptByPrivateKeyLong(String content, String privateKey) {
        String resultContent = "";
        try {
            StringBuffer buffer = new StringBuffer();
            if (content != null && content.trim().length() > 0) {
                String[] contentList = content.split("=");
                for (String item : contentList) {
                    byte[] itemBytes = Base64Utils.decode((item + "=").getBytes());
                    try {
                        byte[] itemResultBytes = decryptByPrivateKey(itemBytes, privateKey);
                        String itemResultStr = new String(itemResultBytes);
                        buffer.append(itemResultStr);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
            resultContent = URLDecoder.decode(buffer.toString());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("jsencryptDecryptByPrivateKeyLong解密出错:" + e.getMessage() + ":" + "解密内容:" + content);
            throw new RuntimeException("rsa解密失败");
        }
        return resultContent;
    }


    /**
     * 通过私钥解密
     *
     * @param data       加密的byte数组
     * @param privateKey 私钥
     * @return byte[]
     * @author compass
     * @date 2022/9/1 17:14
     * @since 1.0.0
     **/
    public static byte[] decryptByPrivateKey(String data, String privateKey)
            throws Exception {
        return decryptByPrivateKey(decryptBASE64(data), privateKey);
    }

    /**
     * 解密<br>
     *
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    /**
     * 用公钥解密
     *
     * @param data      代解密的byte数组
     * @param publicKey
     * @return byte[]
     * @author compass
     * @date 2022/9/1 17:17
     * @since 1.0.0
     **/
    public static byte[] decryptByPublicKey(byte[] data, String publicKey)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(publicKey);
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicityKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicityKey);
        return cipher.doFinal(data);
    }

    /**
     * 加密<br>
     * 用公钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(String data, String key)
            throws Exception {
        // 对公钥解密
        byte[] keyBytes = decryptBASE64(key);
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data.getBytes());
    }

    /**
     * 加密<br>
     * 用私钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 获取私钥
     *
     * @param keyMap 存放私钥和公钥的map集合
     * @return java.lang.String
     * @author compass
     * @date 2022/9/1 17:18
     * @since 1.0.0
     **/
    public static String getPrivateKey(Map<String, Key> keyMap) {
        Key key = keyMap.get(PRIVATE_KEY);
        return encryptBASE64(key.getEncoded());
    }

    /**
     * 取得公钥
     *
     * @param keyMap 放私钥和公钥的map集合
     * @return java.lang.String
     * @author compass
     * @date 2022/9/1 17:28
     * @since 1.0.0
     **/
    public static String getPublicKey(Map<String, Key> keyMap) {
        Key key = keyMap.get(PUBLIC_KEY);
        return encryptBASE64(key.getEncoded());
    }

    /**
     * 初始化公钥和秘钥 每次调用可以获取不同的公钥和私钥
     *
     * @return java.util.Map<java.lang.String, java.security.Key>
     * @author compass
     * @date 2022/9/1 17:28
     * @since 1.0.0
     **/
    public static ConcurrentHashMap<String, Key> initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        ConcurrentHashMap<String, Key> keyMap = new ConcurrentHashMap<>(2);
        keyMap.put(PUBLIC_KEY, keyPair.getPublic());// 公钥
        keyMap.put(PRIVATE_KEY, keyPair.getPrivate());// 私钥
        return keyMap;
    }


    public static void main(String[] args) throws Exception {
        ConcurrentHashMap<String, Key> stringKeyConcurrentHashMap = initKey();


        String inputStr = "sign asdasdas  asdasd";
        byte[] bytes = encryptByPublicKey(inputStr, getPublicKey(stringKeyConcurrentHashMap));
        String s = new String(bytes);
        System.out.println("s = " + s);
        byte[] bytes1 = decryptByPrivateKey(s, getPrivateKey(stringKeyConcurrentHashMap));
        System.out.println(new String(bytes1));


    }
}
