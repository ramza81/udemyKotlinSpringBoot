package com.hi.udemy

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class User(
    var userId: String,

    var password: String,

    @Id
    @GeneratedValue
    var id: Long?= null
)