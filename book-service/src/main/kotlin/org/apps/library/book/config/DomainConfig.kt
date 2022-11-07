package org.apps.library.book.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement


@Configuration
@EntityScan("org.apps.library.book")
@EnableJpaRepositories("org.apps.library.book")
@EnableTransactionManagement
class DomainConfig
