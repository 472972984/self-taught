package indi.repo.springboot.test.engine;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.time.Duration;

/**
 * @author ChenHQ
 * @date 2025/4/8 15:20
 */
@Slf4j
public class ScriptEnginePool {

    private static final GenericObjectPool<CustomScriptEngine> pool;

    static {
        GenericObjectPoolConfig<CustomScriptEngine> config = new GenericObjectPoolConfig<>();
        config.setMaxTotal(16);
        config.setMinIdle(2);
        config.setMaxIdle(8);
        config.setTestOnBorrow(true);
        config.setMaxWait(Duration.ofSeconds(30));

        // 创建对象池
        pool = new GenericObjectPool<>(new ScriptEngineFactory(), config);
    }

    public static CustomScriptEngine borrowObject() throws Exception {
        CustomScriptEngine borrowed = pool.borrowObject();
        borrowed.setState(false);
        return borrowed;
    }

    public static void returnObject(CustomScriptEngine engine) {
        engine.setState(true);
        Bindings bindings = engine.createBindings();
        engine.setBindings(bindings, ScriptContext.GLOBAL_SCOPE);
        pool.returnObject(engine);
    }

    private static class ScriptEngineFactory extends BasePooledObjectFactory<CustomScriptEngine> {
        @Override
        public CustomScriptEngine create() {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine groovy = manager.getEngineByName("groovy");
            return new CustomScriptEngine(groovy);
        }

        @Override
        public PooledObject<CustomScriptEngine> wrap(CustomScriptEngine engine) {
            return new DefaultPooledObject<>(engine);
        }

        @Override
        public boolean validateObject(PooledObject<CustomScriptEngine> p) {
            // 验证
            CustomScriptEngine object = p.getObject();
            return object.isState();
        }

        @Override
        public void destroyObject(PooledObject<CustomScriptEngine> p) {
            CustomScriptEngine engine = p.getObject();
            engine = null;
            log.info(">>>>destroyObject ScriptEngine<<<<");
        }
    }
}