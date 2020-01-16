package com.guilbt.octoevents.repository

import com.guilbt.octoevents.model.IssueEvent
import com.guilbt.octoevents.model.reflection.IssueEventReflection
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface IssueEventRepository : JpaRepository<IssueEvent, Long> {
	@Query("""
		SELECT 
			    "action" as action,
			    CREATED_AT as createdAt 
			  FROM postgres.octoevents.ISSUE_EVENT
			  WHERE ISSUE_ID = :issueId 
	""", nativeQuery = true)
	fun getByIssueId(@Param("issueId") issueId: Long): List<IssueEventReflection>
}
