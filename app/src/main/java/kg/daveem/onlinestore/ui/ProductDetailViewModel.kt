package kg.daveem.onlinestore.ui

import androidx.lifecycle.ViewModel
import kg.daveem.onlinestore.db.dao.ProductDao
import kg.daveem.onlinestore.model.Product

class ProductDetailViewModel(private val productDao: ProductDao) : ViewModel() {

     suspend fun showProductDetail(id: Int): Product {
        return productDao.getProductById(id)
    }
}