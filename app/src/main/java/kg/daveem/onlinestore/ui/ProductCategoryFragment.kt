package kg.daveem.onlinestore.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kg.daveem.onlinestore.R
import kg.daveem.onlinestore.adapters.ProductCategoryAdapter
import kg.daveem.onlinestore.databinding.FragmentProductCategoryBinding
import kg.daveem.onlinestore.db.AppDatabase
import kg.daveem.onlinestore.db.dao.ProductDao
import kg.daveem.onlinestore.model.Category
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

    companion object {
        fun newInstance() = ProductCategoryFragment()
    }

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

        (activity as AppCompatActivity).supportActionBar?.title =
            requireActivity().getString(R.string.categories)
        //не работает

        lifecycleScope.launch{
            viewModel.loadCategories()


        }

        adapter = ProductCategoryAdapter(
            emptyList()
        ) {

        }
        lifecycleScope.launch {
            val categories = withContext(Dispatchers.IO) {
                productDao.getAllCategories()
            }
            adapter.updateList(categories)
        }

        binding.recyclerViewCategories.adapter = adapter


        val backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Toast.makeText(requireActivity(), "Lol", Toast.LENGTH_LONG).show()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            backPressedCallback
        )

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