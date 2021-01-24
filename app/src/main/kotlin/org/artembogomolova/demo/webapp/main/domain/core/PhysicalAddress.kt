package org.artembogomolova.demo.webapp.main.domain.core

import org.artembogomolova.demo.webapp.main.domain.IdentifiedEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "addresses")
class PhysicalAddress(
    @Column(nullable = false)
    val postalCode: String,
    @Column(nullable = false)
    val country: String,
    @Column(nullable = false)
    var city: String,
    @Column(nullable = false)
    var house: String,
    val state: String? = null,
    var district: String? = null,
    var street: String? = null,
    var room: Int? = null,
    var specificPart: String? = null
) : IdentifiedEntity() {

    companion object {
        private const val serialVersionUID = 1L
    }
}