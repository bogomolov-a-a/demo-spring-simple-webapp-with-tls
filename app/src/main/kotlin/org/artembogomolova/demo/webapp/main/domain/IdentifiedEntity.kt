package org.artembogomolova.demo.webapp.main.domain

import java.io.Serializable
import javax.persistence.*

@MappedSuperclass
open class IdentifiedEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic
    @Column(columnDefinition = "integer not null primary key autoincrement")
    var id: Long? = null
) : Serializable {

    companion object {
        private const val serialVersionUID = 1L
    }
}