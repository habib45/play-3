package controllers;

/**
 * Result class for image upload operations
 */
public class ImageUploadResult {
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
        return errorMessage != null && !errorMessage.isEmpty();
    }
    
    public boolean isSuccess() {
        return !hasError() && imagePath != null;
    }
}