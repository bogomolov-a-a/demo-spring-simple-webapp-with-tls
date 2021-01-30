package org.artembogomolova.demo.webapp.test.domain.core

import java.util.Calendar
import java.util.Date
import org.artembogomolova.demo.webapp.main.domain.core.Person
import org.artembogomolova.demo.webapp.main.domain.core.Person_
import org.artembogomolova.demo.webapp.main.domain.core.PhysicalAddress
import org.artembogomolova.demo.webapp.test.domain.AbstractAccessorEntityTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName

@DisplayName("Entity test: Person")
internal class PersonEntityTest :
    AbstractAccessorEntityTest<Person>(
        Person::class.java,
        Person::from
    ) {
    override fun buildStandardEntity(): Person = Person(
        name = NAME_VALUE,
        surname = SURNAME_VALUE,
        patronymic = PATRONYMIC_VALUE,
        phone = PHONE_VALUE,
        estateAddress = PHYSICAL_ADDRESS_VALUE,
        birthDate = BIRTH_DATE_VALUE.time
    )


    override fun containFieldCorrectValuesTest(standardEntity: Person) {
        Assertions.assertEquals(NAME_VALUE, standardEntity.name)
        Assertions.assertEquals(SURNAME_VALUE, standardEntity.surname)
        Assertions.assertEquals(PATRONYMIC_VALUE, standardEntity.patronymic)
        Assertions.assertEquals(BIRTH_DATE_VALUE.time, standardEntity.birthDate)
        Assertions.assertEquals(PHONE_VALUE, standardEntity.phone)
        Assertions.assertEquals(PHYSICAL_ADDRESS_VALUE, standardEntity.estateAddress)
        Assertions.assertEquals(null, standardEntity.user)
    }

   override fun buildAnotherEntityForTest(): Person {
        val result = buildStandardEntity()
        result.name = "ABC"
        return result
    }

    override fun withoutBasicConstraint(standardEntity: Person, columnName: String): Boolean {
        if (Person_.BIRTH_DATE == columnName) {
            withoutColumnEqualTest(standardEntity, Person::birthDate)
            return true
        }
        return personNamesBlockTest(standardEntity, columnName)
    }

    private fun personNamesBlockTest(standardEntity: Person, columnName: String): Boolean {
        return when (columnName) {
            Person_.NAME -> {
                withoutColumnEqualTest(standardEntity, Person::name)
                true
            }
            Person_.PATRONYMIC -> {
                withoutColumnEqualTest(standardEntity, Person::patronymic)
                true
            }
            Person_.SURNAME -> {
                withoutColumnEqualTest(standardEntity, Person::surname)
                true
            }
            else -> false
        }
    }

    companion object {
        private const val NAME_VALUE = "TestName"
        private const val SURNAME_VALUE = "TestSurname"
        private const val PATRONYMIC_VALUE = "TestPatronymic"
        private val BIRTH_DATE_VALUE: Date = Calendar.getInstance().time
        private const val PHONE_VALUE = "+7-812-000-00-00"
        private val PHYSICAL_ADDRESS_VALUE: PhysicalAddress? = PhysicalAddress(null, null, null, null)
    }
}