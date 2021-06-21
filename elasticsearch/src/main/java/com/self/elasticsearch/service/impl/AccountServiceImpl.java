package com.self.elasticsearch.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.self.elasticsearch.dao.AccountDao;
import com.self.elasticsearch.model.Account;
import com.self.elasticsearch.service.AccountService;
import org.springframework.stereotype.Service;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date: 2021/6/21
 * @desc:
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountDao, Account> implements AccountService {
}
