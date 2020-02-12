package linkan.a740362.nearbyrestaurantapp.ui.activity.main

import android.annotation.SuppressLint
import android.app.SearchManager
import linkan.a740362.nearbyrestaurantapp.BR
import linkan.a740362.nearbyrestaurantapp.R
import linkan.a740362.nearbyrestaurantapp.ViewModelProviderFactory
import linkan.a740362.nearbyrestaurantapp.base.BaseActivity
import linkan.a740362.nearbyrestaurantapp.databinding.ActivityMainBinding
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import linkan.a740362.nearbyrestaurantapp.data.entity.api.coordinateResponseApi.NearbyRestaurant
import linkan.a740362.nearbyrestaurantapp.data.network.base.Result
import linkan.a740362.nearbyrestaurantapp.ui.adapter.geocodeAdapter.GeocodeRestAdapter
import linkan.a740362.nearbyrestaurantapp.ui.fragment.home.HomeFragment
import linkan.a740362.nearbyrestaurantapp.util.PermissionUtil
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(),
    MenuItem.OnActionExpandListener {

    private var searchItem: MenuItem? = null

    companion object {

        fun newIntent(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }

    }


    lateinit var mFusedLocationClient: FusedLocationProviderClient

/*    private val mFusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }*/


    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var mLayoutManager: LinearLayoutManager

    @Inject
    lateinit var geocodeRestAdapter: GeocodeRestAdapter

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProviders.of(this, viewModelProviderFactory).get(MainViewModel::class.java)
    }

    override val layoutId: Int
        get() = R.layout.activity_main


    override val viewModel: MainViewModel
        get() = mainViewModel


    override val bindingVariable: Int
        get() = BR.viewModel


    override val toolbar: Toolbar?
        get() = viewDataBinding.includeAppBar.toolbar


    override fun initOnCreate(savedInstanceState: Bundle?) {

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        //.. To Hide the home back button
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)

        subscribeLiveData()
        setUpRecyclerView()
        getLastLocation()
        setUpLocationCall()
    }

    private fun setUpLocationCall() {
        viewDataBinding.includeAppBar.imgLocation.setOnClickListener {
            mainViewModel.setRestaurantList(ArrayList())
            getLastLocation()
        }
    }


    @SuppressLint("WrongConstant")
    private fun setUpRecyclerView() {

        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        viewDataBinding.restraRecyclerView.layoutManager = mLayoutManager
        viewDataBinding.restraRecyclerView.itemAnimator = DefaultItemAnimator()
        viewDataBinding.restraRecyclerView.adapter = geocodeRestAdapter

    }


    private fun subscribeLiveData() {

        mainViewModel.mGeocodeRestaurantLiveData.observe(this@MainActivity, Observer {
            when (it) {
                is Result.Success -> {

                    searchItem?.setVisible(true)
                    viewDataBinding.includeAppBar.imgLocation.visibility = View.VISIBLE

                    viewDataBinding.includeAppBar.title.text =
                        it.data.nearbyRestaurants?.let { list: List<NearbyRestaurant> ->
                            list[0].restaurant.location.localityVerbose
                        }

                    it.data.nearbyRestaurants?.let { list: List<NearbyRestaurant> ->
                        mainViewModel.setRestaurantList(list)
                    }
                }

                is Result.Error -> {
                    searchItem?.setVisible(false)
                    viewDataBinding.includeAppBar.imgLocation.visibility = View.GONE

                    geocodeRestAdapter.setErrorUIVisibility(true)
                    it.message?.let { it1 -> showToast(it1) }
                }
            }
        })


        geocodeRestAdapter.baseRetryLiveData.observe(this@MainActivity, Observer { result: Result<String> ->
            when (result) {
                is Result.Retry -> {
                    getLastLocation()
                }
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.search_menu, menu)

        searchItem = menu?.findItem(R.id.action_search)

        val searchManager = this@MainActivity.getSystemService(Context.SEARCH_SERVICE) as SearchManager

        val searchView = searchItem?.actionView as SearchView?

        searchView?.queryHint = resources.getString(R.string.searchBy)

        setUpSearchQueryListener(searchView)
        searchItem?.setOnActionExpandListener(this)
        searchView?.setSearchableInfo(searchManager.getSearchableInfo(this@MainActivity.componentName))

        return super.onCreateOptionsMenu(menu)
    }


    private fun setUpSearchQueryListener(searchView: SearchView?) {

        searchView?.setOnQueryTextListener(mainViewModel.mSearchListener)
    }


    override fun onMenuItemActionExpand(searchItem: MenuItem?): Boolean {
        // render search fragment
        onFragmentAttached(
            R.id.restaurant_layout,
            HomeFragment.newInstance(),
            HomeFragment.TAG,
            R.anim.fade_in,
            R.anim.exit_to_right
        )
        return true
    }

    override fun onMenuItemActionCollapse(searchItem: MenuItem?): Boolean {
        // remove search fragment
        onFragmentDetached(
            HomeFragment.TAG,
            R.anim.fade_in,
            R.anim.exit_to_right
        )
        return true
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == PermissionUtil.LOCATION_PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // start fetching current location
                getLastLocation()
            }
        }
    }


    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (PermissionUtil.checkLocationPermissions(this@MainActivity)) {
            if (PermissionUtil.isLocationEnabled(this@MainActivity)) {

                mFusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                    if (location == null) {
                        requestNewLocationData()
                    } else
                        location?.let {
                          //  showToast("latitude - ${it.latitude}, longitude - ${it.longitude}")

                            mainViewModel.fetchGeocodeRestaurant(it.latitude, it.longitude)
                        }
                }
            } else {
                showToast("Turn on location")
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            PermissionUtil.requestPermissions(this@MainActivity)
        }
    }


    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient!!.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location = locationResult.lastLocation

          //  showToast("latitude - ${mLastLocation.latitude}, longitude - ${mLastLocation.longitude}")
            mainViewModel.fetchGeocodeRestaurant(mLastLocation.latitude, mLastLocation.longitude)
        }
    }


}
