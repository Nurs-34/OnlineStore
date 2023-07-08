package kg.daveem.onlinestore.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kg.daveem.onlinestore.model.Category
import kg.daveem.onlinestore.repository.ProductRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ProductCategoryViewModel() : ViewModel() {
    private val repository = ProductRepository

    val productCategoryActionFlow: MutableSharedFlow<ProductCategoryAction> =
        MutableSharedFlow()

    private val _categoryListFLow = MutableStateFlow<List<Category>>(emptyList())
    val categoryListFLow = _categoryListFLow

    init {
        viewModelScope.launch {
            loadCategories()
        }
    }

    private suspend fun loadCategories() {
        try {
            val response = repository.getCategories()

            if (response.isSuccessful) {
                val list = viewModelScope.async {
                    response.body()
                }
                _categoryListFLow.value = list.await() as List<Category>
            }
        } catch (e: Exception) {
            Log.e("ProductCategoryVM", "Exception: $e")
            productCategoryActionFlow.emit(ProductCategoryAction.ShowNetworkErrorToast)
        }
    }

    sealed class ProductCategoryAction() {
        object ShowNetworkErrorToast : ProductCategoryAction()
    }
}