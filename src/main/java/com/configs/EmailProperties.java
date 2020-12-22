package com.configs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Getter
@Setter
@Component
@ConfigurationProperties("mail.smtp")
public class EmailProperties {
    private String host;
    private String socketFactoryport;
    private String socketFactoryclass;
    private String auth;
    private String port;
}
