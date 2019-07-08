package controllers

import play.api.mvc._
import play.api.libs.json._
import javax.inject.Inject
import db.InventoryDao
import scala.concurrent.{ExecutionContext, Await}
import scala.concurrent.duration._
import akka.actor._


class InventoryController @Inject()(cc: MessagesControllerComponents, dao: InventoryDao, system: ActorSystem)(implicit ec: ExecutionContext)
  extends InjectedController {

  def productInventory(productId: String) = Action { request =>
    val dbResponse = Await.result(dao.fetchInventory(productId), 5000 millis)
    dbResponse.length match {
      case 1 => {
        val response: JsValue = JsObject(Seq("productId" -> JsNumber(dbResponse(0).id), "inventory" -> JsNumber(dbResponse(0).qty)))
        Ok(response)
      }
      case _ => {
        val response: JsValue = JsObject(Seq("error" -> JsString("unable to find productId")))
        Ok(response)
      }
    }
  }

}