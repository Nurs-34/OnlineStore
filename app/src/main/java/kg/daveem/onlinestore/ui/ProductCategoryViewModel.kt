package kg.daveem.onlinestore.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import kg.daveem.onlinestore.db.dao.ProductDao
import kg.daveem.onlinestore.model.Category
import kg.daveem.onlinestore.model.Product
import kg.daveem.onlinestore.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

class ProductCategoryViewModel(private val productDao: ProductDao) : ViewModel() {
    private val repository = ProductRepository

    val productCategoryActionFlow: MutableSharedFlow<ProductCategoryAction> =
        MutableSharedFlow()

    private val _categoryListFLow = MutableStateFlow<List<Category>>(emptyList())
    private val categoryListFLow = _categoryListFLow
    private val _productListFLow = MutableStateFlow<List<Product>>(emptyList())
    private val productListFLow = _productListFLow

    suspend fun loadCategories() {
        if (productDao.getAllCategories().isNotEmpty()) return

        try {
            val categoryList =
                repository.getCategories().body()?.map { category ->
                    Category(category = category)
                }

            val productsList = repository.getProducts().body()

            if (categoryList != null)
                _categoryListFLow.value = categoryList

            if (productsList != null)
                _productListFLow.value = productsList

            for (element in categoryListFLow.value) {
                val category = Category(category = element.category)
                withContext(Dispatchers.IO) { productDao.insertCategory(category = category) }
            }

            for (element in productListFLow.value) {
                val product = Product(
                    idProduct = element.idProduct,
                    category = element.category,
                    description = element.description,
                    image = element.image,
                    price = element.price,
                    rating = element.rating,
                    title = element.title
                )
                withContext(Dispatchers.IO) { productDao.insertProduct(product) }
            }

        } catch (e: Exception) {
            Log.e("ProductCategoryVM", "Exception: $e")
            productCategoryActionFlow.emit(ProductCategoryAction.ShowNetworkErrorToast)
        }
    }

    sealed class ProductCategoryAction {
        object ShowNetworkErrorToast : ProductCategoryAction()
    }
}