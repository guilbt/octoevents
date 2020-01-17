package com.guilbt.octoevents.model

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "ISSUE_EVENT", schema = "octoevents")
class IssueEvent(
		@Column(name = "ISSUE_EVENT_ID") @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
		var id: Long = 0,
		@Column(name = "ISSUE_ID")
		var issueId: Long = 0,
		@Column(name = "ACTION")
		var action: String = "",
		@Column(name = "CREATED_AT") @Temporal(TemporalType.TIMESTAMP)
		var createdAt: Calendar = Calendar.getInstance()
)
