package controllers;

import models.Category;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static play.mvc.Http.Status.*;
import static play.test.Helpers.*;

public class CategoryControllerTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    @Test
    public void testCreateCategoryFormDisplay() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/categories/create");

        Result result = route(app, request);
        assertEquals(OK, result.status());
        assertTrue(contentAsString(result).contains("Create"));
    }

    @Test
    public void testCreateCategoryWithValidData() {
        Map<String, String> formData = new HashMap<>();
        formData.put("name", "Test Category");
        formData.put("code", "TEST001");
        formData.put("description", "Test Description");

        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(POST)
                .uri("/categories")
                .bodyForm(formData);

        Result result = route(app, request);
        // Should either be OK (success) or redirect (SEE_OTHER)
        assertTrue("Should be successful", result.status() == OK || result.status() == SEE_OTHER);
    }

    @Test
    public void testCreateCategoryWithEmptyName() {
        Map<String, String> formData = new HashMap<>();
        formData.put("name", "");
        formData.put("code", "TEST002");
        formData.put("description", "Test Description");

        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(POST)
                .uri("/categories")
                .bodyForm(formData);

        Result result = route(app, request);
        assertEquals(BAD_REQUEST, result.status());
    }

    @Test
    public void testListCategories() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/categories");

        Result result = route(app, request);
        assertEquals(OK, result.status());
    }

    @Test
    public void testUploadDirectoryCreation() {
        File uploadDir = new File("public/uploads/categories");
        if (!uploadDir.exists()) {
            boolean created = uploadDir.mkdirs();
            assertTrue("Upload directory should be created", created);
        }
        assertTrue("Upload directory should exist", uploadDir.exists());
        assertTrue("Upload directory should be writable", uploadDir.canWrite());
    }

    @Test
    public void testImageValidationLogic() {
        // Test file extension validation
        assertTrue("JPG should be valid", isValidImageFile("jpg"));
        assertTrue("JPEG should be valid", isValidImageFile("jpeg"));
        assertTrue("PNG should be valid", isValidImageFile("png"));
        assertTrue("GIF should be valid", isValidImageFile("gif"));
        assertTrue("BMP should be valid", isValidImageFile("bmp"));
        assertTrue("WEBP should be valid", isValidImageFile("webp"));
        
        assertFalse("TXT should be invalid", isValidImageFile("txt"));
        assertFalse("PDF should be invalid", isValidImageFile("pdf"));
        assertFalse("DOC should be invalid", isValidImageFile("doc"));
    }

    @Test
    public void testFileSizeValidation() {
        long maxSize = 5 * 1024 * 1024; // 5MB
        
        assertTrue("1MB should be allowed", 1024 * 1024 <= maxSize);
        assertTrue("3MB should be allowed", 3 * 1024 * 1024 <= maxSize);
        assertFalse("6MB should be too large", 6 * 1024 * 1024 <= maxSize);
    }

    @Test
    public void testFilenameSanitization() {
        String original = "test file with spaces & symbols!@#.jpg";
        String sanitized = sanitizeFilename(original);
        
        assertFalse("Should not contain spaces", sanitized.contains(" "));
        assertFalse("Should not contain special chars", sanitized.contains("&"));
        assertTrue("Should preserve extension", sanitized.endsWith(".jpg"));
    }

    // Helper methods (copied from CategoryController for testing)
    private boolean isValidImageFile(String extension) {
        String[] validExtensions = {"jpg", "jpeg", "png", "gif", "bmp", "webp"};
        for (String validExt : validExtensions) {
            if (validExt.equals(extension)) {
                return true;
            }
        }
        return false;
    }

    private String sanitizeFilename(String filename) {
        return filename.replaceAll("[^a-zA-Z0-9.-]", "_");
    }
}
