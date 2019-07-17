package db

import anorm.SqlParser._
import anorm.{RowParser, ~, _}
import javax.inject.Inject
import models._
import play.api.db.DBApi
import scala.concurrent.Future


class InventoryDao @Inject()(dbapi: DBApi)(implicit ec: DatabaseExecutionContext){

  val db = dbapi.database("default")

  val inventoryRowParser: RowParser[InventoryLine] = {
    get[Long]("id") ~
    get[Int]("qty") map {
      case id ~
        qty
          =>
            InventoryLine(id = id, qty = qty)
    }
  }

  def fetchInventory(id: String): Future[Seq[InventoryLine]] = Future {
    db.withConnection { implicit c =>
      SQL(s"select * from product_inventory where id = $id").as(inventoryRowParser.*)
    }
  }

  def updateInventory(id: Int, qty: Int) = {
    db.withConnection { implicit c =>
      SQL"update product_inventory set qty = qty - $qty where id = $id".execute();
    }
  }

}