package com.news.application

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*
import java.util.Date

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
open class News() {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @Column(nullable = false)
    open  var date: String? = null

    @Column(nullable = false)
    open  var title: String? = null

    @Column(nullable = false)
    open  var text: String? = null

    @Column(nullable = false)
    open  var author: String? = null
}
