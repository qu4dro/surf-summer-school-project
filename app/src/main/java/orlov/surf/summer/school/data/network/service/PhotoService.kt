package orlov.surf.summer.school.data.network.service

import orlov.surf.summer.school.data.network.model.PhotoObj
import retrofit2.http.GET
import retrofit2.http.Header

interface PhotoService {

    @GET("picture")
    suspend fun fetchPhotos(@Header("Authorization") token: String) : List<PhotoObj>

}