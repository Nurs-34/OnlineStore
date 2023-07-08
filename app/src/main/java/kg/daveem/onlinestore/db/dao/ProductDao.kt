package kg.daveem.onlinestore.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kg.daveem.onlinestore.model.Category
import kg.daveem.onlinestore.model.Product

@Dao
interface ProductDao {

    @Insert
    fun insertCategory(category: Category)

    @Insert
    fun insertProduct(product: Product)

    @Query("SELECT * FROM products WHERE category = :category")
    fun getAllProductsByCategory(category: String): List<Product>

}