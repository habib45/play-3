file://<WORKSPACE>/app/controllers/ProductController.scala
empty definition using pc, found symbol in pc: 
semanticdb not found
empty definition using fallback
non-local guesses:

offset: 431
uri: file://<WORKSPACE>/app/controllers/ProductController.scala
text:
```scala
package controllers

import play.api.mvc._
import models.Product
import javax.inject._
import play.api.libs.json._
import io.ebean._
import java.util.List;
import scala.jdk.CollectionConverters._ 

@Singleton
class ProductController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def list = Action {
    val products = Product.all()
    Ok(views.html.product.list.render(Json.toJson(products)))
  }
// def @@list = Action {
//   val products: java.util.List[Product] = Product.find.all()
//   val scalaProducts: List[Product] = products.asScala.toList
//   Ok(Json.toJson(scalaProducts))
// }
  // def get(id: Long) = Action {
  //   Product.byId(id) match {
  //     case Some(product) => Ok(Json.toJson(product))
  //     case None          => NotFound(Json.obj("error" -> "Product not found"))
  //   }
  // }

  // def create = Action(parse.json) { request =>
  //   request.body.validate[Product].fold(
  //     errors => BadRequest(Json.obj("error" -> JsError.toJson(errors))),
  //     product => {
  //       product.save()
  //       Created(Json.toJson(product))
  //     }
  //   )
  // }

  // def update(id: Long) = Action(parse.json) { request =>
  //   Product.byId(id) match {
  //     case Some(existingProduct) =>
  //       request.body.validate[Product].fold(
  //         errors => BadRequest(Json.obj("error" -> JsError.toJson(errors))),
  //         updated => {
  //           existingProduct.productName = updated.productName
  //           existingProduct.category = updated.category
  //           existingProduct.subCategory = updated.subCategory
  //           existingProduct.brand = updated.brand
  //           existingProduct.unit = updated.unit
  //           existingProduct.sku = updated.sku
  //           existingProduct.minimumQty = updated.minimumQty
  //           existingProduct.quantity = updated.quantity
  //           existingProduct.description = updated.description
  //           existingProduct.tax = updated.tax
  //           existingProduct.discountType = updated.discountType
  //           existingProduct.price = updated.price
  //           existingProduct.status = updated.status
  //           existingProduct.productImage = updated.productImage
  //           existingProduct.update()
  //           Ok(Json.toJson(existingProduct))
  //         }
  //       )
  //     case None => NotFound(Json.obj("error" -> "Product not found"))
  //   }
  // }

  // def delete(id: Long) = Action {
  //   Product.byId(id) match {
  //     case Some(product) =>
  //       product.delete()
  //       NoContent
  //     case None => NotFound(Json.obj("error" -> "Product not found"))
  //   }
  // }
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: 