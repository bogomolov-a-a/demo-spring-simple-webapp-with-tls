package org.artembogomolova.demo.webapp.main.validation

import java.lang.reflect.InvocationTargetException
import java.util.Collections
import java.util.Locale
import javax.validation.Constraint
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import javax.validation.ValidationException
import kotlin.reflect.KClass
import org.artembogomolova.demo.webapp.main.domain.core.IdentifiedEntity
import org.artembogomolova.demo.webapp.main.util.getLogger
import org.springframework.beans.BeanUtils
import org.springframework.data.repository.CrudRepository

@MustBeDocumented
@Constraint(validatedBy = [UniqueMultiColumnConstraintValidator::class])
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@Retention(
    AnnotationRetention.RUNTIME
)
annotation class UniqueMultiColumn(
    val message: String = VIOLATION_MESSAGE_TEMPLATE,
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val constraints: Array<UniqueMultiColumnConstraint>,
    val repository: KClass<*>,
) {
    @MustBeDocumented
    @Target()
    @Retention(
        AnnotationRetention.RUNTIME
    )
    annotation class UniqueMultiColumnConstraint(val name: String, val columnNames: Array<String>)
    companion object {
        const val VIOLATION_MESSAGE_TEMPLATE = "org.artembogomolova.demo.webapp.main.validation.unique"
    }
}

class UniqueMultiColumnConstraintValidator : AbstractApplicationContextConstraintValidator<UniqueMultiColumn, IdentifiedEntity<*>>() {
    companion object {
        private val log = getLogger(UniqueMultiColumnConstraintValidator::class.java)
    }

    private val constraintList: MutableList<UniqueMultiColumn.UniqueMultiColumnConstraint> = mutableListOf()
    private lateinit var clazzType: Class<*>

    override fun initialize(constraintAnnotation: UniqueMultiColumn) {
        clazzType = constraintAnnotation.repository.java
        constraintList.addAll(constraintAnnotation.constraints)
    }

    override fun isValid(identifiedEntity: IdentifiedEntity<*>, constraintValidatorContext: ConstraintValidatorContext): Boolean {
        constraintValidatorContext.disableDefaultConstraintViolation()
        val crudRepository = context.getBean(clazzType) as CrudRepository<*, *>
        val existsEntities = mutableListOf<IdentifiedEntity<*>>()
        crudRepository.findAll().forEach {
            @Suppress("UNCHECKED_CAST") val entity = it as IdentifiedEntity<Any>
            existsEntities.add(entity)
        }
        return hasNoDuplicate(identifiedEntity, constraintValidatorContext, existsEntities)
    }

    private fun hasNoDuplicate(
        identifiedEntity: IdentifiedEntity<*>,
        constraintValidatorContext: ConstraintValidatorContext,
        existsEntities: MutableList<IdentifiedEntity<*>>
    ): Boolean {
        for (constraint in constraintList) {
            val constraintColumnNames = constraint.columnNames
            if (!hasNoDuplicateByConstraint(identifiedEntity, constraintColumnNames, existsEntities)) {
                val message: String = messageLocalSource.getMessage(
                    UniqueMultiColumn.VIOLATION_MESSAGE_TEMPLATE,
                    arrayOf<Any>(identifiedEntity.toString(), constraintColumnNames.contentToString()),
                    Locale.getDefault()
                )
                constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation()
                return false
            }
        }
        return true
    }

    private fun hasNoDuplicateByConstraint(
        identifiedEntity: IdentifiedEntity<*>,
        constraintColumnNames: Array<String>,
        existsEntities: MutableList<IdentifiedEntity<*>>
    ): Boolean {
        val clazz: Class<out IdentifiedEntity<*>> = identifiedEntity.javaClass
        val uniqueNewValues = fillValueMap(identifiedEntity, clazz, constraintColumnNames)
        for (entity in existsEntities) {
            val existsValues = fillValueMap(entity, clazz, constraintColumnNames)
            if (isDuplicateEntity(Pair(entity, existsValues), Pair(identifiedEntity, uniqueNewValues))) {
                return false
            }
        }
        return true
    }

    private fun isDuplicateEntity(
        existEntityInfo: Pair<IdentifiedEntity<*>, Map<String, Any?>>,
        newEntityInfo: Pair<IdentifiedEntity<*>, Map<String, Any?>>,
    ): Boolean = equalsValues(newEntityInfo.second, existEntityInfo.second) && existEntityInfo.first.id != newEntityInfo.first.id


    private fun equalsValues(uniqueNewValues: Map<String, Any?>, existsValues: Map<String, Any?>): Boolean {
        val newValuesList: List<Map.Entry<String, Any?>> = ArrayList(uniqueNewValues.entries)
        Collections.sort(newValuesList, java.util.Map.Entry.comparingByKey())
        log.debug("new values: {}", newValuesList)
        val existsValuesList: List<Map.Entry<String, Any?>> = ArrayList(existsValues.entries)
        Collections.sort(existsValuesList, java.util.Map.Entry.comparingByKey())
        log.debug("exists values: {}", existsValuesList)
        return existsValuesList == newValuesList
    }

    private fun fillValueMap(
        identifiedEntity: IdentifiedEntity<*>,
        clazz: Class<out IdentifiedEntity<*>>,
        constraintColumnNames: Array<String>,
    ): Map<String, Any?> {
        val result: MutableMap<String, Any?> = HashMap()
        constraintColumnNames.forEach { x -> result[x] = addConstraintColumnValue(x, identifiedEntity, clazz) }
        return result
    }

    private fun addConstraintColumnValue(
        columnName: String, identifiedEntity: IdentifiedEntity<*>,
        clazz: Class<out IdentifiedEntity<*>>,
    ): Any? = try {
        BeanUtils.getPropertyDescriptor(clazz, columnName)?.readMethod?.invoke(identifiedEntity)
    } catch (e: IllegalAccessException) {
        throw ValidationException(e)
    } catch (e: InvocationTargetException) {
        throw ValidationException(e.targetException)
    }
}