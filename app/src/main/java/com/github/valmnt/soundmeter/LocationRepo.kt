package com.github.valmnt.soundmeter

import android.provider.BaseColumns
import androidx.room.*
import com.github.valmnt.soundmeter.LocationDb.Companion.COLUMN_LAT
import com.github.valmnt.soundmeter.LocationDb.Companion.COLUMN_LNG
import com.github.valmnt.soundmeter.LocationDb.Companion.COLUMN_SPEED
import com.github.valmnt.soundmeter.LocationDb.Companion.TABLE_LOCATION

@Entity(tableName = TABLE_LOCATION)
data class LocationEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = BaseColumns._ID)
    var id: Long? = null,

    @ColumnInfo(name = COLUMN_LAT) val lat: Double,
    @ColumnInfo(name = COLUMN_LNG) val lng: Double,
    @ColumnInfo(name = COLUMN_SPEED) val speed: Double
)

@Dao
interface LocationDao {

    @Insert
    fun insert(l: LocationEntity): Long

    @Query("SELECT * FROM $TABLE_LOCATION")
    fun getAll(): List<LocationEntity>

}

@Database(entities = [LocationEntity::class], version = 1)
abstract class LocationDb: RoomDatabase() {

    companion object {
        const val TABLE_LOCATION = "location"
        const val COLUMN_LAT = "lat"
        const val COLUMN_LNG = "lng"
        const val COLUMN_SPEED = "speed"
    }

    abstract fun locationDao(): LocationDao

}