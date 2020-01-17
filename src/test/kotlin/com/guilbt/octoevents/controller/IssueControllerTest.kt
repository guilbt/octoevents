package com.guilbt.octoevents.controller

import org.json.JSONArray
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import javax.transaction.Transactional


@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class IssueControllerTest {
	@Autowired
	private lateinit var mockMvc: MockMvc

	fun mockMvcGetIssueEventsByIssue(issueId: Long): ResultActions {
		return mockMvc.perform(MockMvcRequestBuilders.get("/issues/${issueId}/events"))
	}

	fun mockMvcPostWebhookEvent(content: String): ResultActions {
		return mockMvc.perform(MockMvcRequestBuilders.post("/issues/webhook").content(content))
	}

	fun mockMvcpostDefaultWebhookEditEvent(): ResultActions {
		return mockMvcPostWebhookEvent("""
		{
			"issue": {
				"id": 127389721893
			},
			"action": "edit"
		}
	""")
	}

	@Test
	fun testSendingNewWeebhookEditEvent() {
		mockMvcpostDefaultWebhookEditEvent()
				.andExpect(MockMvcResultMatchers.status().isOk())
	}

	@Test
	fun testSendingTwoNewWeebhookEditEventsAndVerifyDifIds() {
		val issueEvent1ResponseContent = mockMvcpostDefaultWebhookEditEvent().andReturn().response.contentAsString;
		val issueEvent2ResponseContent = mockMvcpostDefaultWebhookEditEvent().andReturn().response.contentAsString;
		Assert.assertNotEquals(issueEvent1ResponseContent, issueEvent2ResponseContent)
	}

	@Test
	fun testBadRequestSendingWrongTypeWeebhookEvent() {
		mockMvcPostWebhookEvent("""
		{
			"action": "edit"
		}
		""")
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
	}

	@Test
	fun testBadRequestSendingIssueCommentTypeWeebhookEvent() {
		mockMvcPostWebhookEvent("""
		{
			"action": "edit",
			"issue": {
				"id": 12738972189321312312
			},
			"comment": {}
		}
		""").andExpect(MockMvcResultMatchers.status().isBadRequest())
	}

	@Test
	fun testGettingIssueEventsByRandomEventGeneration() {
		val qtd = Math.ceil(Math.random() * 10).toInt();
		val issueId = 21897398127398217L
		val action = "edit"
		for (i in 0 until qtd) {
			mockMvcPostWebhookEvent("""
				{
					"issue": {
						"id": ${issueId}
					},
					"action": ${action}
				}
			""");
		}
		val responseString = mockMvcGetIssueEventsByIssue(issueId).andReturn().response.contentAsString;
		val responseJSON = JSONArray(responseString);
		Assert.assertEquals(qtd, responseJSON.length())
	}


	@Test
	fun testGettingEmptyList() {
		val issueId = 21897398127398218L
		val responseString = mockMvcGetIssueEventsByIssue(issueId).andReturn().response.contentAsString;
		val responseJSON = JSONArray(responseString);
		Assert.assertEquals(0, responseJSON.length())
	}
}
