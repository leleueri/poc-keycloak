package net.wl.poc

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import scala.util.Random

class AuthzSimulation extends Simulation {

  val keycloakLoginUrl = "http://10.24.130.143:8180/auth/realms/test2-realm/protocol/openid-connect/token"
  val keycloakLogoutUrl = "http://10.24.130.143:8180/auth/admin/realms/test2-realm/sessions/"
  val frontAppClientId = "front-app"
  val frontAppClientSecret = "8936a14a-3acd-4a6c-ae96-556c6f969526"
  
  val httpConf = http
    .baseURL("http://10.24.131.231:8180")
    .doNotTrackHeader("1")
    .userAgentHeader("Gatling")
    .disableWarmUp 

    
  val userFeeder = Iterator.range(1, 40000).map { index => Map ( 
      "user" -> s"loginuser${index}",
      "passwd" -> s"loginuser${index}"
      ) }
    
  
  val scn = scenario("Load Test Keycloak").
    repeat(100){
      feed(userFeeder)
      .exec(http("Login")
        .post(keycloakLoginUrl)
        .formParam("client_secret", frontAppClientSecret)
        .formParam("client_id", frontAppClientId)
        .formParam("grant_type", "password")
        .formParam("username", "${user}")
        .formParam("password", "${passwd}")
        //.header(HttpHeaderNames.ContentType, HttpHeaderValues.ApplicationFormUrlEncoded)
        .check(status.is(200))
        .check(jsonPath("$.access_token").exists.saveAs("access_token")))
       .exec(
         http("Call Gateway X")
           .get("/x")
           .header(HttpHeaderNames.Authorization, "Bearer ${access_token}")
       )
       .exec(
         http("Call Gateway Y")
           .get("/y")
           .header(HttpHeaderNames.Authorization, "Bearer ${access_token}")
       )
       .exec(
         http("Call Gateway A")
           .get("/a")
           .header(HttpHeaderNames.Authorization, "Bearer ${access_token}")
       )
       .exec(
         http("Call Gateway B")
           .get("/b")
           .header(HttpHeaderNames.Authorization, "Bearer ${access_token}")
       )
       .exec(
         http("Call Gateway C")
           .get("/c")
           .header(HttpHeaderNames.Authorization, "Bearer ${access_token}")
       ).exec(
         http("Call Gateway D")
           .get("/d")
           .header(HttpHeaderNames.Authorization, "Bearer ${access_token}")
       ).exec(
         http("Call Gateway i")
           .get("/i")
           .header(HttpHeaderNames.Authorization, "Bearer ${access_token}")
       ).exec(
         http("Call Gateway j")
           .get("/j")
           .header(HttpHeaderNames.Authorization, "Bearer ${access_token}")
       ).exec(
         http("Call Gateway G")
           .get("/g")
           .header(HttpHeaderNames.Authorization, "Bearer ${access_token}")
       ).exec(
         http("Call Gateway H")
           .get("/h")
           .header(HttpHeaderNames.Authorization, "Bearer ${access_token}")
       ).exec(
         http("Call Gateway P")
           .get("/p")
           .header(HttpHeaderNames.Authorization, "Bearer ${access_token}")
       ).exec(
         http("Call Gateway Q")
           .get("/q")
           .header(HttpHeaderNames.Authorization, "Bearer ${access_token}")
       ).exec(
         http("Call Gateway R")
           .get("/x")
           .header(HttpHeaderNames.Authorization, "Bearer ${access_token}")
       )
       // PAS DE LOGOUT pour le moment car il faut interpreter le JWT pour avoir le sessionid
//        .exec(http("Logout")
//        .delete(keycloakLogoutUrl+"${sessionId})
//        .header(HttpHeaderNames.Authorization, "Bearer ${access_token}")
//        .check(status.is(204))
       
  }

  setUp(scn.inject(atOnceUsers(20)).protocols(httpConf))
}