package com.guilbt.octoevents.controller

import com.guilbt.octoevents.model.IssueEvent
import com.guilbt.octoevents.model.reflection.IssueEventReflection
import com.guilbt.octoevents.repository.IssueEventRepository
import com.guilbt.octoevents.service.IssueService
import org.springframework.boot.json.JsonJsonParser
import org.springframework.web.bind.annotation.*
import org.json.JSONObject
import org.json.JSONException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity


@RestController
@RequestMapping("issues")
class IssueController(private val issueService : IssueService) {

	@GetMapping("/{id}/events")
	fun getEvents(@PathVariable id: Long) : List<IssueEventReflection> = issueService.getEvents(id)

	@PostMapping("/webhook")
	fun postGithubEvent(@RequestBody eventString: String) : Any {
		try {
			val event = JSONObject(eventString);
			if(!event.has("issue") || event.has("comment")) {
				return ResponseEntity.badRequest().body(
					"Event isn't a IssueEvent"
				);
			}
			return issueService.createIssueEventByJSONObject(event)
		} catch (e: JSONException) {
			return ResponseEntity.badRequest().body(
					String.format("Malformed Object, error: %s", e.localizedMessage)
			)
		}
	}
}
