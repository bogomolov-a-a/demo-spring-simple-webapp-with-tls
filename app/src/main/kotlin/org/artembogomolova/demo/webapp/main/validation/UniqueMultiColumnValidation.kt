package org.artembogomolova.demo.webapp.main.validation

import org.artembogomolova.demo.webapp.main.domain.core.IdentifiedEntity
import org.artembogomolova.demo.webapp.main.util.getLogger
import org.springframework.beans.BeanUtils
import org.springframework.data.repository.CrudRepository
import java.lang.reflect.InvocationTargetException
import java.util.*
import javax.validation.Constraint
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import javax.validation.ValidationException
import kotlin.reflect.KClass

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
    @Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
    @Retention(
        AnnotationRetention.RUNTIME
    )
    annotation class UniqueMultiColumnConstraint(val name: String, val columnNames: Array<String>)
    companion object {
        const val VIOLATION_MESSAGE_TEMPLATE = "org.artembogomolova.demo.webapp.dao.validation.unique"
    }
}

class UniqueMultiColumnConstraintValidator : AbstractApplicationContextConstraintValidator<UniqueMultiColumn, IdentifiedEntity<Any>?>() {
    companion object {
        private val log = getLogger(UniqueMultiColumnConstraintValidator::class.java)
    }

    private val allEntities: MutableList<IdentifiedEntity<Any>> = mutableListOf()
    private val constraintList: MutableList<UniqueMultiColumn.UniqueMultiColumnConstraint> = mutableListOf()
    private lateinit var clazzType: Class<*>

    override fun initialize(constraintAnnotation: UniqueMultiColumn) {
        clazzType = constraintAnnotation.repository.java
        constraintList.addAll(constraintAnnotation.constraints)
    }

    override fun isValid(identifiedEntity: IdentifiedEntity<Any>?, constraintValidatorContext: ConstraintValidatorContext): Boolean {
        constraintValidatorContext.disableDefaultConstraintViolation()
        val crudRepository = context.getBean(clazzType) as CrudRepository<*, *>
        crudRepository.findAll().forEach {
            @Suppress("UNCHECKED_CAST") val entity = it as IdentifiedEntity<Any>
            allEntities.add(entity)
        }
        return hasNoDuplicate(identifiedEntity!!, constraintValidatorContext)
    }

    private fun hasNoDuplicate(identifiedEntity: IdentifiedEntity<Any>, constraintValidatorContext: ConstraintValidatorContext): Boolean {
        for (constraint in constraintList) {
            val constraintColumnNames = constraint.columnNames
            if (!hasNoDuplicateByConstraint(identifiedEntity, constraintColumnNames)) {
                val message: String = messageLocalSource.getMessage(
                    UniqueMultiColumn.VIOLATION_MESSAGE_TEMPLATE,
                    arrayOf<Any>(identifiedEntity.toString(), constraintColumnNames.toString()),
                    Locale.getDefault()
                )
                constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation()
                return false
            }
        }
        return true
    }

    private fun hasNoDuplicateByConstraint(identifiedEntity: IdentifiedEntity<Any>, constraintColumnNames: Array<String>): Boolean {
        val clazz: Class<out IdentifiedEntity<Any>?> = identifiedEntity.javaClass
        val uniqueNewValues = fillValueMap(identifiedEntity, clazz, constraintColumnNames)
        for (entity in allEntities) {
            val existsValues = fillValueMap(entity, clazz, constraintColumnNames)
            if (isDuplicateEntity(Pair(entity, existsValues), Pair(identifiedEntity, uniqueNewValues))) {
                return false
            }
        }
        return true
    }

    private fun isDuplicateEntity(
        existEntityInfo: Pair<IdentifiedEntity<Any>, Map<String, Any?>>,
        newEntityInfo: Pair<IdentifiedEntity<Any>, Map<String, Any?>>,
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
        identifiedEntity: IdentifiedEntity<Any>?,
        clazz: Class<out IdentifiedEntity<Any>?>,
        constraintColumnNames: Array<String>,
    ): Map<String, Any?> {
        val result: MutableMap<String, Any?> = HashMap()
        constraintColumnNames.forEach { x -> result[x] = addConstraintColumnValue(x, identifiedEntity, clazz) }
        return result
    }

    private fun addConstraintColumnValue(
        columnName: String, identifiedEntity: IdentifiedEntity<Any>?,
        clazz: Class<out IdentifiedEntity<Any>?>,
    ): Any? = try {
        BeanUtils.getPropertyDescriptor(clazz, columnName)?.readMethod?.invoke(identifiedEntity)
    } catch (e: IllegalAccessException) {
        throw ValidationException(e)
    } catch (e: InvocationTargetException) {
        throw ValidationException(e.targetException)
    }
}