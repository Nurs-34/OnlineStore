package kg.daveem.onlinestore.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kg.daveem.onlinestore.db.dao.ProductDao
import kg.daveem.onlinestore.model.Category
import kg.daveem.onlinestore.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductCategoryViewModel(private val productDao: ProductDao) : ViewModel() {
    private val repository = ProductRepository

    val productCategoryActionFlow: MutableSharedFlow<ProductCategoryAction> =
        MutableSharedFlow()

    private val _categoryListFLow = MutableStateFlow<List<Category>>(emptyList())
    private val categoryListFLow = _categoryListFLow

    init {
        viewModelScope.launch {
            loadCategories()
        }
    }

     suspend fun loadCategories() {
        try {

                    if (repository.getCategories().body()?.size == productDao.getAllCategories().size) return

                repository.getCategories()
                val list =
                    repository.getCategories().body()?.map { category ->
                        Category(category = category)

                }
            if (list != null) {
                _categoryListFLow.value = list
            }

                for (element in categoryListFLow.value) {
                    val category = Category(category = element.category)
                    withContext(Dispatchers.IO) { productDao.insertCategory(category = category) }
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