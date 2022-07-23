package orlov.surf.summer.school.data.mapper

import orlov.surf.summer.school.data.network.model.AuthResponse
import orlov.surf.summer.school.data.network.model.LogoutResponse
import orlov.surf.summer.school.domain.model.LogoutInfo
import orlov.surf.summer.school.domain.model.User
import orlov.surf.summer.school.domain.model.UserInfo

fun AuthResponse.mapToDomain(): User {
    return User(
        this.token,
        UserInfo(
            this.userInfo.id,
            this.userInfo.phone,
            this.userInfo.email,
            this.userInfo.firstName,
            this.userInfo.lastName,
            this.userInfo.avatar,
            this.userInfo.city,
            this.userInfo.about
        )
    )
}

fun LogoutResponse.mapToDomain(): LogoutInfo {
    return LogoutInfo(
        this.code,
        this.message
    )
}