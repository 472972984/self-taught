package indi.repo.openapi.constant;

/**
 * @author ChenHQ
 * 开放api常量
 * @date 2021/12/27 10:30
 */
public class ApplicationConstant {


    /**
     * 请求唯一requestId
     */
    public static final String MDC_TRACE = "trace";

    /**
     * SIGN_METHOD
     */
    public static final String SIGN_METHOD_MD5 = "md5";

    /**
     * SIGN_METHOD
     */
    public static final String SIGN_METHOD_HMAC = "hmac";

    /**
     * 请求头标识
     */
    public static final String APP_KEY = "app-key";


    /**
     * 请求头标识
     */
    public static final String VERSION = "version";


    /**
     * 请求头标识
     */
    public static final String SIGN_METHOD = "sign-method";


    /**
     * 请求头标识
     */
    public static final String TIMESTAMP = "timestamp";


    /**
     * 请求头标识
     */
    public static final String SIGN = "sign";

    /**
     * 请求头标识
     */
    public static final String METHOD = "method";

    /**
     * 请求头标识
     */
    public static final String RANDOM_KEY = "random-key";

    /**
     * 请求头标识
     */
    public static final String ENCRYPT_PARM = "encrypt-parm";

    /**
     * 格式化时间
     */
    public static final String DATE_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";

    /**
     * jsonParam
     */
    public static final String JSON_PARAM = "jsonParam";

    /**
     * 上下文计算值
     */
    public static final String THREAD_LOCAL_SIGN_PARAM = "computeSignature";

    /**
     * 上下文 secret
     */
    public static final String THREAD_LOCAL_SECRET = "THREAD_LOCAL_SECRET";

}
