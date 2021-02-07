package org.artembogomolova.demo.webapp.main.domain.core

import java.io.Serializable
import javax.persistence.Basic
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.MappedSuperclass
import javax.persistence.OneToOne
import javax.persistence.Table
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1
import org.artembogomolova.demo.webapp.main.dao.repo.IPersonRepository
import org.artembogomolova.demo.webapp.main.dao.repo.IPhysicalAddressRepository
import org.artembogomolova.demo.webapp.main.domain.auth.User
import org.artembogomolova.demo.webapp.main.validation.UniqueMultiColumn
import org.artembogomolova.demo.webapp.main.validation.ValidCountryCodeAndZipCode

@MappedSuperclass
abstract class IdentifiedEntity<T>(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic
    @Column(columnDefinition = "integer not null primary key autoincrement")
    var id: Long? = null,
) : Serializable {

    abstract fun buildNaturalKey(): Array<KMutableProperty1<T, *>>

    @Transient
    protected val naturalKey: Array<KMutableProperty1<T, *>> = buildNaturalKey()

    companion object {
        private const val serialVersionUID = 1L
        const val NATURAL_KEY_CONSTRAINT_NAME = "naturalKeyConstraint"
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (pointerEquals(other).not()) return false
        return equalsByNaturalKey(other)
    }


    private fun pointerEquals(other: Any): Boolean {
        if (this === other) return true
        return this.javaClass == other.javaClass
    }

    private fun equalsByNaturalKey(other: Any): Boolean {
        for (keyProperty in naturalKey) {
            @Suppress("UNCHECKED_CAST") val otherPropertyValue = keyProperty.get(other as T)
            @Suppress("UNCHECKED_CAST") val thatPropertyValue = keyProperty.get(this as T)
            if (thatPropertyValue != otherPropertyValue) return false
        }
        return true
    }

    override fun hashCode(): Int {
        var result = 1
        val hashCodeCalculator = HashCodeCalculator<T>()
        naturalKey.forEach { keyProperty ->
            @Suppress("UNCHECKED_CAST")
            result += hashCodeCalculator.calculatePropertyHashCode(keyProperty, result, this as T)
        }
        return result
    }

    override fun toString(): String {
        var result = "${this.javaClass.name}("
        naturalKey.forEach { keyProperty ->
            @Suppress("UNCHECKED_CAST")
            result += keyProperty.name + "='" + keyProperty.get(this as T) + "',"
        }
        return "${result.substring(0, result.length - 1)})"
    }

}

private class HashCodeCalculator<T> {
    companion object {
        /*from lombok @EqualsAndHashCode*/
        const val PRIME = 59
        const val STRING_DEFAULT_HASH_CODE = 43
        const val DOUBLE_USHR_BITS_COUNT = 31
    }

    fun calculatePropertyHashCode(keyProperty: KProperty1<T, Any?>, calculatedHashCode: Int, entity: T): Int {
        var result = 0
        val value = keyProperty.get(entity)
        if (value == null) {
            result += calculateNullHashCode(keyProperty)
            return result
        }
        result += calculatedHashCode * PRIME + calculateNonNullHashCode(value)
        return result
    }

    private fun calculateNonNullHashCode(value: Any): Int =
        when {
            (isFloatingPointValue(value)) -> {
                val bits = (value as Double).toBits().toInt()
                calculateFloatingPointHashCode(bits)
            }
            else -> calculateReferencableHashCode(value)
        }

    private fun calculateFloatingPointHashCode(bits: Int): Int = bits xor (bits ushr DOUBLE_USHR_BITS_COUNT)

    private fun calculateReferencableHashCode(value: Any): Int = value.hashCode()


    private fun isFloatingPointValue(value: Any): Boolean = (value is Double)

    private fun calculateNullHashCode(keyProperty: KProperty1<T, Any?>): Int = when {
        (isStringProperty(keyProperty)) -> {
            STRING_DEFAULT_HASH_CODE
        }
        else -> 0
    }

    private fun isStringProperty(keyProperty: KProperty1<T, Any?>): Boolean = keyProperty.returnType == String::class
}

@Entity
@Table(name = "addresses")
@ValidCountryCodeAndZipCode
@UniqueMultiColumn(
    repository = IPhysicalAddressRepository::class,
    constraints = [UniqueMultiColumn.UniqueMultiColumnConstraint(
        name = IdentifiedEntity.NATURAL_KEY_CONSTRAINT_NAME,
        columnNames = [PhysicalAddress_.POSTAL_CODE,
            PhysicalAddress_.COUNTRY_CODE,
            PhysicalAddress_.STATE,
            PhysicalAddress_.CITY,
            PhysicalAddress_.DISTRICT,
            PhysicalAddress_.STREET,
            PhysicalAddress_.HOUSE,
            PhysicalAddress_.ROOM,
            PhysicalAddress_.SPECIFIC_PART
        ]
    )]
)
class PhysicalAddress(
    @field:NotBlank
    @field:Pattern(regexp = ConstraintPatterns.POSTAL_CODE_PATTERN)
    @Column(nullable = false)
    var postalCode: String?,
    @field:NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var countryCode: CountryCode?,
    @field:NotBlank
    @Column(nullable = false)
    var city: String?,
    @field:NotBlank
    @Column(nullable = false)
    var house: String?,
    var state: String? = null,
    var district: String? = null,
    var street: String? = null,
    var room: Int? = null,
    var specificPart: String? = null,
) : IdentifiedEntity<PhysicalAddress>() {

    constructor() : this(null, null, null, null)

    override fun buildNaturalKey(): Array<KMutableProperty1<PhysicalAddress, *>> = arrayOf(
        PhysicalAddress::postalCode,
        PhysicalAddress::countryCode,
        PhysicalAddress::city,
        PhysicalAddress::house,
        PhysicalAddress::state,
        PhysicalAddress::district,
        PhysicalAddress::street,
        PhysicalAddress::room,
        PhysicalAddress::specificPart
    )

    companion object {
        private const val serialVersionUID = 1L
        fun from(copyingEntity: PhysicalAddress): PhysicalAddress = PhysicalAddress(
            postalCode = copyingEntity.postalCode,
            countryCode = copyingEntity.countryCode,
            city = copyingEntity.city,
            house = copyingEntity.house,
            state = copyingEntity.state,
            district = copyingEntity.district,
            street = copyingEntity.street,
            room = copyingEntity.room,
            specificPart = copyingEntity.specificPart
        )
    }
}

@Entity
@Table(name = "persons")
@UniqueMultiColumn(
    repository = IPersonRepository::class,
    constraints = [UniqueMultiColumn.UniqueMultiColumnConstraint(
        name = IdentifiedEntity.NATURAL_KEY_CONSTRAINT_NAME,
        columnNames = [Person_.NAME,
            Person_.PATRONYMIC,
            Person_.SURNAME,
            Person_.BIRTH_DATE]
    )]
)
class Person(
    @field:NotBlank
    @Column(nullable = false)
    var name: String?,
    @field:NotBlank
    @Column(nullable = false)
    var surname: String?,
    @field:NotBlank
    @Column(nullable = false)
    var patronymic: String?,
    @field:NotNull
    var birthDate: Long? = null,
    @field:NotBlank
    @field:Pattern(regexp = ConstraintPatterns.PHONE_PATTERN)
    @Column(nullable = false)
    var phone: String? = null,
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "estate_address_id", columnDefinition = "bigint", nullable = false)
    var estateAddress: PhysicalAddress? = null,
    @OneToOne(mappedBy = "person")
    var user: User? = null,
) : IdentifiedEntity<Person>() {

    constructor() : this(null, null, null, null)

    override fun buildNaturalKey(): Array<KMutableProperty1<Person, *>> = arrayOf(
        Person::name,
        Person::surname,
        Person::patronymic,
        Person::birthDate
    )

    companion object {
        private const val serialVersionUID = 1L
        fun from(copyingEntity: Person): Person = Person(
            name = copyingEntity.name,
            surname = copyingEntity.surname,
            patronymic = copyingEntity.patronymic,
            phone = copyingEntity.phone,
            birthDate = copyingEntity.birthDate
        )

    }
}


