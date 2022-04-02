package indi.repo.openapi.verification;

/**
 * @author ChenHQ
 * app key 核验
 * @date 2021/12/16 17:17
 */
public interface CheckAppKeyPermit {

    /**
     * 校验 appKey 是否合法
     * @param appKey
     * @return
     */
    boolean checkPermit(String appKey);

}
