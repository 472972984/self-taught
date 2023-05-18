package indi.repo.springboot.log.process;


import indi.repo.springboot.log.model.OpLogRecord;

public interface OpLogStorage {

    void put(OpLogRecord opLog);

    OpLogRecord take();

}
