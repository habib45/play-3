package controllers

import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents, Request}
import repositories.BrandRepository
import models.Brand
import scala.jdk.CollectionConverters._
import javax.inject.Inject

class BrandController @Inject()(
                                 cc: ControllerComponents,
                                 brandRepo: BrandRepository
                               ) extends AbstractController(cc) {

  def index: Action[AnyContent] = Action {
    val brandes = brandRepo.listAll();
    Ok(views.html.brand.index.render(brandes.asJava));
  }

  def add: Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.brand.add())
  }

  def saveBrand: Action[AnyContent] = Action { implicit request =>
    val data: Map[String, Seq[String]] = request.body.asFormUrlEncoded.getOrElse(Map.empty)
    try {
      val name = data.get("name").flatMap(_.headOption).getOrElse("")
      val description = data.get("description").flatMap(_.headOption).getOrElse("")

      val brand = Brand(None, name, description)
      if(brandRepo.insert(brand) ==1) {

        Redirect(routes.BrandController.index).flashing("msg" -> "Brand saved successfully")
      }else{
        Redirect(routes.BrandController.add).flashing("error" -> "Failed to save brand")
      }
    } catch {
      case ex: Exception =>
        println(s"Error saving brand: ${ex.getMessage}")
        Redirect(routes.BrandController.add).flashing("error" -> "Failed to save brand")
    }
  }
}
