package validators;

import models.Category;
import java.util.HashMap;
import java.util.Map;

public class CategoryValidator {

    public Map<String, String> validate(Category category) {
        Map<String, String> errors = new HashMap<>();

        if (category.getName() == null || category.getName().trim().isEmpty()) {
            errors.put("name", "Name is required");
        }
        if (category.getCode() == null || category.getCode().trim().isEmpty()) {
            errors.put("code", "Code is required");
        } else if (category.getCode().length() > 50) {
            errors.put("code", "Code cannot exceed 50 characters");
        }
        return errors;
    }
}
