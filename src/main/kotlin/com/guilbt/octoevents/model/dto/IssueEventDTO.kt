package com.guilbt.octoevents.model.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper

data class SimpleIssueDTO @JsonIgnoreProperties(ignoreUnknown = true) @JsonCreator constructor(
		@JsonProperty("id") val id: Long?
)


data class IssueEventDTO @JsonIgnoreProperties(ignoreUnknown = true) @JsonCreator constructor(
		@JsonProperty("action") val action: String?,
		@JsonProperty("issue") val issue: SimpleIssueDTO?,
		@JsonProperty("comment") val comment: String?
) {
	override fun toString(): String {
		try {
			return ObjectMapper().writeValueAsString(this)
		} catch (e: JsonProcessingException) {
			return super.toString()
		}
	}
}
