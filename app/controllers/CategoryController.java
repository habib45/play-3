package controllers;

import models.Category;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.mvc.*;
import repositories.CategoryRepository;
import javax.inject.Inject;
import java.util.List;
import java.nio.file.Paths;
import play.libs.Files.TemporaryFile;
import java.io.File;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CategoryController extends Controller {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
    private final FormFactory formFactory;
    private final MessagesApi messagesApi;
    private final CategoryRepository categoryRepository;

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
            logger.warn("Form validation errors: {}", categoryForm.errors());
            return badRequest(
                    views.html.category.create.render(categoryForm, request, messagesApi.preferred(request))
            );
        }
        
        Category categoryInfo = categoryForm.get();
        
        // Additional validation
        if (categoryInfo.getName() == null || categoryInfo.getName().trim().isEmpty()) {
            categoryForm = categoryForm.withError("name", "Category name cannot be empty");
            return badRequest(
                    views.html.category.create.render(categoryForm, request, messagesApi.preferred(request))
            );
        }

        // Check if category code already exists
        List<Category> existingCategories = categoryRepository.findAll();
        for (Category existing : existingCategories) {
            if (existing.getCode().equals(categoryInfo.getCode())) {
                categoryForm = categoryForm.withError("code", "Category code already exists");
                return badRequest(
                        views.html.category.create.render(categoryForm, request, messagesApi.preferred(request))
                );
            }
        }

        try {
            // Handle file upload
            ImageUploadResult uploadResult = handleImageUpload(request);
            if (uploadResult.hasError()) {
                categoryForm = categoryForm.withError("image", uploadResult.getErrorMessage());
                return badRequest(
                        views.html.category.create.render(categoryForm, request, messagesApi.preferred(request))
                );
            }
            
            if (uploadResult.getImagePath() != null) {
                categoryInfo.setImage(uploadResult.getImagePath());
            }

            categoryRepository.save(categoryInfo);
            logger.info("Category created successfully: {}", categoryInfo.getName());
            return redirect(routes.CategoryController.index()).flashing("success", "Category created successfully!");
            
        } catch (Exception e) {
            logger.error("Error saving category", e);
            categoryForm = categoryForm.withError("", "An error occurred while saving the category");
            return badRequest(
                    views.html.category.create.render(categoryForm, request, messagesApi.preferred(request))
            );
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
                .orElse(null);

        if (existingCategory == null) {
            return notFound("Category not found");
        }

        try {
            // Update basic fields
            existingCategory.setName(updatedCategory.getName());
            existingCategory.setDescription(updatedCategory.getDescription());
            existingCategory.setCode(updatedCategory.getCode());

            // Handle image upload for update
            ImageUploadResult uploadResult = handleImageUpload(request);
            if (uploadResult.hasError()) {
                categoryForm = categoryForm.withError("image", uploadResult.getErrorMessage());
                return badRequest(views.html.category.edit.render(categoryForm, id, request, messagesApi.preferred(request)));
            }
            
            if (uploadResult.getImagePath() != null) {
                // Delete old image if exists
                if (existingCategory.getImage() != null && !existingCategory.getImage().isEmpty()) {
                    deleteOldImage(existingCategory.getImage());
                }
                existingCategory.setImage(uploadResult.getImagePath());
            }

            categoryRepository.update(existingCategory);
            logger.info("Category updated successfully: {}", existingCategory.getName());
            return redirect(routes.CategoryController.index()).flashing("success", "Category updated successfully!");
            
        } catch (Exception e) {
            logger.error("Error updating category", e);
            categoryForm = categoryForm.withError("", "An error occurred while updating the category");
            return badRequest(views.html.category.edit.render(categoryForm, id, request, messagesApi.preferred(request)));
        }
    }

    public Result delete(Http.Request request, Long id) {
        try {
            Category category = categoryRepository.findById(id).orElse(null);
            if (category != null) {
                // Delete associated image file
                if (category.getImage() != null && !category.getImage().isEmpty()) {
                    deleteOldImage(category.getImage());
                }
                category.delete();
                logger.info("Category deleted successfully: {}", category.getName());
                return redirect(routes.CategoryController.index()).flashing("success", "Category deleted successfully!");
            }
            return redirect(routes.CategoryController.index()).flashing("error", "Category not found!");
        } catch (Exception e) {
            logger.error("Error deleting category", e);
            return redirect(routes.CategoryController.index()).flashing("error", "An error occurred while deleting the category");
        }
    }

    /**
     * Handle image upload and return the result with path or error
     */
    private ImageUploadResult handleImageUpload(Http.Request request) {
        Http.MultipartFormData<TemporaryFile> body = request.body().asMultipartFormData();
        if (body == null) {
            return new ImageUploadResult(null, null); // No file uploaded
        }

        Http.MultipartFormData.FilePart<TemporaryFile> filePart = body.getFile("image");
        if (filePart == null || filePart.getFilename() == null || filePart.getFilename().isEmpty()) {
            return new ImageUploadResult(null, null); // No file uploaded
        }

        String originalFilename = filePart.getFilename();
        String fileExtension = getFileExtension(originalFilename);
        
        // Validate file type
        if (!isValidImageFile(fileExtension)) {
            return new ImageUploadResult(null, "Invalid file type. Please upload jpg, jpeg, png, gif, bmp, or webp files only.");
        }

        // Check file size (limit to 5MB)
        try {
            long fileSize = Files.size(filePart.getRef().path());
            if (fileSize > 5 * 1024 * 1024) { // 5MB
                return new ImageUploadResult(null, "File size too large. Maximum allowed size is 5MB.");
            }
        } catch (IOException e) {
            logger.error("Error checking file size", e);
            return new ImageUploadResult(null, "Error processing file.");
        }

        try {
            // Create unique filename
            String fileName = System.currentTimeMillis() + "_" + sanitizeFilename(originalFilename);
            
            // Ensure upload directory exists
            File uploadDir = new File("public/uploads/categories");
            if (!uploadDir.exists()) {
                boolean created = uploadDir.mkdirs();
                if (!created) {
                    logger.error("Failed to create upload directory: {}", uploadDir.getAbsolutePath());
                    return new ImageUploadResult(null, "Failed to create upload directory.");
                }
            }
            
            File targetFile = new File(uploadDir, fileName);
            
            // Copy the uploaded file to target location
            Files.copy(filePart.getRef().path(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            
            logger.info("File uploaded successfully: {}", targetFile.getAbsolutePath());
            
            // Return the web-accessible path
            return new ImageUploadResult("/assets/uploads/categories/" + fileName, null);
            
        } catch (IOException e) {
            logger.error("Error uploading file", e);
            return new ImageUploadResult(null, "Error uploading file: " + e.getMessage());
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
                boolean deleted = oldFile.delete();
                if (deleted) {
                    logger.info("Old image deleted: {}", oldFile.getAbsolutePath());
                } else {
                    logger.warn("Failed to delete old image: {}", oldFile.getAbsolutePath());
                }
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

    /**
     * Sanitize filename to remove special characters
     */
    private String sanitizeFilename(String filename) {
        return filename.replaceAll("[^a-zA-Z0-9.-]", "_");
    }

    /**
     * Inner class to handle image upload results
     */
    private static class ImageUploadResult {
        private final String imagePath;
        private final String errorMessage;

        public ImageUploadResult(String imagePath, String errorMessage) {
            this.imagePath = imagePath;
            this.errorMessage = errorMessage;
        }

        public String getImagePath() {
            return imagePath;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public boolean hasError() {
            return errorMessage != null;
        }
    }
}
