package kg.daveem.onlinestore.repository

import kg.daveem.onlinestore.model.Product
import kg.daveem.onlinestore.utils.network.RestApiInterface
import retrofit2.Response

object ProductRepository {

    private val apiService: RestApiInterface
        get() = RestApiInterface()

    suspend fun getProductListByCategory(category: String): Response<List<Product>> {
        return apiService.getProductsByCategory(categoryId = category)
    }

    suspend fun sortProducts(category: String, sort: String): Response<List<Product>> {
        return apiService.getSortedProductsByCategory(categoryId = category, sort = sort)
    }

    suspend fun getProduct(productId: Int): Response<Product> {
        return apiService.getProduct(productId = productId)
    }
}