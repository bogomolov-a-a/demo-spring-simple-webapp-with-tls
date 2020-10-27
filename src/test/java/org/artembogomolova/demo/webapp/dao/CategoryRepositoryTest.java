package org.artembogomolova.demo.webapp.dao;

import java.util.ArrayList;
import java.util.Collection;
import org.artembogomolova.demo.webapp.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

public class CategoryRepositoryTest extends AbstractDaoTest<Category>{

  @Autowired
  private ICategoryRepository categoryRepository;



  @Override
  protected CrudRepository<Category,Long> getCrudRepository() {
    return categoryRepository;
  }

  @Override
  protected Collection<Category> generateEntities() {
    Collection<Category> result=new ArrayList<>();
    Category category1 = RepositoryTestUtil.buildCategory1();
    result.add(category1);
    Category category2 = RepositoryTestUtil.buildCategory2();
    result.add(category2);
    Category category11 = new Category();
    category11.setName("Category11");
    result.add(category11);
    return result;
  }
  @Override
  protected Collection<Category> updateEntities(Collection<Category> savedCollection) {
    savedCollection.stream().filter(x->x.getName().equals("Category11")).findFirst().get().setParentCategoryId(
        savedCollection.stream().filter(y->y.getName().equals("Category1")).findFirst().get().getId());
    return savedCollection;
  }
}
