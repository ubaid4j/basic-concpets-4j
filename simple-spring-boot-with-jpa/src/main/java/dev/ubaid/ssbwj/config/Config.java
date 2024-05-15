package dev.ubaid.ssbwj.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "dev.ubaid.ssbwj.repository", enableDefaultTransactions = false)
@EnableTransactionManagement
public class Config {
}
