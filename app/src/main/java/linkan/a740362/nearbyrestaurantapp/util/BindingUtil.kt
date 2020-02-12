package linkan.a740362.nearbyrestaurantapp.util

import android.net.Uri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import linkan.a740362.nearbyrestaurantapp.data.entity.api.coordinateResponseApi.NearbyRestaurant
import linkan.a740362.nearbyrestaurantapp.ui.adapter.geocodeAdapter.GeocodeRestAdapter
import linkan.a740362.nearbyrestaurantapp.ui.adapter.searchAdapter.SearchRestAdapter

@BindingAdapter("bind:imageUrl")
fun setImageUrl(draweeView: SimpleDraweeView, imageUrl: String?) {

    val uri = Uri.parse(imageUrl ?: "")
    draweeView.setImageURI(uri)
}


@BindingAdapter("geocodeAdapter")
fun bindGeocodeRestAdapter(recyclerView: RecyclerView, restaurantList: List<NearbyRestaurant>) {

    val adapter = recyclerView.adapter as GeocodeRestAdapter?

    adapter?.let {
        it.clearItems()
        it.addItems(restaurantList)
    }

}


@BindingAdapter("searchAdapter")
fun bindSearchRestAdapter(recyclerView: RecyclerView, restaurantList: List<NearbyRestaurant>) {

    val adapter = recyclerView.adapter as SearchRestAdapter?

    adapter?.let {
        it.setSearchErrorUIVisibility(false)
        it.clearItems()
        it.addItems(restaurantList)
    }

}