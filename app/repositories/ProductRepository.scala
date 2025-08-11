package repositories

import javax.inject._
import play.api.db.Database
import models.Product
import scala.collection.mutable.ListBuffer

@Singleton
class ProductRepository @Inject()(db: Database) {

  def insert(product: Product): Int = {
    db.withConnection { conn =>
      val stmt = conn.prepareStatement(
        "INSERT INTO products (" +
          "name," +
          "category," +
          "sub_category," +
          "brand," +
          "unit," +
          "sku," +
          "minimum_qty," +
          "quantity," +
          "description," +
          "tax," +
          "discount_type, " +
          "price, " +
          "status" +
          ") VALUES (?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?)"
      )
      stmt.setString(1, product.name)
      stmt.setLong(2, product.category)
      stmt.setLong(3, product.sub_category)
      stmt.setLong(4, product.brand)
      stmt.setString(5, product.unit)
      stmt.setString(6, product.sku)
      stmt.setBigDecimal(7, product.minimum_qty.bigDecimal)
      stmt.setBigDecimal(8, product.quantity.bigDecimal)
      stmt.setString(9, product.description)
      stmt.setString(10, product.tax)
      stmt.setString(11, product.discount_type)
      stmt.setBigDecimal(12, product.price.bigDecimal)
      stmt.setString(13, product.status)
      stmt.executeUpdate()
    }
  }


  def listAll(): List[Product] = {
    db.withConnection { conn =>
      val sql =
        """
          |SELECT p.id, p.name, p.category, p.sub_category, p.brand,
          |       p.unit, p.sku, p.minimum_qty, p.quantity,
          |       p.description, p.tax, p.discount_type, p.price, p.status,
          |       b.name AS brand_name,
          |       c.name AS category_name,
          |       sc.name AS sub_category_name
          |FROM products p
          |JOIN brands b ON p.brand = b.id
          |JOIN category c ON p.category = c.id
          |JOIN sub_category sc ON p.sub_category = sc.id
        """.stripMargin
      val stmt = conn.prepareStatement(sql)
      val rs = stmt.executeQuery()
      val buffer = scala.collection.mutable.ListBuffer[Product]()
//      val products = ListBuffer[Product]()
      while (rs.next()) {
        buffer += Product(
          Some(rs.getLong("id")),
          rs.getString("name"),
          rs.getLong("category"),
          rs.getLong("sub_category"),
          rs.getLong("brand"),
          rs.getString("unit"),
          rs.getString("sku"),
          rs.getBigDecimal("minimum_qty"),
          rs.getBigDecimal("quantity"),
          rs.getString("description"),
          rs.getString("tax"),
          rs.getString("discount_type"),
          rs.getBigDecimal("price"),
          rs.getString("status"),
          Option(rs.getString("brand_name")),
          Option(rs.getString("category_name")),
          Option(rs.getString("sub_category_name"))
        )

//        products += Product(
//          id = Some(rs.getLong("id")),
//          name = rs.getString("name"),
//          category = rs.getString("category"),
//          sub_category = rs.getString("sub_category"),
//          brand = rs.getLong("brand"),
//          unit = rs.getString("unit"),
//          sku = rs.getString("sku"),
//          minimum_qty = rs.getBigDecimal("minimum_qty"),
//          quantity = rs.getBigDecimal("quantity"),
//          description = rs.getString("description"),
//          tax = rs.getString("tax"),
//          discount_type = rs.getString("discount_type"),
//          price = rs.getBigDecimal("price"),
//          status = rs.getString("status"),
//          Some(rs.getString("brand_name"))
//        )
      }

      buffer.toList
    }
  }

}
