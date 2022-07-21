package orlov.surf.summer.school.domain.repository

import orlov.surf.summer.school.domain.model.User

interface ProfileRepository {

    suspend fun logout()

    suspend fun fetchUser(): User

}