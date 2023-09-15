package kg.daveem.onlinestore.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import kg.daveem.onlinestore.R
import kg.daveem.onlinestore.adapters.ProductCategoryAdapter
import kg.daveem.onlinestore.databinding.FragmentProductCategoryBinding
import kg.daveem.onlinestore.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductCategoryFragment : Fragment() {

    private var _binding: FragmentProductCategoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ProductCategoryAdapter

    private val viewModel: ProductCategoryViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = AppDatabase.getInstance(requireContext().applicationContext)
        val productDao = db.productDao()
        val lifecycleScope = viewLifecycleOwner.lifecycleScope

        adapter = ProductCategoryAdapter(
            emptyList()
        ) {
            lifecycleScope.launch {
                val categoryBundle = bundleOf("category" to it.category)
                Navigation.findNavController(view)
                    .navigate(R.id.navigate_to_product_list, categoryBundle)
            }
        }

        lifecycleScope.launch {
            viewModel.loadCategories()
            val categories = withContext(Dispatchers.IO) {
                productDao.getAllCategories()
            }
            adapter.updateList(categories)
        }

        binding.recyclerViewCategories.adapter = adapter

        viewModel.productCategoryActionFlow.onEach { action ->
            when (action) {
                is ProductCategoryViewModel.ProductCategoryAction.ShowNetworkErrorToast -> Toast.makeText(
                    requireContext(),
                    getString(R.string.please_check_your_internet_connection),
                    Toast.LENGTH_LONG
                ).show()
            }
        }.launchIn(lifecycleScope)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}