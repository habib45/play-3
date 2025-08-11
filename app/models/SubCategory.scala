package models

case class SubCategory(
                        id: Option[Long],
                        category: Long,
                        name: String,
                        code: String,
                        description: String,
                        categoryName: Option[String] = None
                       )
