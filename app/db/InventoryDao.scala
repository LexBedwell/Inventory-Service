package db

import slick.jdbc.PostgresProfile.api._
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.language.postfixOps

object Main {

  case class ProductInventory(
                   id: Int,
                   qty: Int)

  class ProductInventoryTable(tag: Tag) extends Table[(Int, Int)](tag, "product_inventory") {
    def id = column[Int]("id")

    def qty = column[Int]("qty")

    def * = (id, qty)
  }

  val db = Database.forConfig("scalaxdb")

  val productInventory = TableQuery[ProductInventoryTable]

  def main(args: Array[String]): Unit = {
    Await.result({
      db.run(productInventory.result).map(_.foreach(row =>
        println("product with id " + row._1 + " has quantity " + row._2)))
    }, 1 minute)
  }
}