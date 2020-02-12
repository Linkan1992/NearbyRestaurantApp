package linkan.a740362.nearbyrestaurantapp.ui.activity.main

import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.databinding.ObservableArrayList
import linkan.a740362.nearbyrestaurantapp.base.BaseViewModel
import linkan.a740362.nearbyrestaurantapp.data.network.ApiHelper
import linkan.a740362.nearbyrestaurantapp.data.persistence.db.DbHelper
import linkan.a740362.nearbyrestaurantapp.data.persistence.pref.PrefHelper
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import linkan.a740362.nearbyrestaurantapp.data.entity.api.coordinateResponseApi.NearbyRestaurant
import linkan.a740362.nearbyrestaurantapp.data.entity.api.coordinateResponseApi.RestaurantResponse
import linkan.a740362.nearbyrestaurantapp.data.entity.api.locationSearchResponse.LocationRestaurantResponse
import linkan.a740362.nearbyrestaurantapp.data.network.base.Result
import java.util.concurrent.TimeUnit

class MainViewModel(
    private val dbHelper: DbHelper,
    private val apiHelper: ApiHelper,
    private val prefHelper: PrefHelper
) : BaseViewModel() {

    private val searchQuerySubject: PublishSubject<String> by lazy { PublishSubject.create<String>() }

    private val nearbyRestaurantObservableList = ObservableArrayList<NearbyRestaurant>()

    val mNearbyRestaurantObservableList: ObservableArrayList<NearbyRestaurant>
        get() = nearbyRestaurantObservableList

    private val searchRestaurantObservableList = ObservableArrayList<NearbyRestaurant>()

    val mSearchRestaurantObservableList: ObservableArrayList<NearbyRestaurant>
        get() = searchRestaurantObservableList

    private val geocodeRestaurantLiveData: LiveData<Result<RestaurantResponse>> =
        applyResultTransformation(apiHelper.getCoordinateRestaurantLiveData())

    val mGeocodeRestaurantLiveData: LiveData<Result<RestaurantResponse>>
        get() = geocodeRestaurantLiveData

    private val searchQueryLiveData: LiveData<Result<List<NearbyRestaurant>>> =
        applyResultTransformation(setUpLiveDataSearchCall())

    val mSearchQueryLiveData: LiveData<Result<List<NearbyRestaurant>>>
        get() = searchQueryLiveData


    private val searchListener = object : SearchView.OnQueryTextListener {

        override fun onQueryTextSubmit(query: String): Boolean {
            searchQuerySubject.onComplete()
            return true
        }

        override fun onQueryTextChange(newText: String): Boolean {
            searchQuerySubject.onNext(newText)
            return true
        }

    }


    val mSearchListener: SearchView.OnQueryTextListener
        get() = searchListener


    fun fetchGeocodeRestaurant(latitude: Double, longitude: Double) {
        apiHelper.fetchCoordinateBasedRestaurant(latitude, longitude)
    }


    private fun setUpLiveDataSearchCall(): LiveData<Result<List<NearbyRestaurant>>> {

        val searchQueryMediatorLiveData: MediatorLiveData<Result<List<NearbyRestaurant>>> = MediatorLiveData()

        val source = LiveDataReactiveStreams.fromPublisher<LocationRestaurantResponse>(setUpRXSearchCall())

        searchQueryMediatorLiveData.addSource(source) { query: LocationRestaurantResponse ->
            if (query.restaurants?.isNotEmpty()!!)
                searchQueryMediatorLiveData.postValue(Result.Success(query.restaurants))
            else
                searchQueryMediatorLiveData.postValue(Result.Error("Search result not found"))
        }

        return searchQueryMediatorLiveData
    }


    private fun setUpRXSearchCall(): Flowable<LocationRestaurantResponse> {

        return searchQuerySubject
            .debounce(300, TimeUnit.MILLISECONDS)
            .filter {
                it.isNotEmpty() && it.length > 1
            }
            .distinctUntilChanged()
            .doOnNext {
                setSearchLoading(true)
                searchRestaurantObservableList.clear()
            }
            .switchMap { it ->
                apiHelper.fetchRestaurantWithLocation(it)
                    .doOnError { Log.e("SearchAPI", it.message ?: "Server Error on Search") }
                    .onErrorReturn { LocationRestaurantResponse(restaurants = ArrayList()) }
                    .toObservable()
            }
            .doFinally { setSearchLoading(false) }
            .subscribeOn(Schedulers.io())
            .toFlowable(BackpressureStrategy.LATEST)


    }


    fun setRestaurantList(restaurantList: List<NearbyRestaurant>) {

        nearbyRestaurantObservableList.clear()

        nearbyRestaurantObservableList.addAll(restaurantList)

    }


    fun setSearchRestaurantList(restaurantList: List<NearbyRestaurant>) {

        searchRestaurantObservableList.clear()

        searchRestaurantObservableList.addAll(restaurantList)

    }


}