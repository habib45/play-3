package repositories

import models.SubCategory
import play.api.db.Database
import scala.collection.mutable.ListBuffer
import javax.inject._

@Singleton
class SubCategoryRepository @Inject()(db: Database) {

//  def insert(subCategory: SubCategory): Int = {
//    db.withConnection { conn =>
//      val stmt = conn.prepareStatement(
//        "INSERT INTO products (" +
//          "category," +
//          "name," +
//          "code," +
//          "description," +
//          ") VALUES (?, ?, ?, ?)"
//      )
//      stmt.setLong(1, subCategory.category)
//      stmt.setString(2, subCategory.name)
//      stmt.setString(3, subCategory.code)
//      stmt.setString(4, subCategory.description)
//      stmt.executeUpdate()
//    }
//  }


  def listAll(): List[SubCategory] = {
    db.withConnection { conn =>
      val sql =
        """
          |SELECT sc.id,sc.category, sc.name,sc.code,
          |       sc.description,
          |       c.name AS category_name
          |FROM sub_category sc
          |JOIN category c ON sc.category = c.id
        """.stripMargin
      val stmt = conn.prepareStatement(sql)
      val rs = stmt.executeQuery()
      val buffer = scala.collection.mutable.ListBuffer[SubCategory]()
      while (rs.next()) {
        buffer += SubCategory(
          Some(rs.getLong("id")),
          rs.getLong("category"),
          rs.getString("name"),
          rs.getString("code"),
          rs.getString("description"),
          Option(rs.getString("category_name")),
        )
      }

      buffer.toList
    }
  }

}
