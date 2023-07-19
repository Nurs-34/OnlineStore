package kg.daveem.onlinestore.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kg.daveem.onlinestore.db.dao.ProductDao
import kg.daveem.onlinestore.model.Category
import kg.daveem.onlinestore.model.Product

@Database(entities = [Category::class, Product::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "store_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}