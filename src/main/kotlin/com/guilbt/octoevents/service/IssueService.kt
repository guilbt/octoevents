package com.guilbt.octoevents.service

import com.guilbt.octoevents.model.IssueEvent
import com.guilbt.octoevents.model.reflection.IssueEventReflection
import com.guilbt.octoevents.repository.IssueEventRepository
import org.json.JSONException
import org.json.JSONObject
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service

@Service
@Scope("singleton")
class IssueService(private val issueEventRepository: IssueEventRepository) {
	fun getEvents(id: Long): List<IssueEventReflection> = issueEventRepository.getByIssueId(id)

	private val ID_ERROR = "Event's issue object doesn't have a valid id"

	fun createIssueEventByJSONObject(event: JSONObject): Long {
		val issue = JSONObject(event.get("issue").toString())
		if (!issue.has("id")) throw JSONException(ID_ERROR)
		var issueId = try {
			issue.get("id").toString().toLong()
		} catch (e: NumberFormatException) {
			throw JSONException(ID_ERROR)
		}
		val issueEvent = IssueEvent(
				action = event.get("action").toString(),
				issueId = issueId
		)
		return issueEventRepository.save(issueEvent).id;
	}
}
