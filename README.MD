**Tech stack:**
- Java 17;
- Spring Boot 3.3.3

**How to build:**

mvn clean install

**How to run:**

Before running application need to add variables to make call to http://www.omdbapi.com site:
- apikey: ${MOVIE_API_KEY}

Also requires to get API KEY to make call to Chat Gpt:
- api-key: ${GPT_API_KEY}