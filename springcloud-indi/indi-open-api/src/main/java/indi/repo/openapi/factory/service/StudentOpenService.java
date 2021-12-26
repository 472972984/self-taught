package indi.repo.openapi.factory.service;

import indi.repo.openapi.annotation.OpenApiEvent;
import indi.repo.openapi.base.HdeRequest;
import indi.repo.openapi.common.Result;
import indi.repo.student.StudentRpc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static indi.repo.openapi.constant.MethodMappingConstant.QUERY_STUDENT;

/**
 * @author ChenHQ
 * @date: create in 2021/12/26
 */
@Slf4j
@Service
@OpenApiEvent({QUERY_STUDENT})
public class StudentOpenService extends AbstractOpenService {

    @Autowired
    private StudentRpc studentRpc;

    @Override
    public <T extends Result> T execute(HdeRequest request) {
        return execute(request,studentRpc);
    }
}
