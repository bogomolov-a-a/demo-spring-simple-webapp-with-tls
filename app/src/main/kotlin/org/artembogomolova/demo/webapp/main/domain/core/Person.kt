package org.artembogomolova.demo.webapp.main.domain.core

import org.artembogomolova.demo.webapp.main.domain.IdentifiedEntity
import org.artembogomolova.demo.webapp.main.domain.auth.User
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "persons")
class Person(
    var name: String,
    var surname: String,
    var patronymic: String,
    var phone: String,
    @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH])
    @JoinColumn(name = "estate_address_id", columnDefinition = "bigint")
    var estateAddress: PhysicalAddress,
    @OneToOne
    @JoinColumn(name = "id")
    var user: User? = null
) : IdentifiedEntity() {
    @Temporal(TemporalType.TIMESTAMP)
    var birthDate: Date? = null
        get() {
            if (field != null)
                return Date(field!!.time)
            return null
        }
        set(value) {
            if (value != null) {
                field = Date(value.time)
            }
        }

    companion object {
        private const val serialVersionUID = 1L
    }
}