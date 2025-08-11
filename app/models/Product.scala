package models

case class Product(
                    id: Option[Long],
                    name: String,
                    category: Long,
                    sub_category: Long,
                    brand: Long,
                    unit: String,
                    sku: String,
                    minimum_qty: BigDecimal,
                    quantity: BigDecimal,
                    description: String,
                    tax: String,
                    discount_type: String,
                    price: BigDecimal,
                    status: String,
                    brandName: Option[String] = None,
                    categoryName: Option[String] = None,
                    subCategoryName: Option[String] = None
                  )
