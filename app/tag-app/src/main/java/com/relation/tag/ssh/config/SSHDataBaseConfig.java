package com.relation.tag.ssh.config;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties(SSHProperties.class)
@Slf4j
public class SSHDataBaseConfig {
    @Autowired
    private SSHProperties sshConfig;

    //represents each ssh session
    private Session session;

    public void createSSH() {
        if (sshConfig == null || sshConfig.getEnabled() == null || !sshConfig.getEnabled()) {
            return;
        }
        try {

            log.info("ssh forward is opend.");
            log.info("ssh init ……");
            JSch sch = new JSch();
            if (null != sshConfig.getSshKnownHosts() && !"".equals(sshConfig.getSshKnownHosts())) {
                sch.setKnownHosts(sshConfig.getSshKnownHosts());
            }
            if (null != sshConfig.getSshKeyPath() && !"".equals(sshConfig.getSshKeyPath())) {
                sch.addIdentity(sshConfig.getSshKeyPath(),sshConfig.getPassword());
            }

            session = sch.getSession(sshConfig.getUsername(), sshConfig.getHost(), sshConfig.getPort());
            session.setConfig("StrictHostKeyChecking", "no");
//            if (null != sshConfig.getPassword() && !"".equals(sshConfig.getPassword())) {
//                session.setPassword(sshConfig.getPassword());
//            }
            session.connect();
            session.setPortForwardingL(sshConfig.getFromHost(),sshConfig.getFromPort(), sshConfig.getToHost(), sshConfig.getToPort());

            log.info("ssh init sucessful……");
        } catch (JSchException e) {
            log.error("ssh JSchException failed.", e);
        } catch (Exception e) {
            log.error("ssh settings is failed. skip!", e);
        }

    }

    /**
     * 关闭SSH连接
     */
    public void closeSSH() {
        if (sshConfig == null || sshConfig.getEnabled() == null || !sshConfig.getEnabled()) {
            return;
        }
        session.disconnect();
    }
}
