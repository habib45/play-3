package repositories;

//import io.ebean.Ebean;
import io.ebean.*;
import models.Category;

import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;

@Singleton
public class CategoryRepository implements BaseRepository {

    private final Finder<Long, Category> finder = new Finder<>(Category.class);

    @Override
    public List<Category> findAll() {
        return finder.all();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return Optional.ofNullable(finder.byId(id));
    }

    @Override
    public void save(Category category) {
        category.save();
    }

    @Override
    public void update(Category category) {
        category.update();
    }

    @Override
    public void delete(Long id) {
        finder.deleteById(id);
    }
}
