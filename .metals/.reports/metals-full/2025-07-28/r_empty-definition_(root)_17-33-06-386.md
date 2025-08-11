error id: file://<WORKSPACE>/app/models/Product.scala:
file://<WORKSPACE>/app/models/Product.scala
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 133
uri: file://<WORKSPACE>/app/models/Product.scala
text:
```scala
package models

import io.ebean._
import play.data.validation.Constraints
import scala.beans.BeanProperty
import jakarta.persistence.@@_

@Entity
@Table(name = "products")
class Product extends Model {

  @Id
  @BeanProperty
  var id: Long = _

  @Constraints.Required
  @Column(name = "Product_Name")
  @BeanProperty
  var productName: String = _

  @BeanProperty var category: String = _
  @BeanProperty var subCategory: String = _
  @BeanProperty var brand: String = _
  @BeanProperty var unit: String = _
  @BeanProperty var sku: String = _
  @BeanProperty var minimumQty: Int = _
  @BeanProperty var quantity: Int = _
  @BeanProperty var description: String = _
  @BeanProperty var tax: Double = _
  @BeanProperty var discountType: String = _
  @BeanProperty var price: Double = _
  @BeanProperty var status: Boolean = _
  @BeanProperty var productImage: String = _
}

object Product extends Finder[Long, Product](classOf[Product])

```


#### Short summary: 

empty definition using pc, found symbol in pc: 