file://<WORKSPACE>/app/models/Product.scala
empty definition using pc, found symbol in pc: 
semanticdb not found
empty definition using fallback
non-local guesses:

offset: 774
uri: file://<WORKSPACE>/app/models/Product.scala
text:
```scala
package models

import io.ebean._
import jakarta.persistence._
import play.api.libs.json._

@Entity
@Table(name = "products")
case class Product(   // <-- CASE CLASS
  @Id
  var id: Long = 0L,

  @Column(name = "product_name")
  var productName: String = "",

  var category: String = "",
  @Column(name = "sub_category")
  var subCategory: String = "",
  var brand: String = "",
  var unit: String = "",
  var sku: String = "",
  @Column(name = "minimum_qty")
  var minimumQty: Int = 0,
  var quantity: Int = 0,
  var description: String = "",
  var tax: BigDecimal = 0,
  @Column(name = "discount_type")
  var discountType: String = "",
  var price: BigDecimal = 0,
  var status: Int = 1,
  @Column(name = "product_image")
  var productImage: String = ""
) extends Model
o@@bject Product extends Finder[Long, Product](classOf[Product]) {
  implicit val productWrites: Writes[Product] = Json.writes[Product]
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: 