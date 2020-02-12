package linkan.a740362.nearbyrestaurantapp.data.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import linkan.a740362.nearbyrestaurantapp.data.network.base.BaseRepository
import linkan.a740362.nearbyrestaurantapp.data.network.base.Result
import linkan.a740362.nearbyrestaurantapp.data.persistence.pref.PrefHelper
import linkan.a740362.nearbyrestaurantapp.di.annotation.CoroutineScopeIO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import linkan.a740362.nearbyrestaurantapp.data.entity.api.coordinateResponseApi.RestaurantResponse
import linkan.a740362.nearbyrestaurantapp.data.entity.api.locationSearchResponse.LocationRestaurantResponse
import javax.inject.Inject


class AppApiHelper @Inject
constructor(
    private val coroutineApiService: CoroutineApiService,
    private val rxApiService: RxApiService,
    @CoroutineScopeIO private val ioCoroutineScope: CoroutineScope, private val prefHelper: PrefHelper
) : BaseRepository(), ApiHelper {


    private val geocodeRestaurantLiveData: MutableLiveData<Result<RestaurantResponse>> by lazy { MutableLiveData<Result<RestaurantResponse>>() }


    override fun fetchCoordinateBasedRestaurant(latitude: Double, longitude: Double) {

        ioCoroutineScope.launch {

            geocodeRestaurantLiveData.postValue(Result.Loading())

            val geocodeResponse = makeApiCall(
                call = { coroutineApiService.fetchGeocodeNearbyRestaurantAsync(latitude, longitude).await() }
            )

            geocodeRestaurantLiveData.postValue(geocodeResponse)

        }

    }

    override fun getCoordinateRestaurantLiveData(): LiveData<Result<RestaurantResponse>> {
        return geocodeRestaurantLiveData
    }


    override fun fetchRestaurantWithLocation(
        query: String,
        count_per_page: Int,
        startFrom: Int,
        entity_type : String
    ): Single<LocationRestaurantResponse> {
        return rxApiService.fetchLocationBasedRestaurants(query, count_per_page, startFrom, entity_type)
    }


}