package indi.repo.springboot.common.log.spring;

import indi.repo.springboot.common.log.annotation.UpdateEntityLog;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LogObject {

    private final UpdateEntityLog entityLog;

    private final Object[] args;


}
