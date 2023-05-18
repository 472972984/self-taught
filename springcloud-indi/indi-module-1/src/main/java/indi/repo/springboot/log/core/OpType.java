package indi.repo.springboot.log.core;


import indi.repo.springboot.log.InternalOpModule;

public interface OpType {

    default OpModule module() {
        return InternalOpModule.OTHER;
    }

    String bizName();

}
