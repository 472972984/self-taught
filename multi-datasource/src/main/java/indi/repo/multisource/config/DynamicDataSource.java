package indi.repo.multisource.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 功能说明:使用哪个数据源
 *
 * @author: ChenHQ
 * @date: 2021/6/7
 * @desc:
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        String datasource = DbContextHolder.getDbType();
        log.debug("当前使用数据源:{}", datasource);
        return datasource;
    }

}
