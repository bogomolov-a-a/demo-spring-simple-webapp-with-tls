package org.artembogomolova.demo.webapp.test.db.dao

import java.text.ParseException
import java.text.SimpleDateFormat
import org.artembogomolova.demo.webapp.main.domain.core.Person
import org.artembogomolova.demo.webapp.main.domain.core.PhysicalAddress

object DomainTestUtil {
    fun buildTestAddress(): PhysicalAddress = PhysicalAddress(
        postalCode = "523152",
        country = "Country1",
        state = "State1",
        city = "City1",
        district = "District1",
        street = "Street1",
        house = "1234/42",
        room = 42,
        specificPart = "unused"
    )

    fun buildOrderTestAddress(): PhysicalAddress {
        val result = buildTestAddress()
        result.room = 13
        return result
    }

    fun buildProducerTestAddress(): PhysicalAddress {
        val result = buildTestAddress()
        result.street = "Street32"
        result.specificPart = "test-producer-part"
        return result
    }

    fun buildProducerNewTestAddress(): PhysicalAddress {
        val result = buildProducerTestAddress()
        result.specificPart = "test-producer-part-2"
        return result
    }

    fun buildPerson(): Person {
        val result = Person(
            surname = "Surname1",
            name = "Name1",
            patronymic = "Patronymic1",
            phone = "+7-812-812-00-00",
            estateAddress = buildTestAddress()
        )
        try {
            result.birthDate = (SimpleDateFormat("yyyy-MM-dd").parse("2010-10-27"))
        } catch (e: ParseException) {
            //
        }
        return result
    }
}