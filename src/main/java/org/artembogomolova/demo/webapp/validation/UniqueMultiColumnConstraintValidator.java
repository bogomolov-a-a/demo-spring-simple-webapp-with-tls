package org.artembogomolova.demo.webapp.validation;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.artembogomolova.demo.webapp.domain.IdentifiedEntity;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumn.UniqueMultiColumnConstraint;
import org.artembogomolova.demo.webapp.validation.base.AbstractApplicationContextConstraintValidator;
import org.springframework.beans.BeanUtils;
import org.springframework.data.repository.CrudRepository;

@Slf4j
public class UniqueMultiColumnConstraintValidator extends AbstractApplicationContextConstraintValidator<UniqueMultiColumn, IdentifiedEntity> {

  private Iterable<? extends IdentifiedEntity> allEntities;
  private List<UniqueMultiColumnConstraint> constraintList;

  private Class<?> clazzType;

  @Override
  public void initialize(UniqueMultiColumn constraintAnnotation) {
    clazzType = constraintAnnotation.repository();
    constraintList = Arrays.asList(constraintAnnotation.constraints());
  }

  @Override
  public boolean isValid(IdentifiedEntity identifiedEntity, ConstraintValidatorContext constraintValidatorContext) {
    constraintValidatorContext.disableDefaultConstraintViolation();
    CrudRepository crudRepository = (CrudRepository) applicationContext.getBean(clazzType);
    allEntities = crudRepository.findAll();
    return hasNoDuplicate(identifiedEntity, constraintValidatorContext);
  }

  private boolean hasNoDuplicate(IdentifiedEntity identifiedEntity, ConstraintValidatorContext constraintValidatorContext) {
    for (UniqueMultiColumnConstraint constraint : constraintList) {
      List<String> constraintColumnNames = Arrays.asList(constraint.columnNames());
      if (!hasNoDuplicateByConstraint(identifiedEntity, constraintColumnNames)) {
        String message = messageSource.getMessage(UniqueMultiColumn.VIOLATION_MESSAGE_TEMPLATE,
            new Object[]{identifiedEntity.toString(), constraintColumnNames.toString()}, Locale.getDefault());
        constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        return false;
      }
    }
    return true;
  }

  private boolean hasNoDuplicateByConstraint(IdentifiedEntity identifiedEntity, List<String> constraintColumnNames) {
    Class<? extends IdentifiedEntity> clazz = identifiedEntity.getClass();
    Map<String, Object> uniqueNewValues = fillValueMap(identifiedEntity, clazz, constraintColumnNames);
    for (IdentifiedEntity entity : allEntities) {
      Map<String, Object> existsValues = fillValueMap(entity, clazz, constraintColumnNames);
      if (equalsValues(uniqueNewValues, existsValues) && !Objects.equals(identifiedEntity.getId(), entity.getId())) {
        return false;
      }
    }
    return true;
  }

  private boolean equalsValues(Map<String, Object> uniqueNewValues, Map<String, Object> existsValues) {
    List<Entry<String, Object>> newValuesList = new ArrayList<>(uniqueNewValues.entrySet());
    Collections.sort(newValuesList, Map.Entry.comparingByKey());
    log.debug("new values: {}", newValuesList);
    List<Entry<String, Object>> existsValuesList = new ArrayList<>(existsValues.entrySet());
    Collections.sort(existsValuesList, Map.Entry.comparingByKey());
    log.debug("exists values: {}", existsValuesList);
    return Objects.equals(existsValuesList, newValuesList);
  }

  private Map<String, Object> fillValueMap(IdentifiedEntity identifiedEntity,
      Class<? extends IdentifiedEntity> clazz,
      List<String> constraintColumnNames) {
    Map<String, Object> result = new HashMap<>();
    constraintColumnNames.forEach(x -> {
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
