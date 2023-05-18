package indi.repo.springboot.log.process;


public interface CurrentUserResolver {

    IUser getCurrentUser();

    String getRequestIp();

    default String getTenantId() {
        return "";
    }

}
