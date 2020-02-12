package linkan.a740362.nearbyrestaurantapp.ui.activity.splash

import linkan.a740362.nearbyrestaurantapp.base.BaseViewModel
import linkan.a740362.nearbyrestaurantapp.data.network.ApiHelper
import linkan.a740362.nearbyrestaurantapp.data.network.base.Result
import linkan.a740362.nearbyrestaurantapp.data.persistence.pref.PrefHelper
import linkan.a740362.nearbyrestaurantapp.util.AppConstants
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(
    private val apiHelper: ApiHelper,
    private val prefHelper: PrefHelper,
    private val ioCoroutineScope: CoroutineScope
) : BaseViewModel() {


    private val statusLiveData: MutableLiveData<Result<String>> by lazy { MutableLiveData<Result<String>>() }

    val mStatusLiveData: LiveData<Result<String>>
        get() = statusLiveData

    init {
        ioCoroutineScope.launch {

            delay(AppConstants.SPLASH_TIME_IN_MILLIS)

            statusLiveData.postValue(Result.Success(AppConstants.SUCCESS))

        }
    }


}