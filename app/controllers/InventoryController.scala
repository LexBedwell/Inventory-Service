package controllers

import play.api.mvc._
import play.api.libs.json._
import javax.inject.Inject
import db.InventoryDao
import scala.concurrent.Await
import scala.concurrent.duration._


class InventoryController @Inject()(dao: InventoryDao) extends InjectedController {

  def getProductInventory(productId: String) = Action { request =>

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

  def updateProductInventory = Action(parse.json) { request =>

    val orderedInventory = request.body.validate[Map[String,Int]].getOrElse(Map[String, Int]())

    val orderedInventoryStatus = orderedInventory.foldLeft(Map.empty[String, Boolean]){ case (acc, (k, v)) => {

      val dbResponse = Await.result(dao.fetchInventory(k), 5000 millis)

      val isInStock = dbResponse.length match {
        case 1 => dbResponse(0).qty match {
          case inv if inv - v > 0 => true
          case _ => false
          }
        case _ => false
      }
      acc + (k -> isInStock)
    }}

    val processTransaction = orderedInventoryStatus.foldLeft(true) { case (acc, (k, v)) => {
      acc match {
        case false => false
        case true => v
      }
    }}

//    processTransaction match {
//      case true => {
//        orderedInventory.foreach( ***updateDB***)
//      }
//      case _ =>
//    }

    val response = Json.toJson(orderedInventoryStatus + ("processTransaction" -> processTransaction))

    Ok(response)

  }

}