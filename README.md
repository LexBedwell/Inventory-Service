# Inventory Service

Service that stores and updates product inventory quantities for [BuyAndCell](https://github.com/LexBedwell/BuyAndCell).
Utilizes a Scala Play Framework and PostgreSQL database.

## Routes

| Method | Route | Class | Description
| ------ | ----- | ----- | -----------
| GET | /admin/ping | controllers.AdminController.ping | Ping service
| GET | /inventory/$productId | controllers.InventoryController.getProductInventory | Check if product is in-stock
| PUT | /inventory | controllers.InventoryController.getProductInventory | Check if there is enough inventory to fill an order
