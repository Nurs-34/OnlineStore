package kg.daveem.onlinestore.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "idProduct")
    @SerializedName("id")
    val idProduct: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "price")
    val price: Double,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "category")
    val category: String,
    @ColumnInfo(name = "image")
    val image: String,
    @Ignore
    val rating: Rating
) {
    data class Rating(
        @ColumnInfo(name = "rating")
        val rating: Double,
        @Ignore
        val count: Int
    )
}
