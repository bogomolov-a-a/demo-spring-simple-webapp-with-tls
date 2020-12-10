package org.artembogomolova.demo.webapp.test.domain.db.dao.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.artembogomolova.demo.webapp.dao.repo.business.ICategoryRepository;
import org.artembogomolova.demo.webapp.domain.business.Category;
import org.artembogomolova.demo.webapp.test.domain.DomainTestUtil;
import org.artembogomolova.demo.webapp.test.domain.db.dao.AbstractDaoTest;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumn.UniqueMultiColumnConstraint;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

@DisplayName("Category entity repository test")
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
  protected Map<String, Object> buildCommonFieldValues(UniqueMultiColumnConstraint uniqueMultiColumnConstraint) {
    return null;
  }

  @Override
  protected Category doPrepareDeniedTestEntity(UniqueMultiColumnConstraint uniqueMultiColumnConstraint,
      Map<String, Object> commonValues) {
    return null;
  }

  @Override
  protected Category doDuplicateDeniedTestEntity(UniqueMultiColumnConstraint columns, Map<String, Object> commonValues) {
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
