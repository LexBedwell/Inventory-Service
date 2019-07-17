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

    val orderedInventoryStatus = orderedInventory.map{ case (productId, orderQty) => {

      val dbResponse = Await.result(dao.fetchInventory(productId), 5000 millis)

      val isInStock = dbResponse.length match {
        case 1 => dbResponse(0).qty match {
          case inv if inv - orderQty > 0 => true
          case _ => false
          }
        case _ => false
      }
      Map(productId -> isInStock)
    }}

    val processTransaction = orderedInventoryStatus.foldLeft(true)( (acc, invMap) => {
      acc match {
        case false => false
        case true => invMap(invMap.keys.head)
      }
    })

//    now do processTransaction match: true => update db and send response, false => don't update db and send response
//    processTransaction match {
//      case true => {
//        orderedInventory.foreach( ***updateDB***)
//      }
//      case _ =>
//    }

    val responseMap = orderedInventoryStatus.foldLeft(Map.empty[String, Boolean]) ( (acc, invMap) => {
      acc + (invMap.keys.head -> invMap(invMap.keys.head))
    })

    val response = Json.toJson(responseMap + ("processTransaction" -> processTransaction))

    Ok(response)

  }

}