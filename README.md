This application was created using https://github.com/auth0-blog/kotlin-spring-boot project as a boilerplate.

STEPS TO RUN THIS APPLICATION

1. You need to have a postgres server running in 5432 port or change the database configuration in the application.yml. It also has to have the default "postgres" database.
To create a new dockerized database, you can use the following command:
```
docker run --name octoevents-postgres -p 5432:5432 -e POSTGRES_PASSWORD=octoevents42 -d postgres
```

2. Your database has to have a schema named "octoevents";

If you are using the dockerized database, you have to exec it's psql first with:
```
docker exec -i octoevents-postgres psql -U postgres
```

then create the schema with:
```
create schema octoevents;
``` 

3. Your "octoevents" schema has to have the ISSUE_EVENT table;

inside the psql console, run the following script;

```
create table octoevents.ISSUE_EVENT
(
	ISSUE_EVENT_ID bigserial not null
		constraint ISSUE_EVENT_pk
			primary key,
	ISSUE_ID int8 not null,
	ACTION varchar(50) not null,
	CREATED_AT timestamp not null
);
```
