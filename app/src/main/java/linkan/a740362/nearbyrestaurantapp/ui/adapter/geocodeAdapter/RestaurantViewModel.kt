package linkan.a740362.nearbyrestaurantapp.ui.adapter.geocodeAdapter

import android.graphics.Color
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import linkan.a740362.nearbyrestaurantapp.data.entity.api.coordinateResponseApi.Restaurant

class RestaurantViewModel(restaurant: Restaurant?) {

    val thumbnail: ObservableField<String>

    val restaurantName: ObservableField<String>

    val cuisineType: ObservableField<String>

    val cost_per_person: ObservableField<String>

    val locality : ObservableField<String>

    val ratingNum: ObservableField<String>

    val ratingColor: ObservableInt


    init {

        thumbnail = ObservableField(restaurant?.thumbnail ?: "")

        restaurantName = ObservableField(restaurant?.restaurantName ?: "")

        cuisineType = ObservableField(restaurant?.cuisines ?: "")

        locality = ObservableField(restaurant?.location?.locality ?: "")

        cost_per_person =
            ObservableField("${restaurant?.currencySymbol}  ${restaurant?.average_cost_per_two?.div(2)} per person")

        ratingNum = ObservableField(restaurant?.userRating?.ratingNum ?: "0")

        ratingColor = ObservableInt(Color.parseColor("#${restaurant?.userRating?.ratingColor ?: "31B057"}"))
    }

    fun submit(){

    }

}