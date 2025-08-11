error id: file://<WORKSPACE>/app/repositories/ProductRepository.scala:
file://<WORKSPACE>/app/repositories/ProductRepository.scala
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 531
uri: file://<WORKSPACE>/app/repositories/ProductRepository.scala
text:
```scala
package repositories;

//import io.ebean.Ebean;
import io.ebean.*;
import models.Product;

import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;

@Singleton
public class ProductRepository implements BaseRepository {

    private final Finder<Long, Product> finder = new Finder<>(Product.class);

    @Override
    public List<Product> findAll() {
        return finder.all();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(finder.byId(id));
    }@@

    @Override
    public void save(Product Product) {
        Product.save();
    }

    @Override
    public void update(Product Product) {
        Product.update();
    }

    @Override
    public void delete(Long id) {
        finder.deleteById(id);
    }
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: 