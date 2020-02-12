package linkan.a740362.nearbyrestaurantapp.ui.activity.splash

import linkan.a740362.nearbyrestaurantapp.BR
import linkan.a740362.nearbyrestaurantapp.R
import linkan.a740362.nearbyrestaurantapp.ViewModelProviderFactory
import linkan.a740362.nearbyrestaurantapp.base.BaseActivity
import linkan.a740362.nearbyrestaurantapp.data.network.base.Result
import linkan.a740362.nearbyrestaurantapp.databinding.ActivitySplashBinding
import linkan.a740362.nearbyrestaurantapp.ui.activity.main.MainActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import javax.inject.Inject

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory


    private val splashViewModel: SplashViewModel by lazy {
        ViewModelProviders.of(this, viewModelProviderFactory).get(SplashViewModel::class.java)
    }

    override val layoutId: Int
        get() = R.layout.activity_splash


    override val viewModel: SplashViewModel
        get() = splashViewModel


    override val bindingVariable: Int
        get() = BR.viewModel


    override val toolbar: Toolbar?
        get() = null


    override fun initOnCreate(savedInstanceState: Bundle?) {
        subscribeToLiveData()
    }

    private fun subscribeToLiveData() {

        splashViewModel.mStatusLiveData.observe(this@SplashActivity, Observer { result ->
            when (result) {
                is Result.Success -> {
                    MainActivity.newIntent(this@SplashActivity)
                    finish()
                }
            }
        })

    }
}
