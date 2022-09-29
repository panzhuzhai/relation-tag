package com.relation.tag.ssh.listener;

import com.relation.tag.ssh.config.SSHDataBaseConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@Slf4j
@WebListener
@Component
public class RelationSshContextListener implements ServletContextListener {
    @Autowired
    private SSHDataBaseConfig dataBaseConfig;

    /**
     * 监听Servlet初始化事件
     */
    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        // 建立连接
        try {
            dataBaseConfig.createSSH();
        } catch (Throwable e) {
            e.printStackTrace(); // error connecting SSH server
        }
    }

    /**
     * 监听Servlet终止事件
     */
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        // 断开连接
        try {
            dataBaseConfig.closeSSH(); // disconnect
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
