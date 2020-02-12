package linkan.a740362.nearbyrestaurantapp.base

import androidx.databinding.ObservableBoolean
import linkan.a740362.nearbyrestaurantapp.data.network.base.Result

class SearchErrorViewModel {

    val isVisible = ObservableBoolean(false)

    fun setIsVisible(value: Boolean) {
        this.isVisible.set(value)
    }

}