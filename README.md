This application was created using https://github.com/auth0-blog/kotlin-spring-boot project as a boilerplate.

It's purpose is to receive webhooks payloads for github projects' Issue Events, and then expose a endpoint where you can get all events from a issue by it's id.


<h2>Deploying with Docker</h2>

The application was configured to be runned inside a `docker` container, and deployed by docker compose.
You can deploy the application by running `docker-compose up` in the project's root folder.
- The Postgres server will be available through the 15432 port
- The application will be running in the 8080 port

<h2>Deploying in Local Machine</h2>
<h4>You need to have the following resources installed in your machine.</h4>
	
- Maven 3.6.0
- Java 8 (JDK 1.8 or later)
- Postgres 9.6

**1.** You need to have a Postgres server running and insert it's configuration in the `./src/main/resources/application.yml` file.

The default user configured in the application is `postgres` with `octoevents42` as password, but you're *REQUIRED* to change the datasource's url in the `application.yml` file to the correct value, since it's configured to the PSQL docker container local URL.
You can create the required database structures with the sql scripts in the `./scripts/init.sql` file

**2.** Use maven to package/install this application, by default it'll be available in the 8080 port.
You can do this by running `mvn install` in the project's root folder.
Then, inside the created `target` dir, run the created .jar file with Java
```
java -jar octoevents.jar
```


<h2>Using the application</h2>

**1.** Expose your application in a public URL, then add this URL to a github project's Webhook.
You may use ngrok (https:/ngrok.com/) to install/debug the webhook calls, it'll generate a public URL that will route to your localhost.

Then put this url as your desired github project's Webhook's Payload URL
```
${publicUrl}/issues/webhook
``` 

Example:
```
http://3e96a305.ngrok.io/issues/webhook
```

After the required configuration, Github will be sending POST requests to this url.
Valid POST Example:

``` 
POST http://3e96a305.ngrok.io/issues/webhook
{
	"issue" : {
		"id": "1"
	},
	"action": "edited"
}
```
It's required for the Request Body to have a JSON object with the following properties:
- action: a String type
- issue: a JSON object type that contains a 'id' property with a number type.


**2.** Then, you can use this URL to get events from a issue by a issueId

```
${publicUrl}/issues/${issueId}/events
```

example:
```
http://3e96a305.ngrok.io/issues/1/events
```

It'll return a list of events associated to this event (which can be empty).

example: 
```
[
    {
        "action": "edited",
        "createdAt": "2020-01-17 11:42:43.372"
    },
    {
        "action": "delete",
        "createdAt": "2020-01-17 11:53:00.923"
    },
]
```
