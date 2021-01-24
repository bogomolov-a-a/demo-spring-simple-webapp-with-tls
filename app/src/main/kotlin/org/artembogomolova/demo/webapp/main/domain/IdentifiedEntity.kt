package org.artembogomolova.demo.webapp.main.domain

import java.io.Serializable
import javax.persistence.Basic
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass

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