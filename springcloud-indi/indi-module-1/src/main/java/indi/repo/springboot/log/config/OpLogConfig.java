package indi.repo.springboot.log.config;

import indi.repo.common.exception.BaseException;
import indi.repo.springboot.common.log.spring.LogSpringExpression;
import indi.repo.springboot.log.model.OpLogRecord;
import indi.repo.springboot.log.process.CurrentUserResolver;
import indi.repo.springboot.log.process.IUser;
import indi.repo.springboot.log.process.OpLogJoinPointProcessor;
import indi.repo.springboot.log.process.OpLogStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class OpLogConfig {

    @Bean
    public LogSpringExpression logSpringExpression() {
        return new LogSpringExpression();
    }

    @Bean
    public OpLogStorage opLogStorage() {
        return new OpLogStorage() {
            @Override
            public void put(OpLogRecord opLogRecord) {
                // 保存日志信息:TODO: 入库
                System.out.println(opLogRecord);
            }

            @Override
            public OpLogRecord take() {
                throw new BaseException(500, "不支持的操作");
            }

            ;
        };
    }

    @Bean
    public CurrentUserResolver currentUserResolver() {
        return new CurrentUserResolver() {
            @Override
            public IUser getCurrentUser() {
//                return PrincipalUtils.isLogin() ? PrincipalUtils.getPrincipal().getUser() : null;
                return null;
            }

            @Override
            public String getRequestIp() {
//                HttpServletRequest request = ServletUtils.getRequest();
//                return IPUtils.getIpAddr(request);
                return "127.0.0.1";
            }
        };
    }

    @Bean
    public OpLogJoinPointProcessor opLogJoinPointProcessor(OpLogStorage opLogStorage, CurrentUserResolver currentUserResolver, LogSpringExpression logSpringExpression) {
        return new OpLogJoinPointProcessor(currentUserResolver, opLogStorage, logSpringExpression);
    }

}
