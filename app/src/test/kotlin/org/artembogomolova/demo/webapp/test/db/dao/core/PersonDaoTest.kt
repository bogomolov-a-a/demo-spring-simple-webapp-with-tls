package org.artembogomolova.demo.webapp.test.db.dao.core

import org.artembogomolova.demo.webapp.main.dao.repo.IPersonRepository
import org.artembogomolova.demo.webapp.main.dao.repo.IPhysicalAddressRepository
import org.artembogomolova.demo.webapp.main.domain.core.Person
import org.artembogomolova.demo.webapp.main.validation.UniqueMultiColumn
import org.artembogomolova.demo.webapp.test.db.dao.AbstractDaoTest
import org.artembogomolova.demo.webapp.test.db.dao.DomainTestUtil
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.CrudRepository

@DisplayName("Dao test: Person")
internal class PersonDaoTest : AbstractDaoTest<Person>(
    Person::class.java,
    ::Person
) {
    @Autowired
    private lateinit var personRepository: IPersonRepository

    @Autowired
    private lateinit var physicalAddressRepository: IPhysicalAddressRepository

    override fun updateEntities(savedCollection: List<Person>): List<Person> {
        val person: Person = savedCollection[0]
        person.phone = "+7-812-812-81-20"
        return savedCollection
    }

    override fun crudRepository(): CrudRepository<Person, Long> = personRepository

    override fun generateEntities(): List<Person> {
        return mutableListOf(DomainTestUtil.buildPerson())
    }

    override fun buildCommonFieldValues(uniqueMultiColumnConstraint: UniqueMultiColumn.UniqueMultiColumnConstraint): Map<String, Any>? = null

    override fun doPrepareDeniedTestEntity(
        uniqueMultiColumnConstraint: UniqueMultiColumn.UniqueMultiColumnConstraint,
        commonValues: Map<String, Any?>
    ): Person = DomainTestUtil.buildPerson()

    override fun doDuplicateDeniedTestEntity(columns: UniqueMultiColumn.UniqueMultiColumnConstraint, commonValues: Map<String, Any?>): Person {
        val result: Person = DomainTestUtil.buildPerson()
        result.estateAddress = DomainTestUtil.buildOrderTestAddress()
        return result
    }

    override fun validateAnotherRepositoryEmpty() {
        /*address repository must be empty after successful delete person.*/
        Assertions.assertEquals(EMPTY_REPOSITORY_COUNT, physicalAddressRepository.count())
    }

    override fun buildEntityWithoutViolationEntity(): Person {
        return DomainTestUtil.buildPerson()
    }
}