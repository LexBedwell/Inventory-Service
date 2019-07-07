package controllers

import play.api.mvc._
import play.api.libs.json._


class InventoryController extends InjectedController {

  def productInventory(productId: String) = Action { request =>
    val response: JsValue = JsObject(Seq("productId" -> JsNumber(productId.toInt), "inventory" -> JsString("???")))
    db.Main.main(Array(""))
    Ok(response)
  }

}