package org.cqlin.propertysourcedemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:myconfig.properties")
@Configuration
public class AppConfig {

}
