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
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.io.IOException;

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
        Form<Category> categoryForm = formFactory.form(Category.class).bindFromRequest(request);

        if (categoryForm.hasErrors()) {
            return badRequest(
                    views.html.category.create.render(categoryForm, request, messagesApi.preferred(request))
            );
        }
        
        Category categoryInfo = categoryForm.get();
        
        if (categoryInfo.getName() == null || categoryInfo.getName().trim().isEmpty()) {
            return badRequest("Category name cannot be empty.");
        }

        // Handle file upload
        String imagePath = handleImageUpload(request);
        if (imagePath != null) {
            categoryInfo.setImage(imagePath);
        }

        categoryRepository.save(categoryInfo);
        return redirect(routes.CategoryController.createCategory());
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

        // Update basic fields
        existingCategory.setName(updatedCategory.getName());
        existingCategory.setDescription(updatedCategory.getDescription());
        existingCategory.setCode(updatedCategory.getCode());

        // Handle image upload for update
        String imagePath = handleImageUpload(request);
        if (imagePath != null) {
            // Delete old image if exists
            if (existingCategory.getImage() != null && !existingCategory.getImage().isEmpty()) {
                deleteOldImage(existingCategory.getImage());
            }
            existingCategory.setImage(imagePath);
        }

        categoryRepository.update(existingCategory);
        return redirect(routes.CategoryController.index());
    }

    public Result delete(Http.Request request, Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        if (category != null) {
            // Delete associated image file
            if (category.getImage() != null && !category.getImage().isEmpty()) {
                deleteOldImage(category.getImage());
            }
            category.delete();
        }
        return redirect(routes.CategoryController.index());
    }

    /**
     * Handle image upload and return the path to be stored in database
     */
    private String handleImageUpload(Http.Request request) {
        Http.MultipartFormData<TemporaryFile> body = request.body().asMultipartFormData();
        if (body == null) {
            return null;
        }

        Http.MultipartFormData.FilePart<TemporaryFile> filePart = body.getFile("image");
        if (filePart == null || filePart.getFilename() == null || filePart.getFilename().isEmpty()) {
            return null;
        }

        String originalFilename = filePart.getFilename();
        String fileExtension = getFileExtension(originalFilename);
        
        // Validate file type
        if (!isValidImageFile(fileExtension)) {
            // You might want to add this error to the form instead
            return null;
        }

        try {
            // Create unique filename
            String fileName = System.currentTimeMillis() + "_" + originalFilename.replaceAll("[^a-zA-Z0-9.-]", "_");
            
            // Ensure upload directory exists
            File uploadDir = new File("public/uploads/categories");
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            
            File targetFile = new File(uploadDir, fileName);
            
            // Copy the uploaded file to target location
            Files.copy(filePart.getRef().path(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            
            // Return the web-accessible path
            return "/assets/uploads/categories/" + fileName;
            
        } catch (IOException e) {
            // Log the error
            play.Logger.of(CategoryController.class).error("Error uploading file", e);
            return null;
        }
    }

    /**
     * Delete old image file from filesystem
     */
    private void deleteOldImage(String imagePath) {
        if (imagePath != null && imagePath.startsWith("/assets/uploads/categories/")) {
            String fileName = imagePath.substring("/assets/uploads/categories/".length());
            File oldFile = new File("public/uploads/categories/" + fileName);
            if (oldFile.exists()) {
                oldFile.delete();
            }
        }
    }

    /**
     * Get file extension from filename
     */
    private String getFileExtension(String filename) {
        if (filename == null || filename.lastIndexOf('.') == -1) {
            return "";
        }
        return filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
    }

    /**
     * Validate if the file is a valid image
     */
    private boolean isValidImageFile(String extension) {
        String[] validExtensions = {"jpg", "jpeg", "png", "gif", "bmp", "webp"};
        for (String validExt : validExtensions) {
            if (validExt.equals(extension)) {
                return true;
            }
        }
        return false;
    }
}
