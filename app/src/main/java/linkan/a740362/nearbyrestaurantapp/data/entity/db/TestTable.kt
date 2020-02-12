package linkan.a740362.nearbyrestaurantapp.data.entity.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "test_table")
data class TestTable(

        @ColumnInfo(name = "column1")
        var column1: String,

        @ColumnInfo(name = "column2")
        var column2: String

) {

    // way to generate primary key
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "test_id")
    var row_id: Int = 0


}
