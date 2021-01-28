package org.artembogomolova.demo.webapp.test.domain.core

import org.artembogomolova.demo.webapp.main.domain.core.PhysicalAddress
import org.artembogomolova.demo.webapp.main.domain.core.PhysicalAddress_
import org.artembogomolova.demo.webapp.test.domain.AbstractAccessorEntityTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName

@DisplayName("Entity test: PhysicalAddress")
internal class PhysicalAddressEntityTest : AbstractAccessorEntityTest<PhysicalAddress>(
    PhysicalAddress::class.java,
    PhysicalAddress::from
) {
    override fun buildStandardEntity(): PhysicalAddress = PhysicalAddress(
        postalCode = POSTAL_CODE_VALUE,
        country = COUNTRY_VALUE,
        state = STATE_VALUE,
        city = CITY_VALUE,
        district = DISTRICT_VALUE,
        street = STREET_VALUE,
        house = HOUSE_VALUE,
        room = ROOM_VALUE,
        specificPart = SPECIFIC_PART_VALUE
    )


    override fun containFieldCorrectValuesTest(standardEntity: PhysicalAddress) {
        Assertions.assertEquals(POSTAL_CODE_VALUE, standardEntity.postalCode)
        Assertions.assertEquals(COUNTRY_VALUE, standardEntity.country)
        Assertions.assertEquals(STATE_VALUE, standardEntity.state)
        Assertions.assertEquals(CITY_VALUE, standardEntity.city)
        Assertions.assertEquals(DISTRICT_VALUE, standardEntity.district)
        Assertions.assertEquals(STREET_VALUE, standardEntity.street)
        Assertions.assertEquals(HOUSE_VALUE, standardEntity.house)
        Assertions.assertEquals(ROOM_VALUE, standardEntity.room)
        Assertions.assertEquals(SPECIFIC_PART_VALUE, standardEntity.specificPart)
    }

    override fun buildAnotherEntityForTest(): PhysicalAddress = PhysicalAddress(
        postalCode = "523152",
        country = "Country1",
        city = "City1",
        house = "42"
    )

    override fun withoutBasicConstraint(standardEntity: PhysicalAddress, columnName: String): Boolean {
        if (isCountryPartColumnName(columnName)) {
            return withoutCountryPartEqualTest(standardEntity, columnName)
        }
        if (isCityLevelPartColumnName(columnName)) {
            return withoutCityPartEqualTest(standardEntity, columnName)
        }
        return if (isHouseLevelPartColumnName(columnName)) {
            withoutHousePartEqualTest(standardEntity, columnName)
        } else false
    }

    private fun withoutHousePartEqualTest(standardEntity: PhysicalAddress, columnName: String): Boolean {
        return when (columnName) {
            PhysicalAddress_.HOUSE -> {
                withoutColumnEqualTest(standardEntity, PhysicalAddress::house)
                true
            }
            PhysicalAddress_.ROOM -> {
                withoutColumnEqualTest(standardEntity, PhysicalAddress::room)
                true
            }
            PhysicalAddress_.SPECIFIC_PART -> {
                withoutColumnEqualTest(standardEntity, PhysicalAddress::specificPart)
                true
            }
            else -> {
                false
            }
        }
    }

    private fun isHouseLevelPartColumnName(columnName: String): Boolean {
        return PhysicalAddress_.HOUSE == columnName || PhysicalAddress_.ROOM == columnName ||
                PhysicalAddress_.SPECIFIC_PART == columnName
    }

    private fun withoutCityPartEqualTest(standardEntity: PhysicalAddress, columnName: String): Boolean {
        return when (columnName) {
            PhysicalAddress_.CITY -> {
                withoutColumnEqualTest(standardEntity, PhysicalAddress::city)
                true
            }
            PhysicalAddress_.DISTRICT -> {
                withoutColumnEqualTest(standardEntity, PhysicalAddress::district)
                true
            }
            PhysicalAddress_.STREET -> {
                withoutColumnEqualTest(standardEntity, PhysicalAddress::street)
                true
            }
            else -> {
                false
            }
        }
    }

    private fun isCityLevelPartColumnName(columnName: String): Boolean {
        return PhysicalAddress_.CITY == columnName || PhysicalAddress_.DISTRICT == columnName ||
                PhysicalAddress_.STREET == columnName
    }

    private fun withoutCountryPartEqualTest(standardEntity: PhysicalAddress, columnName: String): Boolean {
        return when (columnName) {
            PhysicalAddress_.POSTAL_CODE -> {
                withoutColumnEqualTest(standardEntity, PhysicalAddress::postalCode)
                true
            }
            PhysicalAddress_.COUNTRY -> {
                withoutColumnEqualTest(standardEntity, PhysicalAddress::country)
                true
            }
            PhysicalAddress_.STATE -> {
                withoutColumnEqualTest(standardEntity, PhysicalAddress::state)
                true
            }
            else -> false
        }
    }

    private fun isCountryPartColumnName(columnName: String): Boolean {
        return PhysicalAddress_.POSTAL_CODE == columnName || PhysicalAddress_.COUNTRY == columnName ||
                PhysicalAddress_.STATE == columnName
    }

    companion object {
        private const val POSTAL_CODE_VALUE = "190000"
        private const val COUNTRY_VALUE = "Russia"
        private const val STATE_VALUE = "Saint Petersburg"
        private const val CITY_VALUE = "Saint Petersburg"
        private const val DISTRICT_VALUE = "District1"
        private const val STREET_VALUE = "Street1"
        private const val HOUSE_VALUE = "House1"
        private const val ROOM_VALUE = 10
        private const val SPECIFIC_PART_VALUE = "SpecificPart1"
    }
}