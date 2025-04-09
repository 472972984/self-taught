package indi.repo.springboot.test.engine;

/**
 * @author ChenHQ
 * @date 2025/4/8 16:00
 */
import lombok.Data;
import org.codehaus.groovy.jsr223.GroovyScriptEngineImpl;

import javax.script.ScriptEngine;

/**
 * @author ChenHQ
 * @date 2025/4/8 15:37
 */
@Data
public class CustomScriptEngine extends GroovyScriptEngineImpl {

    private ScriptEngine engine;

    private boolean state = true;

    public CustomScriptEngine(ScriptEngine engine) {
        this.engine = engine;
    }



}
