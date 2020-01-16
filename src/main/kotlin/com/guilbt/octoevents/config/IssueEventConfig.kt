package com.guilbt.octoevents.config

import com.guilbt.octoevents.model.IssueEvent
import com.guilbt.octoevents.repository.IssueEventRepository
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableJpaRepositories(basePackageClasses = arrayOf(IssueEventRepository::class))
@EntityScan(basePackageClasses = arrayOf(IssueEvent::class))
@EnableTransactionManagement
internal class IssueEventConfig
