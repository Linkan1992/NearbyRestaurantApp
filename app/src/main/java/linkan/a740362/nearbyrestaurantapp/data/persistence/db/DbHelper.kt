package linkan.a740362.nearbyrestaurantapp.data.persistence.db

import linkan.a740362.nearbyrestaurantapp.data.entity.db.TestTable
import androidx.lifecycle.LiveData


interface DbHelper {

    // declare method for DB transaction

    fun insert(firstName: String, lastName: String)

    fun fetchAllData() : LiveData<List<TestTable>>

}
