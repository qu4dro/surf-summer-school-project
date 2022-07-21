package orlov.surf.summer.school.data.datastore

import orlov.surf.summer.school.domain.model.User
import orlov.surf.summer.school.domain.model.UserInfo

fun UserPreferences.mapToDomain(): User {
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

fun User.mapToPreferences(): UserPreferences {
    return UserPreferences(
        true,
        this.token,
        UserInfoPreferences(
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