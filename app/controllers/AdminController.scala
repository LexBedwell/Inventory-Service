package controllers

import play.api.mvc._
import play.api.libs.json._
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class AdminController extends InjectedController {

  def ping = Action { request =>
    val fullRequestUrl = if (request.secure) "https://" else "http://" + request.host + request.uri
    val response: JsValue = JsObject(Seq(
      "request" -> JsObject(Seq(
          "url" -> JsString(fullRequestUrl),
          "server_received_time" -> JsString(ZonedDateTime.now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ"))))),
      "response" -> JsObject(Seq("results" -> JsString("pong")))
    ))
    Ok(response)
  }

}
