package kg.daveem.onlinestore.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "products")
data class Product(
    @Expose(deserialize = false, serialize = false)
    @PrimaryKey(autoGenerate = true) var idKey: Int = 0,
    @ColumnInfo(name = "idProduct")
    @SerializedName("id")
    var idProduct: Int,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "price")
    var price: Double,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "category")
    var category: String,
    @ColumnInfo(name = "image")
    var image: String,
    @Embedded
    var rating: Rating
) {

    data class Rating(
        @ColumnInfo(name = "rating")
        var rate: Double,
        @Ignore
        var count: Int
    ) {
        constructor() : this(0.0, 0)
    }

}
