package linkan.a740362.nearbyrestaurantapp.data.persistence.dao

import linkan.a740362.nearbyrestaurantapp.data.entity.db.TestTable
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TestingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(testTable: TestTable)

    @Insert
    fun insertMultiple(testList: List<TestTable>)

    @Delete
    fun delete(testTable: TestTable)

    @Query("DELETE FROM test_table")
    fun deleteRecord()

    @Query("SELECT * FROM test_table")
    fun loadAllData(): LiveData<List<TestTable>?>

}
