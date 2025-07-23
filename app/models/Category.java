package models;

import io.ebean.*;
import io.ebean.Model;
import io.ebean.Finder;
//import io.ebean.annotation.CreatedTimestamp;
//import io.ebean.annotation.UpdatedTimestamp;
import java.time.LocalDateTime;

import jakarta.persistence.*;

import play.data.validation.Constraints;

@Entity
public class Category extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

//  @Constraints.Required(message = "Name is required")
    @Column(nullable = false, length = 250)
    public String name;

    @Column(length = 255)
    public String description;

    @Column(unique = true, nullable = false, length = 50)
    public String code;
    @Column(name = "image")
    public String image;

//    @CreatedTimestamp
//    private LocalDateTime createdAt;

//    @UpdatedTimestamp
//    private LocalDateTime updatedAt;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }


    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

//    public LocalDateTime getCreatedAt() { return createdAt; }
//    public LocalDateTime getUpdatedAt() { return updatedAt; }

//
//
//    @Id
//    public Long id;
//
//    @NotNull
//    @Constraints.Required(message = "Name is required")
//    @Constraints.MinLength(value = 3, message = "Name must be at least 3 characters long")
//    public String name;
//
//    @NotNull
//    @Constraints.Required(message = "Category Code is required")
//    @Constraints.MinLength(value = 2, message = "Code must be at least 2 characters long")
//    public String code;
//
//
//    public String description;
//
//
//    // Getters and setters
//    public Long getId() {
//        return id;
//    }
//    public void setId(Long id) {
//        this.id = id;
//    }
//    public String getName() {
//        return name;
//    }
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getCode() {
//        return code;
//    }
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }



    public static final Finder<Long, Category> find = new Finder<>(Category.class);

    public static Category findById(Long id) {
        return find.byId(id);
    }
}
