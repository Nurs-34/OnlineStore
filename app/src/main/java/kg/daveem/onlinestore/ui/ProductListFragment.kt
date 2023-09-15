package kg.daveem.onlinestore.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import kg.daveem.onlinestore.R
import kg.daveem.onlinestore.adapters.ProductListAdapter
import kg.daveem.onlinestore.databinding.FragmentProductListBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductListFragment : Fragment() {

    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ProductListAdapter

    private val viewModel: ProductListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lifecycleScope = viewLifecycleOwner.lifecycleScope
        val category = arguments?.getString("category")

        adapter = ProductListAdapter(emptyList()) {
            val productId = bundleOf("id" to it.idProduct)
            Navigation.findNavController(view).navigate(R.id.navigate_to_product_detail, productId)
        }

        lifecycleScope.launch {
            viewModel.showProductList(category!!)
            adapter.updateList(viewModel.productListFLow.value)
        }

        binding.recyclerViewProductList.adapter = adapter

        binding.up.setOnClickListener {
            adapter.sortByPrice()
        }

        binding.down.setOnClickListener {
            adapter.sortByPriceDescending()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}