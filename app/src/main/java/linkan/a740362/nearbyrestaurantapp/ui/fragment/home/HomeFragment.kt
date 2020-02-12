package linkan.a740362.nearbyrestaurantapp.ui.fragment.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import linkan.a740362.nearbyrestaurantapp.BR
import linkan.a740362.nearbyrestaurantapp.R
import linkan.a740362.nearbyrestaurantapp.ViewModelProviderFactory
import linkan.a740362.nearbyrestaurantapp.base.BaseFragment
import linkan.a740362.nearbyrestaurantapp.data.network.base.Result
import linkan.a740362.nearbyrestaurantapp.databinding.HomeFragmentBinding
import linkan.a740362.nearbyrestaurantapp.ui.activity.main.MainViewModel
import linkan.a740362.nearbyrestaurantapp.ui.adapter.searchAdapter.SearchRestAdapter
import javax.inject.Inject

class HomeFragment : BaseFragment<HomeFragmentBinding, MainViewModel>() {


    companion object {

        val TAG = HomeFragment::class.java.simpleName;

        fun newInstance() = HomeFragment()

    }


    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var mLayoutManager: LinearLayoutManager

    @Inject
    lateinit var searchRestAdapter: SearchRestAdapter


    private val mainViewModel: MainViewModel by lazy {
        ViewModelProviders.of(activity!!, viewModelProviderFactory).get(MainViewModel::class.java)
    }

    override val layoutId: Int
        get() = R.layout.home_fragment


    override val viewModel: MainViewModel
        get() = mainViewModel


    override val bindingVariable: Int
        get() = BR.viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        subscribeLiveData()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
    }


    @SuppressLint("WrongConstant")
    private fun setUpRecyclerView() {

        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        viewDataBinding.searchRecyclerView.layoutManager = mLayoutManager
        viewDataBinding.searchRecyclerView.itemAnimator = DefaultItemAnimator()
        viewDataBinding.searchRecyclerView.adapter = searchRestAdapter

    }


    private fun subscribeLiveData() {

        mainViewModel.mSearchQueryLiveData.observe(this@HomeFragment, Observer {

            when (it) {
                is Result.Success -> {

                    viewDataBinding.heading.text =
                        it.data[0].restaurant.location.address

                    mainViewModel.setSearchRestaurantList(it.data)
                }

                is Result.Error -> {
                    searchRestAdapter.setSearchErrorUIVisibility(true)
                    viewDataBinding.heading.text = "Error"
                }
            }

        })

    }

}