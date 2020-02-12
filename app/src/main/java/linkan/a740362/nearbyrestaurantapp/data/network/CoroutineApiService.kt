package linkan.a740362.nearbyrestaurantapp.data.network

import kotlinx.coroutines.Deferred
import linkan.a740362.nearbyrestaurantapp.data.entity.api.coordinateResponseApi.RestaurantResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CoroutineApiService {

    // declare method for api call

    @GET("geocode")
    fun fetchGeocodeNearbyRestaurantAsync(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double
    ): Deferred<Response<RestaurantResponse>>

}
