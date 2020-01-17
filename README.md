This application was created using https://github.com/auth0-blog/kotlin-spring-boot project as a boilerplate.

It's purpose is to receive webhooks payloads for github projects' Issue Events, and then expose a endpoint where you can get all events from a issue by it's id.


**STEPS TO RUN THIS APPLICATION**

1. You need to have a postgres server running in 5432 port or change the database configuration in the application.yml file. It also has to have the default "postgres" database.
If you have docker and want to create a new dockerized postgresql database, you can use the following command:
```
docker run --name octoevents-postgres -p 5432:5432 -e POSTGRES_PASSWORD=octoevents42 -d postgres
```

2. Your database has to have a schema named "octoevents";

If you are using the dockerized database, you have to exec it's psql first with:
```
docker exec -i octoevents-postgres psql -U postgres
```

Then create the schema running
```
create schema octoevents;
``` 

3. Your "octoevents" schema has to have the ISSUE_EVENT table with it's necessary columns;

Inside your psql console, run the following script

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


4. Use maven to run/install this application, by default it'll be available in the 8080 port.

5. Expose your application in a public URL, then add this URL to a project's Webhook.
You may use ngrok (https:/ngrok.com/) to install/debug the webhook calls, it'll generate a public URL that will route to your localhost.

then put at your desired github project's Webhook's Payload URL
```
${publicUrl}/issues/webhook
``` 

6. You may then use this URL to get events by a issueId

```
${publicUrl}/issues/${issueId}/events
```