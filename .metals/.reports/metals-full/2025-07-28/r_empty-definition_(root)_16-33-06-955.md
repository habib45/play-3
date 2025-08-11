file://<WORKSPACE>/app/controllers/ProductController.scala
empty definition using pc, found symbol in pc: 
semanticdb not found
empty definition using fallback
non-local guesses:

offset: 311
uri: file://<WORKSPACE>/app/controllers/ProductController.scala
text:
```scala
package controllers

import play.api.mvc._
import play.api.libs.json._
import javax.inject._
import repositories.ProductRepository
import models.Product
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ProductController @Inject()(
    cc: ControllerComponents,
    repo: ProductRepository
)(@@implicit ec: ExecutionContext) extends AbstractController(cc) {



private val productForm: Form[Product] = Form(
  mapping(
    "id" -> default(longNumber, 0L),
    "productName" -> nonEmptyText,
    "category" -> text,
    "subCategory" -> text,
    "brand" -> text,
    "unit" -> text,
    "sku" -> text,
    "minimumQty" -> number,
    "quantity" -> number,
    "description" -> text,
    "tax" -> bigDecimal,
    "discountType" -> text,
    "price" -> bigDecimal,
    "status" -> text,
    "productImage" -> text
  )( (id, productName, category, subCategory, brand, unit, sku, minQty, qty, desc, tax, discount, price, status, img) => {
      val p = new Product
      p.id = id
      p.productName = productName
      p.category = category
      p.subCategory = subCategory
      p.brand = brand
      p.unit = unit
      p.sku = sku
      p.minimumQty = minQty
      p.quantity = qty
      p.description = desc
      p.tax = tax
      p.discountType = discount
      p.price = price
      p.status = status
      p.productImage = img
      p
    })
    (p: Product) => Some(
      (p.id, p.productName, p.category, p.subCategory, p.brand, p.unit, p.sku, p.minimumQty, p.quantity, p.description, p.tax, p.discountType, p.price, p.status, p.productImage)
    )
)

def listPage = Action {
  val products = Product.all()
  Ok(views.html.products.list(products))
}

def createPage = Action {
  Ok(views.html.products.form(productForm, false))
}

def saveProduct = Action { implicit request =>
  productForm.bindFromRequest().fold(
    formWithErrors => BadRequest(views.html.products.form(formWithErrors, false)),
    product => {
      product.save()
      Redirect(routes.ProductController.listPage())
    }
  )
}

def editPage(id: Long) = Action {
  Product.byId(id) match {
    case Some(product) =>
      val filledForm = productForm.fill(product)
      Ok(views.html.products.form(filledForm, true))
    case None => NotFound("Product not found")
  }
}

def updateProductSubmit(id: Long) = Action { implicit request =>
  productForm.bindFromRequest().fold(
    formWithErrors => BadRequest(views.html.products.form(formWithErrors, true)),
    product => {
      product.update()
      Redirect(routes.ProductController.listPage())
    }
  )
}

}

```


#### Short summary: 

empty definition using pc, found symbol in pc: 