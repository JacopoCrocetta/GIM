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
@ConfigurationProperties("spring.datasource")
public class DatasourceProperties {
    private String url;
    private String username;
    private String password;
}
