package org.artembogomolova.demo.webapp.main.domain.core

import org.artembogomolova.demo.webapp.main.domain.IdentifiedEntity
import org.artembogomolova.demo.webapp.main.domain.auth.User
import java.util.Date
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
import javax.persistence.Table
import javax.persistence.Temporal
import javax.persistence.TemporalType


@Entity
@Table(name = "persons")
class Person(
    @Column(nullable = false)
    val name: String,
    @Column(nullable = false)
    val surname: String,
    @Column(nullable = false)
    val patronymic: String,
    @Column(nullable = false)
    val phone: String,
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "estate_address_id", columnDefinition = "bigint", nullable = false)
    var estateAddress: PhysicalAddress?,
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "birthDate", nullable = false)
    private var _birthDate: Date? = null,
    @OneToOne(mappedBy = "person")
    val user: User? = null
) : IdentifiedEntity() {

    var birthDate: Date?
        get() = if (_birthDate != null) {
            Date(_birthDate!!.time)
        } else {
            null
        }
        set(value) {
            if (value != null) {
                _birthDate = Date(value.time)
            }
        }

    companion object {
        private const val serialVersionUID = 1L
    }
}