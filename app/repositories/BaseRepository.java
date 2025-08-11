package repositories;

import models.Category;
import java.util.List;
import java.util.Optional;

public interface BaseRepository {

    List<Category> findAll();

    Optional<Category> findById(Long id);

    boolean save(Category category);

    void update(Category category);

    void delete(Long id);
}