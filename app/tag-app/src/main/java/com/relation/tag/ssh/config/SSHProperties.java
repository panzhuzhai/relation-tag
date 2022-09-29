package com.relation.tag.ssh.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.datasource.ssh.forward")
@Data
public class SSHProperties {
    private Boolean enabled;
    private String sshKeyPath;
    private String sshKnownHosts;
    private String host;
    private Integer port = -1;
    private String username;
    private String password;
    private String fromHost;
    private Integer fromPort = -1;
    private String toHost;
    private Integer toPort = -1;
}
