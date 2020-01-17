
create database test;

\c test

create schema octoevents;

create table octoevents.ISSUE_EVENT
(
	ISSUE_EVENT_ID bigserial not null
		constraint ISSUE_EVENT_pk
			primary key,
	ISSUE_ID int8 not null,
	ACTION varchar(50) not null,
	CREATED_AT timestamp not null
);