package kg.daveem.onlinestore.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kg.daveem.onlinestore.model.Category
import kg.daveem.onlinestore.model.Product

@Dao
interface ProductDao {

    @Insert
    suspend fun insertCategory(category: Category)

    @Insert
    suspend fun insertProduct(product: Product)

    @Query("SELECT * FROM categories")
    suspend fun getAllCategories(): List<Category>

    @Query("SELECT * FROM products WHERE category = :category")
    suspend fun getAllProductsByCategory(category: String): List<Product>

    @Query("SELECT * FROM products WHERE idProduct = :id")
    suspend fun getProductById(id: Int): Product

}