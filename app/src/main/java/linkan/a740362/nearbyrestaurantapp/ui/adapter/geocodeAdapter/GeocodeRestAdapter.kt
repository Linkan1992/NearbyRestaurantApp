package linkan.a740362.nearbyrestaurantapp.ui.adapter.geocodeAdapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import linkan.a740362.nearbyrestaurantapp.base.BaseRecyclerViewAdapter
import linkan.a740362.nearbyrestaurantapp.data.entity.api.coordinateResponseApi.NearbyRestaurant
import linkan.a740362.nearbyrestaurantapp.databinding.RestaurantItemLayoutBinding

class GeocodeRestAdapter(private val dataList: MutableList<NearbyRestaurant>) :
    BaseRecyclerViewAdapter<NearbyRestaurant, GeocodeRestAdapter.GeocodeViewHolder>(dataList) {


    override fun mOnCreateViewHolder(parent: ViewGroup, viewType: Int): GeocodeViewHolder {
        return GeocodeViewHolder(
            RestaurantItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }


    inner class GeocodeViewHolder(private val mBinding: RestaurantItemLayoutBinding) :
        BaseRecyclerViewAdapter<NearbyRestaurant, GeocodeViewHolder>.BaseViewHolder(mBinding.root) {

        override fun onBind(model: NearbyRestaurant?) {

            val restaurantViewModel = RestaurantViewModel(model?.restaurant)

            mBinding.viewModel = restaurantViewModel

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mBinding.executePendingBindings()

            mBinding.tvLocality.visibility = View.GONE

            val drawable: GradientDrawable = mBinding.tvRating.background as GradientDrawable

            drawable.setColor(Color.parseColor("#${model?.restaurant?.userRating?.ratingColor ?: "31B057"}"))

        }

        override fun viewDetachedFromWindow() {

        }

    }

}