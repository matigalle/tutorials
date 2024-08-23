# Tutorials
Spring Web API to manage tutorials through CRUD operations.

* Each tutorial has id, title, description, published status
* Actions help to create, retrieve, update and delete tutorials
* Actions also support custom finder methods such as find by published status or by title

### Available actions

| Actions                                             | Methods | URLs                     |
|-----------------------------------------------------|---------|--------------------------|
| Create new tutorial                                 | POST    | /tutorials               |
| Retrieve all tutorials                              | GET     | /tutorials               |
| Retrieve a tutorial by id                           | GET     | /tutorials/:id           |
| Update a tutorial by id                             | PUT     | /tutorials/:id           |
| Delete a tutorial by id                             | DELETE  | /tutorials/:id           |
| Delete all tutorials                                | DELETE  | /tutorials               |
| Find all published tutorials                        | GET     | /tutorials/published     |
| Find all tutorials which title contains **keyword** | GET     | /tutorials?title=keyword |

### Technology
* Java 17
* Spring Boot 3 (with Spring Web MVC)
* JPA
* PostgreSQL
* JUnit
* Mockito
* Gradle
