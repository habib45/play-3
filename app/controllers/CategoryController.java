package controllers;

import models.Category;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.mvc.*;
import repositories.CategoryRepository;
//import validators.CategoryValidator;
import javax.inject.Inject;
import java.util.List;
import java.nio.file.Paths;
import play.libs.Files.TemporaryFile;
import java.io.File;
import java.util.Map;

public class CategoryController extends Controller {

    private final FormFactory formFactory;
    private final MessagesApi messagesApi;
    private final CategoryRepository categoryRepository;
//    private final CategoryValidator validator;

    @Inject
    public CategoryController(
            FormFactory formFactory,
            MessagesApi messagesApi,
            CategoryRepository categoryRepository
    ) {
        this.formFactory = formFactory;
        this.messagesApi = messagesApi;
        this.categoryRepository = categoryRepository;
    }

    public Result index(Http.Request request) {
        List<Category> categories = categoryRepository.findAll();
        return ok(views.html.category.list.render(categories));
    }

    public Result createCategory(Http.Request request) {
        Form<Category> form = formFactory.form(Category.class);
        return ok(views.html.category.create.render(form, request, messagesApi.preferred(request)));

    }

    public Result save(Http.Request request) {
//        Category category = new Category();
        Form<Category> categoryForm = formFactory.form(Category.class).bindFromRequest(request);

        if (categoryForm.hasErrors()) {
            return badRequest(
                    views.html.category.create.render(categoryForm, request, messagesApi.preferred(request))
            );
        }
        Category categoryInfo = categoryForm.get();
        // Handle file upload
        // Http.MultipartFormData<TemporaryFile> body = request.body().asMultipartFormData();
        // Http.MultipartFormData.FilePart<TemporaryFile> filePart = body.getFile("image");
        // if (filePart != null) {
        //     String fileName = System.currentTimeMillis() + "_" + filePart.getFilename();
        //     String uploadPath = "public/uploads/categories/" + fileName;
        //     filePart.getRef().copyTo(new java.io.File(uploadPath), true);
        //     categoryInfo.setImage("/assets/uploads/categories/" + fileName);
        // }
        System.out.println(categoryInfo);

//        if (categoryInfo.getName() == null || categoryInfo.getName().trim().isEmpty()) {
//            return badRequest("Category name cannot be empty.");
//        }
        categoryInfo.setImage("ABC");
//        System.out.println("Category Name: " + category.getName());
        boolean saveResult = categoryRepository.save(categoryInfo);
        if(saveResult) {
//            flash("success", "Category successfully save");
            return redirect(routes.CategoryController.index())
                    .flashing("success", "Category saved successfully!");
        }else{
//            flash("error", "Unable to save category. Please try again.");
            return redirect(routes.CategoryController.createCategory())
                    .flashing("error", "Unable to save category. Please try again.");
        }
    }

    public Result edit(Http.Request request, Long id) {
        Category category = Category.findById(id);
        if (category == null) {
            return notFound("Category not found");
        }
        Form<Category> form = formFactory.form(Category.class).fill(category);
        return ok(views.html.category.edit.render(form, category.getId(), request, messagesApi.preferred(request)));
    }

public Result update(Http.Request request, Long id) {
    Form<Category> categoryForm = formFactory.form(Category.class).bindFromRequest(request);
    if (categoryForm.hasErrors()) {
        return badRequest(views.html.category.edit.render(categoryForm, id, request, messagesApi.preferred(request)));
    }

    Category updatedCategory = categoryForm.get();
    Category existingCategory = categoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Category not found"));

    if (existingCategory == null) {
        return notFound("Category not found");
    }

    existingCategory.setName(updatedCategory.getName());
    categoryRepository.update(existingCategory);
    return redirect(routes.CategoryController.index());
}

    public Result delete(Http.Request request, Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        if (category != null) {
            category.delete();
        }
        return redirect(routes.CategoryController.index());
    }
}
