package linkan.a740362.nearbyrestaurantapp.data.persistence.db

import linkan.a740362.nearbyrestaurantapp.data.entity.db.TestTable
import linkan.a740362.nearbyrestaurantapp.data.persistence.dao.AppDatabase
import linkan.a740362.nearbyrestaurantapp.di.annotation.CoroutineScopeIO
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class AppDbHelper @Inject
constructor(private val appDatabase: AppDatabase, @CoroutineScopeIO private val ioCoroutineScope: CoroutineScope) :
    DbHelper {

    // implement method for DB transaction defined in DBhHelper interface
    override fun insert(firstName: String, lastName: String) {
        ioCoroutineScope.launch {
            appDatabase.testingDao().insert(TestTable(firstName, lastName))
        }
    }


    override fun fetchAllData(): LiveData<List<TestTable>> {
        return appDatabase.testingDao().loadAllData().map { list: List<TestTable>? ->  list ?: ArrayList()}
    }


}
