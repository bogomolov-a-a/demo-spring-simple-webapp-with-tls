package org.artembogomolova.demo.webapp.main.domain.core

import org.artembogomolova.demo.webapp.main.domain.IdentifiedEntity
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "addresses")
class PhysicalAddress(
    var postalCode: String,
    var country: String,
    var state: String? = null,
    var city: String,
    var district: String? = null,
    var street: String? = null,
    var house: String,
    var room: Int? = null,
    var specificPart: String? = null
) : IdentifiedEntity() {


    companion object {
        private const val serialVersionUID = 1L
    }
}