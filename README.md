# user-service
This Spring boot Microservice exposes REST API's for the user to invoke. Also, it provides JWT authentication to access the API.

## Pre-requisite:
* Create the schema USERS and create a table with name USERS having below columns,

| API | Description |
| ------ | ------ |
|NO|	NUMBER - SEQUENCE|
|USERID|	VARCHAR2(30 BYTE)
|TITLE|	VARCHAR2(200 BYTE)	
|DESCRIPTION|	VARCHAR2(500 BYTE)	
|USERNAME|	VARCHAR2(50 BYTE)
|PASSWORD|	VARCHAR2(50 BYTE)

## API list
| API | Description |
| ------ | ------ |
| ${base_url}/user-service/authenticate :POST | URL to authenticate the user using jwt. e.g) {"userName":"Sundar","password":"Rahane123*#"} |
| ${base_url}/user-service/user :GET  |  URL to get the users posts with title and description |
| ${base_url}/user-service/user :POST  |  URL to add the users posts with title and description. 
e.g) To add a user 
{
"userId":"1",
"title":"Docker",
"description": "Docker is a set of platform as a service products that use OS-level virtualization to deliver software in packages called containers",
"userName":"Sundar",
"password":"Rahane123*#"
} |

Headers:
Pass the jwt token like below,
Authorization: Bearer ${token}
