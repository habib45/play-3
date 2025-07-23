# Play Framework Category Management with Image Upload

This Play Framework application provides category management functionality with image upload capabilities.

## Features

- Create, read, update, and delete categories
- Upload and manage category images
- Images are stored in the `public/uploads/categories/` directory
- Automatic image validation (supports jpg, jpeg, png, gif, bmp, webp)
- Image cleanup when categories are deleted or updated

## Image Upload Functionality

### Supported Image Formats
- JPG/JPEG
- PNG
- GIF
- BMP
- WebP

### Image Storage
- Images are stored in `public/uploads/categories/`
- Images are accessible via `/assets/uploads/categories/` URL path
- Unique filenames are generated using timestamp + original filename

### Features
- **Create Category**: Upload image during category creation
- **Update Category**: Replace existing image or keep current one
- **Delete Category**: Automatically removes associated image file
- **Image Validation**: Only allows valid image file types
- **File Safety**: Sanitizes filenames to prevent security issues

## Usage

1. **Create Category**: Navigate to `/categories/create` and fill the form with category details and upload an image
2. **View Categories**: Navigate to `/categories` to see all categories with their images
3. **Edit Category**: Click edit button to modify category details and optionally update the image
4. **Delete Category**: Click delete button to remove category and its associated image

## Technical Details

### Controller Methods
- `save()`: Handles category creation with image upload
- `update()`: Handles category updates with optional image replacement
- `delete()`: Handles category deletion with image cleanup
- `handleImageUpload()`: Private method for processing image uploads
- `deleteOldImage()`: Private method for cleaning up old image files

### Security Features
- File type validation
- Filename sanitization
- Directory traversal protection
- CSRF protection on forms

## Directory Structure
```
public/
  uploads/
    categories/
      .gitkeep          # Ensures directory is tracked in git
      [uploaded-images] # Actual uploaded images (ignored by git)
```