package controllers

import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import repositories.SubCategoryRepository
import javax.inject.{Inject, Singleton}
import scala.jdk.CollectionConverters._

@Singleton
class SubCategoryController @Inject()(
                                   cc: ControllerComponents,
                                   sunCategoryRep: SubCategoryRepository
                                 ) extends AbstractController(cc) {


  def subCategory: Action[AnyContent] = Action {
    val sunCategories = sunCategoryRep.listAll()
    Ok(views.html.subCategory.index.render(sunCategories.asJava))

  }
}
