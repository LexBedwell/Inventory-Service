# Inventory Service

Service that stores and updates product inventory quantities for [BuyAndCell](https://github.com/LexBedwell/BuyAndCell).
Utilizes a Scala Play Framework and PostgreSQL database.

## Routes

| Method | Route | Call Definition | Description
| ------ | ----- | ----- | -----------
| GET | /admin/ping | controllers.AdminController.ping | Ping service
| GET | /inventory/$productId | controllers.InventoryController.getProductInventory | Check if product is in-stock
| PUT | /inventory | controllers.InventoryController.updateProductInventory | Deduct ordered quantities from product inventory
