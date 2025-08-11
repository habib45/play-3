package controllers


import javax.inject.{Inject, Singleton}
import play.api.mvc.{Action, _}
import play.api.i18n.I18nSupport
import repositories.{ProductRepository,BrandRepository}
import models.Product
import models.Brand
import scala.jdk.CollectionConverters._

@Singleton
class ProductController @Inject()(
                                   cc: ControllerComponents,
                                   productRepo: ProductRepository,
                                   brandRepo: BrandRepository,
                                 ) extends AbstractController(cc) {


  def products: Action[AnyContent] = Action {
    val products = productRepo.listAll()
    for(product <- products){
      System.out.println(product)
    }
    System.out.println(products)
    Ok(views.html.product.index.render(products.asJava))

  }

  def add: Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val brandes =brandRepo.listAll().asJava
//    Ok(views.html.product.add.render(brandes))
    Ok(views.html.product.add.render(brandes, request))

  }

  def save: Action[AnyContent] = Action { implicit request =>
    val formData = request.body.asFormUrlEncoded

    formData.map { args =>
      val productName = args("name").head
      val category = args("category").head
      Ok(s"Product name is $productName and category is  $category");
    }.getOrElse(Ok("Opps")).flashing("error" -> "Something wring ")
    //    val name = formData.flatMap(_.get("name").flatMap(_.headOption)).getOrElse("No name")
    //    val category = formData.flatMap(_.get("category").flatMap(_.headOption)).getOrElse("No category")
    Redirect(routes.ProductController.add).flashing("msg" -> "Product save successfully");
  }


  //  def saveProduct: Action[AnyContent] = Action { implicit request =>
  //    val data: Map[String, Seq[String]] = request.body.asFormUrlEncoded.getOrElse(Map.empty)
  //
  //    data.foreach { case (key, values) =>
  //      println(s"$key -> ${values.mkString(",")}")
  //    }
  //    productRepo.insert(data: Map[String, Seq[String]])
  //    Redirect(routes.ProductController.add).flashing("msg"->"Product save successfully");
  //  Ok("Received form data")
  //  val productData ="";
  //    productRepo.insert(productData)
  //    Redirect(routes.ProductController.add).flashing("msg"->"Product save successfully");
  //    Redirect(routes.ProductController.showForm()).flashing("success" -> "Product saved!")
  //    productForm.bindFromRequest.fold(
  //      formWithErrors => BadRequest(views.html.productForm(formWithErrors)),
  //      productData => {
  //        productRepo.insert(productData)
  //        Redirect(routes.ProductController.showForm()).flashing("success" -> "Product saved!")
  //      }
  //    )
  //  }

  def saveProduct: Action[AnyContent] = Action { implicit request =>
    val data: Map[String, Seq[String]] = request.body.asFormUrlEncoded.getOrElse(Map.empty)
    try {
      // Extract fields safely
      val name = data.get("name").flatMap(_.headOption).getOrElse("")
      val price = BigDecimal(data.get("price").flatMap(_.headOption).getOrElse("0"))
      val description = data.get("description").flatMap(_.headOption).getOrElse("")
      val category = data.get("category").flatMap(_.headOption).getOrElse("")
      val subCategory = data.get("sub_category").flatMap(_.headOption).getOrElse("")
      val brand = data.get("brand").flatMap(_.headOption).getOrElse("")
      val unit = data.get("unit").flatMap(_.headOption).getOrElse("")
      val sku = data.get("sku").flatMap(_.headOption).getOrElse("")
      val minimum_qty = BigDecimal(data.get("minimum_qty").flatMap(_.headOption).getOrElse("0"))
      val quantity = BigDecimal(data.get("quantity").flatMap(_.headOption).getOrElse("0"))
      val tax = data.get("tax").flatMap(_.headOption).getOrElse("")
      val discount_type = data.get("discount_type").flatMap(_.headOption).getOrElse("")
      val status = data.get("status").flatMap(_.headOption).getOrElse("")

      //    data.foreach { case (key, values) =>
      //      println(s"$key -> ${values.mkString(",")}")
      //    }
      val brandId = brand.toLong
      val categoryId = category.toLong
      val subCategoryId = subCategory.toLong
      // Save to DB using Product case class
      val product = Product(None, name, categoryId, subCategoryId, brandId, unit, sku, minimum_qty, quantity, description, tax, discount_type, price, status)
      productRepo.insert(product)

      Redirect(routes.ProductController.products).flashing("msg" -> "Product saved successfully")
    } catch {
      case ex: Exception =>
        // Log the error (recommended for debugging)
        println(s"Error saving product: ${ex.getMessage}")
        // Redirect with error message
        Redirect(routes.ProductController.add).flashing("error" -> "Failed to save product")
    }
  }

  def importProduct: Action[AnyContent] = Action { implicit request =>
    Ok(views.html.product.importdata.render())
  }
}

