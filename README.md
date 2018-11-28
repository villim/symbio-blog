# symbio-blog

Blog Management System

## Features

* Anoymous User can view and search for posts
* Anoymous can register with email address
* Registed User can publish posts and make comments

## API Documents

Please check with Swagger:
http://localhost:8080/symbio-blog/swagger-ui.html#/

![Swagger](https://github.com/villim/symbio-blog/tree/master/documents/swagger-apis-doc.png)


## Technique 

* DDD
* Spring
* SQLite
* Gradle
* Cache Posts
* Swagger
 
## How to Run

### 1. Prepare configuration folder

```bash
$ mkdir -p /opt/symbio/{app,config,logs,h2db}/blog
```

* **/opt/symbio/app/blog** is for excutable SpringBoot Jar file while deployment install with RPM ( not need for local testing )
* **/opt/symbio/config/blog** is for Blog application configuration files
* **/opt/symbio/logs/blog** is for Blog application log files
* **/opt/symbio/h2db/blog** is for Blog application H2DB files

### 2. Prepare configuration files

Copy properties files from blog-init/src/main/resources
```bash
$ cd ~/symbio-blog/
$ copy -R blog-init/src/main/resources /opt/symbio/config/blog
```

Copy H2DB files:
```bash
$ cd ~/symbio-blog/
$ copy /blog-init/DB-SCRIPTS/*.db /opt/symbio/config/blog

```


### 3. Check H2 Database Schema

Not necessary, but if you want to check Database schema, you may follow next steps.

This instructions is only for MacOS, Windows please refer to [H2 Quickstart](http://h2database.com/html/quickstart.html)

First, install H2 with

```bash
$ brew install h2
```

Then Run H2 just with:

```bash
$ h2
```

Open Link in Browser:
```bash
http://localhost:8082/
```

Then configure as following picture:

![H2DB Login](https://github.com/villim/symbio-blog/tree/master/documents/H2DB-login.png)

```text
Driver Class: org.h2.Driver
JDBC URL: jdbc:h2:/opt/symbio/h2db/blog/blogDb;DB_CLOSE_DELAY=-1
User Name: sa
Password: sa
```

After logged in, you can see Schemas as:

![H2DB Schemas](https://github.com/villim/symbio-blog/tree/master/documents/H2DB-schemas.png)


If TABLES and SEQUENCE not there, you can re-create with **/blog-init/DB-SCRIPTS/db-revision001.sql**

Make sure you exit this H2 instance before run blog-rest-app 