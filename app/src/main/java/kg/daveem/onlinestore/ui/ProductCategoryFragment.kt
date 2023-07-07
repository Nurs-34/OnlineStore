package kg.daveem.onlinestore.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kg.daveem.onlinestore.R

class ProductCategoryFragment : Fragment() {

    companion object {
        fun newInstance() = ProductCategoryFragment()
    }

    private lateinit var viewModel: ProductCategoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_category, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductCategoryViewModel::class.java)
        // TODO: Use the ViewModel
    }

}