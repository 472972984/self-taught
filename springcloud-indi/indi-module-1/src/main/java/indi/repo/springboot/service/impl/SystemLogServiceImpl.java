package indi.repo.springboot.service.impl;

import indi.repo.springboot.entity.SystemLog;
import indi.repo.springboot.mapper.SystemLogMapper;
import indi.repo.springboot.service.ISystemLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ChenHQ
 * @since 2021-11-23
 */
@Service
public class SystemLogServiceImpl extends ServiceImpl<SystemLogMapper, SystemLog> implements ISystemLogService {

}
