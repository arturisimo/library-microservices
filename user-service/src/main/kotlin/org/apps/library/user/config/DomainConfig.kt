package org.apps.library.user.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement


@Configuration
@EntityScan("org.apps.library.user")
@EnableJpaRepositories("org.apps.library.user")
@EnableTransactionManagement
class DomainConfig
