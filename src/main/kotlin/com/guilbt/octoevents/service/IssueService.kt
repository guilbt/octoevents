package com.guilbt.octoevents.service

import com.guilbt.octoevents.model.IssueEvent
import com.guilbt.octoevents.model.dto.IssueEventDTO
import com.guilbt.octoevents.model.reflection.IssueEventReflection
import com.guilbt.octoevents.repository.IssueEventRepository
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service
import javax.validation.ValidationException

@Service
@Scope("singleton")
class IssueService(private val issueEventRepository: IssueEventRepository) {
	fun getEvents(id: Long): List<IssueEventReflection> = issueEventRepository.getByIssueId(id)

	fun createIssueEventByDTO(event: IssueEventDTO): Long {
		val issue = event.issue;
		if (issue == null) throw ValidationException("Event doesn't have a issue object")
		if (issue.id == null) throw ValidationException("Event's issue object doesn't have a valid id")
		if (event.action == null) throw ValidationException("Event doesn't have a valid action")
		if (event.comment != null) throw ValidationException("Event is a Issue Comment Event, not a Issue Event")
		val issueEvent = IssueEvent(
				action = event.action,
				issueId = issue.id
		)
		return issueEventRepository.save(issueEvent).id;
	}
}
