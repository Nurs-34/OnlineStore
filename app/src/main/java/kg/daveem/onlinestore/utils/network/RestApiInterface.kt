package kg.daveem.onlinestore.utils.network

import kg.daveem.onlinestore.model.Product
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestApiInterface {

    @GET("/products/categories")
    suspend fun getCategories(): Response<List<String>>

    @GET("/products/category/{categoryId}")
    suspend fun getProductsByCategory(
        @Path("categoryId") categoryId: String
    ): Response<List<Product>>

    @GET("/products/category/{categoryId}")
    suspend fun getSortedProductsByCategory(
        @Path("categoryId") categoryId: String,
        @Query("sort") sort: String,
    ): Response<List<Product>>

    @GET("/products/{id}")
    suspend fun getProduct(
        @Path("id") productId: Int
    ): Response<Product>



    companion object {
        private const val BASE_URL = "https://fakestoreapi.com"

        private val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        private val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val url = originalRequest.url.newBuilder().build()

                val modifierRequest = originalRequest.newBuilder()
                    .url(url)
                    .build()
                chain.proceed(modifierRequest)
            }
            .build()

        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        operator fun invoke(): RestApiInterface {
            return retrofit.create(RestApiInterface::class.java)
        }
    }
}