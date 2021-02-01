package org.artembogomolova.demo.webapp.test.db.dao.core

import org.artembogomolova.demo.webapp.main.dao.repo.IPhysicalAddressRepository
import org.artembogomolova.demo.webapp.main.domain.core.PhysicalAddress
import org.artembogomolova.demo.webapp.main.validation.UniqueMultiColumn
import org.artembogomolova.demo.webapp.test.config.PhysicalAddressDaoTestDatasourceConfiguration
import org.artembogomolova.demo.webapp.test.db.dao.AbstractDaoTest
import org.artembogomolova.demo.webapp.test.db.dao.DomainTestUtil
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.data.repository.CrudRepository
@Import(PhysicalAddressDaoTestDatasourceConfiguration::class)
@DisplayName("Dao test: PhysicalAddress")
internal class PhysicalAddressDaoTest : AbstractDaoTest<PhysicalAddress>(
    PhysicalAddress::class.java,
    ::PhysicalAddress
) {
    companion object {
        private const val ANOTHER_CITY = "City2"
        private const val ANOTHER_HOUSE = "42/8544"
    }

    @Autowired
    private lateinit var physicalAddressRepository: IPhysicalAddressRepository
    override fun updateEntities(savedCollection: List<PhysicalAddress>): List<PhysicalAddress> {
        val address: PhysicalAddress = savedCollection[0]
        address.city = ANOTHER_CITY
        address.house = ANOTHER_HOUSE
        return savedCollection
    }

    override fun crudRepository(): CrudRepository<PhysicalAddress, Long> = physicalAddressRepository

    override fun generateEntities(): List<PhysicalAddress> {
        return mutableListOf(DomainTestUtil.buildTestAddress())
    }

    override fun buildCommonFieldValues(uniqueMultiColumnConstraint: UniqueMultiColumn.UniqueMultiColumnConstraint): Map<String, Any>? = null

    override fun doPrepareDeniedTestEntity(
        uniqueMultiColumnConstraint: UniqueMultiColumn.UniqueMultiColumnConstraint,
        commonValues: Map<String, Any?>
    ): PhysicalAddress {
        return DomainTestUtil.buildTestAddress()
    }

    override fun doDuplicateDeniedTestEntity(
        columns: UniqueMultiColumn.UniqueMultiColumnConstraint,
        commonValues: Map<String, Any?>
    ): PhysicalAddress {
        return DomainTestUtil.buildTestAddress()
    }

    override fun buildEntityWithoutViolationEntity(): PhysicalAddress {
        return DomainTestUtil.buildTestAddress()
    }
}