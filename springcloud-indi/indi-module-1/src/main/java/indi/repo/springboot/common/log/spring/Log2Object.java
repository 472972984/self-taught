package indi.repo.springboot.common.log.spring;

import indi.repo.springboot.log.annotation.OpLog;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author admin
 */
@AllArgsConstructor
@Getter
public class Log2Object {

    private final OpLog entityLog;

    private final Object[] args;


}
