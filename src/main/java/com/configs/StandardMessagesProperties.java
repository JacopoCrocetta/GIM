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
@ConfigurationProperties("msg.accountlocked")
public class StandardMessagesProperties {
    private String sub;
    private String msgStandard;
    private String msgDefinitive;
}
