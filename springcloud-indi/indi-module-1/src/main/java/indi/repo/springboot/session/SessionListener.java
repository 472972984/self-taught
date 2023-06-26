package indi.repo.springboot.session;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * session监听器
 * @author ChenHQ
 * @date 2023/6/26 14:27
 */
@WebListener
public class SessionListener implements HttpSessionListener {

    private final MySessionContext msc = MySessionContext.getInstance();

    /**
     * session创建事件
     * @param se
     */
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("session创建");
        HttpSession session = se.getSession();
        msc.addSession(session);
    }

    /**
     * session销毁事件
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("session销毁");
        HttpSession session = se.getSession();
        msc.delSession(session);
    }
}