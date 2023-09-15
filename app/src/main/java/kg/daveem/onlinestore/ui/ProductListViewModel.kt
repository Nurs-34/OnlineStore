package kg.daveem.onlinestore.ui

import androidx.lifecycle.ViewModel
import kg.daveem.onlinestore.db.dao.ProductDao
import kg.daveem.onlinestore.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

class ProductListViewModel(private val productDao: ProductDao) : ViewModel() {
    private val _productListFLow = MutableStateFlow<List<Product>>(emptyList())
    val productListFLow = _productListFLow

    suspend fun showProductList(category: String) {
        val list = withContext(Dispatchers.IO) {
            productDao.getAllProductsByCategory(category)
        }
        productListFLow.value = list
    }
}