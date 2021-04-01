package models

import com.papsign.ktor.openapigen.APITag

enum class Tags(override val description: String) : APITag {
    USERS(description = "User Management Namespace"),
    RIDES(description = "Rides Management Namespace")
}