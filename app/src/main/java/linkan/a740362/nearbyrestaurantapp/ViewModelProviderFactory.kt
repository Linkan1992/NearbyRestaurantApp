package linkan.a740362.nearbyrestaurantapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import linkan.a740362.nearbyrestaurantapp.data.network.ApiHelper
import linkan.a740362.nearbyrestaurantapp.data.persistence.db.DbHelper
import linkan.a740362.nearbyrestaurantapp.data.persistence.pref.PrefHelper
import linkan.a740362.nearbyrestaurantapp.di.annotation.CoroutineScopeIO
import linkan.a740362.nearbyrestaurantapp.ui.activity.main.MainViewModel
import linkan.a740362.nearbyrestaurantapp.ui.activity.splash.SplashViewModel
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Created by Linkan on 29/01/20.
 *
 *
 * A provider factory that persists ViewModels [ViewModel].
 * Used if the view model has a parameterized constructor.
 */

@Singleton
class ViewModelProviderFactory @Inject
constructor(
    private val dbHelper: DbHelper,
    private val apiHelper: ApiHelper,
    private val prefHelper: PrefHelper,
    @CoroutineScopeIO private val ioCoroutineScope: CoroutineScope
) : ViewModelProvider.NewInstanceFactory() {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> SplashViewModel(apiHelper, prefHelper, ioCoroutineScope) as T
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(dbHelper, apiHelper, prefHelper) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name} " /*+ modelClass.name*/)
        }
    }
}
