package linkan.a740362.nearbyrestaurantapp

import com.facebook.drawee.backends.pipeline.Fresco
import linkan.a740362.nearbyrestaurantapp.di.component.AppComponent
import linkan.a740362.nearbyrestaurantapp.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class BaseApplication : DaggerApplication() {

    private val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .application(this)
            .build()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return appComponent;
    }

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        appComponent.inject(this);

    }


}
