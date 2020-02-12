package linkan.a740362.nearbyrestaurantapp.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.NonNull
import androidx.room.Room
import linkan.a740362.nearbyrestaurantapp.data.persistence.dao.AppDatabase
import linkan.a740362.nearbyrestaurantapp.di.annotation.DatabaseInfo
import linkan.a740362.nearbyrestaurantapp.di.annotation.PreferenceInfo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PersistenceModule{

    @Provides
    @PreferenceInfo
    internal fun providePrefName() : String{
        return "NearbyRestaurantPref"
    }

    @Provides
    @DatabaseInfo
    internal fun provideDBName() : String{
        return "NearbyRestaurantApp.db"
    }

    @Provides
    @Singleton
    internal fun providePreference(@PreferenceInfo prefName : String, application : Application) : SharedPreferences{
        return application.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    }


    @Provides
    @Singleton
    internal fun provideAppDatabase( application : Application, @DatabaseInfo dbName : String) : AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, dbName)
            .fallbackToDestructiveMigration()
            .build()
    }

}
