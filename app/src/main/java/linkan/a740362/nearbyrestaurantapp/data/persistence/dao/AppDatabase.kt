package linkan.a740362.nearbyrestaurantapp.data.persistence.dao

import linkan.a740362.nearbyrestaurantapp.data.entity.db.TestTable
import androidx.room.Database
import androidx.room.RoomDatabase

// define multiple table dao based on use case

@Database(entities = [(TestTable::class)], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){

    abstract fun testingDao() : TestingDao

}
