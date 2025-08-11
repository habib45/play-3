package repositories

import models.Brand
import play.api.db.Database

import javax.inject.{Inject, Singleton}


@Singleton
class BrandRepository  @Inject()(db: Database) {
  def insert(brand: Brand): Int = {
    db.withConnection { conn =>
      val stmt = conn.prepareStatement(
        "INSERT INTO brands (name,description) VALUES (?, ?)"
      )
      stmt.setString(1, brand.name)
      stmt.setString(2, brand.description)
      stmt.executeUpdate()
    }
  }

  def listAll(): List[Brand] = {
    db.withConnection { conn =>
      val stmt = conn.prepareStatement("SELECT * FROM brands")
      val rs = stmt.executeQuery()
      val buffer = scala.collection.mutable.ListBuffer[Brand]()

      while (rs.next()) {
        buffer += Brand(
          Some(rs.getLong("id")),
          rs.getString("name"),
          rs.getString("description")
        )
      }

      buffer.toList
    }
  }
}
