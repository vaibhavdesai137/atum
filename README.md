# atum

- This repo conatins the code for the Inuit's assignment. 
- The name "atum" is randomly chosen and does not mean anything.

### Architecture
- Travis [here](https://travis-ci.org/vaibhavdesai137/atum) is used for CI purposes and is configured for commits on master branch only.
- The build results in two docker images, atum-mysql and atum-tomcat
  - atum-mysql: hosts mysql and has the schema created already
  - atum-tomcat: hosts tomcat and has the war file ready to serve traffic
- The containers interact by sharing a docker network
- The containers are spun up using a shell script on endpoints (triggered via ansible)
![Alt text](arch.png?raw=true "Title")

### Deploy Environment
- Selected DigitalOcean as cloud provider
- This is because they provide direct access to VMs allowing to execute any scripts that we want
- Downside is Travis does not have an easy way to interact with them
- Tried Heroku since Travis supports it but they have their own docker circus and I wasn't able to get it going :(

Pre-Production | Production
--- | ---
159.89.136.190 | 167.99.98.82

### Local Setup
This section goes over the setup needed to be up and running on localhost. Have Tomcat installed (v7 and higher)
```
git clone git@github.com:vaibhavdesai137/atum.git
cd atum
mvn clean install -DskipTests (build)
mvn test (unit tests)
mvn verify (integration tests)
```
Before starting the server:
- Ensure mysql is running in locallhost on port 3306
- Ensure schema is created by using atum-core/src/main/resources/scripts/schema.sql

### APIs
**CHECKOUT**

Validations:
- Invalid book id
- Invalid member id
- Confirm book is not already checkedout

```
Endpoint: http://<server:port>/books/<book-id>/action/checkout
Method: POST
Headers: Content-Type: application/json, Accept: application/json
Payload: 
{
  "memberId": "1",
  "notes": "Foo Bar",
  "checkoutDate": "sdasd",
  "expectedReturnDate": "sdasd"
}
Response:
{
  "checkoutSuccessful": true,
  "msg": "ok"
}
```

**RETURN**

Validations:
- Invalid book id
- Confirm book is checkedout to be eligible for return

```
Endpoint: http://<server:port>/books/<book-id>/action/return
Method: POST
Headers: Accept: application/json
Response:
{
  "returnSuccessful": true,
  "msg": "ok"
}
```

**BOOK DETAILS**
```
Endpoint: http://<server:port>/books/title/<title>
Method: GET
Headers: Accept: application/json
Response:
{
  "msg: "ok",
  "books: [
    {
      "id: 1,
      "title": "Lord Of The Rings",
      "status": "AVAILABLE",
      "returnDate": null
    },
    {
      "id: 2,
      "title": "Lord Of The Rings",
      "status": "CHECKEDOUT",
      "returnDate": "2018-04-01 13:58:03.0"
    }
  ]  
}
```

**BOOKS CHECKEDOUT BY MEMBERS**
```
Endpoint: http://<server:port>/members/<member-id>/books
Method: GET
Headers: Accept: application/json
Response:
{
  "msg: "ok",
  "booksBorrowed": [
    {
      "bookId": 5,
      "bookTitle": "The Hobbit",
      "checkoutDate": "2018-03-18 14:01:26.0",
      "returnDate": "2018-04-01 14:01:26.0"
    }
  ]
}
```

