package com.pp.todopierre.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserUpdate(
        @SerialName("full_name")
        val name: String
)
