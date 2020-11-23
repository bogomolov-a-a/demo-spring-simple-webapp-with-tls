package org.artembogomolova.demo.webapp.validation;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import org.artembogomolova.demo.webapp.domain.core.IdentifiedEntity;
import org.artembogomolova.demo.webapp.validation.base.AbstractApplicationContextConstraintValidator;
import org.springframework.beans.BeanUtils;
import org.springframework.data.repository.CrudRepository;

public class UniqueMultiColumnConstraintValidator extends AbstractApplicationContextConstraintValidator<UniqueMultiColumnConstraint, IdentifiedEntity> {

  private Iterable<? extends IdentifiedEntity> allEntities;
  private List<String> columnList;

  private Class<?> clazzType;

  @Override
  public void initialize(UniqueMultiColumnConstraint constraintAnnotation) {
    clazzType = constraintAnnotation.repository();
    columnList = Arrays.asList(constraintAnnotation.columnList());
  }

  @Override
  public boolean isValid(IdentifiedEntity identifiedEntity, ConstraintValidatorContext constraintValidatorContext) {
    constraintValidatorContext.disableDefaultConstraintViolation();
    CrudRepository crudRepository = (CrudRepository) applicationContext.getBean(clazzType);
    allEntities = crudRepository.findAll();
    return hasNoDuplicate(identifiedEntity);
  }

  private boolean hasNoDuplicate(IdentifiedEntity identifiedEntity) {
    Class<? extends IdentifiedEntity> clazz = identifiedEntity.getClass();
    Map<String, Object> uniqueNewValues = fillValueMap(identifiedEntity, clazz);
    for (IdentifiedEntity entity : allEntities) {
      Map<String, Object> existsValues = fillValueMap(entity, clazz);
      if (equalsValues(uniqueNewValues, existsValues)) {
        return false;
      }
    }
    return true;
  }

  private boolean equalsValues(Map<String, Object> uniqueNewValues, Map<String, Object> existsValues) {
    List<Entry<String, Object>> newValuesList = new ArrayList<>(uniqueNewValues.entrySet());
    Collections.sort(newValuesList, Map.Entry.comparingByKey());
    List<Entry<String, Object>> existsValuesList = new ArrayList<>(existsValues.entrySet());
    Collections.sort(existsValuesList, Map.Entry.comparingByKey());
    return Objects.equals(existsValuesList, newValuesList);
  }

  private Map<String, Object> fillValueMap(IdentifiedEntity identifiedEntity,
      Class<? extends IdentifiedEntity> clazz) {
    Map<String, Object> result = new HashMap<>();
    columnList.forEach(x -> {
      try {
        result.put(x,
            BeanUtils.getPropertyDescriptor(clazz, x).getReadMethod().invoke(identifiedEntity));
      } catch (IllegalAccessException e) {
        throw new ValidationException(e);
      } catch (InvocationTargetException e) {
        throw new ValidationException(e.getTargetException());
      }
    });
    return result;
  }


}
