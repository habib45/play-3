import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Manual test to verify image upload functionality
 * Run this to check if the upload directory is properly configured
 */
public class ManualImageUploadTest {
    
    public static void main(String[] args) {
        System.out.println("=== Manual Image Upload Test ===");
        
        // Test 1: Check upload directory
        testUploadDirectory();
        
        // Test 2: Test file operations
        testFileOperations();
        
        // Test 3: Test file validation
        testFileValidation();
        
        System.out.println("=== Test Complete ===");
    }
    
    private static void testUploadDirectory() {
        System.out.println("\n1. Testing Upload Directory:");
        
        File uploadDir = new File("public/uploads/categories");
        System.out.println("   Upload directory path: " + uploadDir.getAbsolutePath());
        System.out.println("   Directory exists: " + uploadDir.exists());
        System.out.println("   Directory is writable: " + uploadDir.canWrite());
        System.out.println("   Directory is readable: " + uploadDir.canRead());
        
        if (!uploadDir.exists()) {
            boolean created = uploadDir.mkdirs();
            System.out.println("   Created directory: " + created);
        }
        
        // List files in directory
        File[] files = uploadDir.listFiles();
        System.out.println("   Files in directory: " + (files != null ? files.length : 0));
        if (files != null) {
            for (File file : files) {
                System.out.println("     - " + file.getName());
            }
        }
    }
    
    private static void testFileOperations() {
        System.out.println("\n2. Testing File Operations:");
        
        try {
            // Create a test file
            File uploadDir = new File("public/uploads/categories");
            File testFile = new File(uploadDir, "test_" + System.currentTimeMillis() + ".txt");
            
            // Write test content
            String testContent = "This is a test file for upload functionality";
            Files.write(testFile.toPath(), testContent.getBytes());
            
            System.out.println("   Created test file: " + testFile.getName());
            System.out.println("   File size: " + testFile.length() + " bytes");
            System.out.println("   File exists: " + testFile.exists());
            
            // Read back content
            String readContent = new String(Files.readAllBytes(testFile.toPath()));
            System.out.println("   Content matches: " + testContent.equals(readContent));
            
            // Clean up
            boolean deleted = testFile.delete();
            System.out.println("   Cleaned up test file: " + deleted);
            
        } catch (IOException e) {
            System.out.println("   Error during file operations: " + e.getMessage());
        }
    }
    
    private static void testFileValidation() {
        System.out.println("\n3. Testing File Validation:");
        
        // Test valid extensions
        String[] validExtensions = {"jpg", "jpeg", "png", "gif", "bmp", "webp"};
        String[] testFilenames = {
            "test.jpg", "test.JPEG", "image.png", "photo.gif", 
            "picture.bmp", "modern.webp", "invalid.txt", "document.pdf"
        };
        
        for (String filename : testFilenames) {
            boolean isValid = isValidImageFile(getFileExtension(filename));
            System.out.println("   " + filename + " -> " + (isValid ? "VALID" : "INVALID"));
        }
        
        // Test file size limits
        System.out.println("\n   File Size Limits:");
        long[] fileSizes = {1024, 1024*1024, 3*1024*1024, 6*1024*1024}; // 1KB, 1MB, 3MB, 6MB
        long maxSize = 5 * 1024 * 1024; // 5MB limit
        
        for (long size : fileSizes) {
            boolean withinLimit = size <= maxSize;
            System.out.println("   " + formatFileSize(size) + " -> " + (withinLimit ? "ALLOWED" : "TOO LARGE"));
        }
    }
    
    private static String getFileExtension(String filename) {
        if (filename == null || filename.lastIndexOf('.') == -1) {
            return "";
        }
        return filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
    }
    
    private static boolean isValidImageFile(String extension) {
        String[] validExtensions = {"jpg", "jpeg", "png", "gif", "bmp", "webp"};
        for (String validExt : validExtensions) {
            if (validExt.equals(extension)) {
                return true;
            }
        }
        return false;
    }
    
    private static String formatFileSize(long bytes) {
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1024 * 1024) return (bytes / 1024) + " KB";
        return (bytes / (1024 * 1024)) + " MB";
    }
}