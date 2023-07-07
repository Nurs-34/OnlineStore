package kg.daveem.onlinestore.repository

import kg.daveem.onlinestore.model.Category
import kg.daveem.onlinestore.utils.network.RestApiInterface
import retrofit2.Response

object CategoryRepository {

    private val apiService: RestApiInterface
        get() = RestApiInterface()

    suspend fun getCategories(): Response<List<String>> {
        return apiService.getCategories()
    }
}