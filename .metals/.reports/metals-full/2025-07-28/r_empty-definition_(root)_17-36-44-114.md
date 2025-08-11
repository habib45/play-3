error id: file://<WORKSPACE>/app/controllers/ProductController.scala:
file://<WORKSPACE>/app/controllers/ProductController.scala
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -javax/inject/ProductRepository.
	 -play/api/mvc/ProductRepository.
	 -io/ebean/ProductRepository.
	 -play/api/libs/json/ProductRepository.
	 -repositories/ProductRepository.
	 -ProductRepository.
	 -scala/Predef.ProductRepository.
offset: 401
uri: file://<WORKSPACE>/app/controllers/ProductController.scala
text:
```scala
package controllers

import javax.inject._
import play.api.mvc._
import models.Product
import io.ebean._
import play.api.libs.json._
import repositories.ProductRepository

@Singleton
class ProductController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  implicit val productFormat: OFormat[Product] = Json.format[Product]

  def list = Action {
    val products = ProductRepos@@itory.findAll()
    Ok(Json.toJson(products))
  }

  def create = Action(parse.json) { request =>
    val product = request.body.as[Product]
    product.save()
    Created(Json.toJson(product))
  }

  def get(id: Long) = Action {
    Product.find.byId(id) match {
      case null => NotFound(Json.obj("error" -> "Product not found"))
      case product => Ok(Json.toJson(product))
    }
  }

  def update(id: Long) = Action(parse.json) { request =>
    Product.find.byId(id) match {
      case null => NotFound(Json.obj("error" -> "Product not found"))
      case product =>
        val updated = request.body.as[Product]
        product.productName = updated.productName
        product.price = updated.price
        product.update()
        Ok(Json.toJson(product))
    }
  }

  def delete(id: Long) = Action {
    Product.find.byId(id) match {
      case null => NotFound(Json.obj("error" -> "Product not found"))
      case product =>
        product.delete()
        NoContent
    }
  }
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: 