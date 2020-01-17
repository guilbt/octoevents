package com.guilbt.octoevents.controller

import com.guilbt.octoevents.model.dto.IssueEventDTO
import com.guilbt.octoevents.model.reflection.IssueEventReflection
import com.guilbt.octoevents.service.IssueService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.ValidationException


@RestController
@RequestMapping("issues")
class IssueController(private val issueService: IssueService) {
	private val LOGGER: Logger = LoggerFactory.getLogger(IssueController::class.java)
	
	@GetMapping("/{id}/events")
	fun getEvents(@PathVariable id: Long): List<IssueEventReflection> = issueService.getEvents(id)

	@PostMapping("/webhook")
	fun postGithubEvent(@RequestBody eventDTO: IssueEventDTO): Any {
		try {
			return issueService.createIssueEventByDTO(eventDTO)
		} catch (e: ValidationException) {
			val errorMsg = String.format("Malformed Object, error: %s", e.localizedMessage);
			LOGGER.info(String.format("POST /issues/webhook failed \n Exception: %s \n RequestBody: %s", errorMsg, eventDTO.toString()))
			return ResponseEntity.badRequest().body(errorMsg)
		}
	}
}
