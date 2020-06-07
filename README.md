Spring-Security-With-JWT

Objective
1. Create a new authentication API endpoint
2. Examines every incoming request for valid JWT and authorize

Step-1
A /authenticate API endpoint

JPA Authentication to authenticate username and password 1st time with /authenticate API

JWT will secure communication for further incoming requests by creating JWT Token

- Accepts user id and password
- Returns JWT as response


Step-2
Intercept all incoming requests

File Name: JWTRequestFilter.java 

Class Name: Overriding doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)

- Extract JWT from the header
- Validate and set in execution context
