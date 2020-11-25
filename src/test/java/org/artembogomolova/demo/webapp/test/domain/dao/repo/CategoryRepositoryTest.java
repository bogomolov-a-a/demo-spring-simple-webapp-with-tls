package org.artembogomolova.demo.webapp.test.domain.dao.repo;

import java.util.ArrayList;
import java.util.List;
import org.artembogomolova.demo.webapp.dao.repo.ICategoryRepository;
import org.artembogomolova.demo.webapp.domain.business.Category;
import org.artembogomolova.demo.webapp.test.domain.DomainTestUtil;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumnConstraint.UniqueMultiColumnConstraintColumns;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

public class CategoryRepositoryTest extends AbstractDaoTest<Category> {

  @Autowired
  private ICategoryRepository categoryRepository;

  protected CategoryRepositoryTest() {
    super(Category.class);
  }

  @Override
  protected CrudRepository<Category, Long> getCrudRepository() {
    return categoryRepository;
  }

  @Override
  protected List<Category> generateEntities() {
    List<Category> result = new ArrayList<>();
    Category category1 = DomainTestUtil.buildCategory1();
    result.add(category1);
    Category category2 = DomainTestUtil.buildCategory2();
    result.add(category2);
    Category category11 = new Category();
    category11.setName("Category11");
    result.add(category11);
    return result;
  }

  @Override
  protected Category doDuplicateDeniedTestEntity(UniqueMultiColumnConstraintColumns columns) {
    return null;
  }

  @Override
  protected List<Category> updateEntities(List<Category> savedCollection) {
    savedCollection.stream()
        .filter(x -> x.getName()
            .equals("Category11"))
        .findFirst()
        .get()
        .setParentCategoryId(
            savedCollection.stream()
                .filter(y -> y.getName()
                    .equals("Category1"))
                .findFirst()
                .get()
                .getId());
    return savedCollection;
  }
}
