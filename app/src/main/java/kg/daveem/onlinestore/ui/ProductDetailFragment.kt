package kg.daveem.onlinestore.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kg.daveem.onlinestore.databinding.FragmentProductDetailBinding
import kg.daveem.onlinestore.model.Product
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductDetailFragment : Fragment() {

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductDetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lifecycleScope = viewLifecycleOwner.lifecycleScope
        val productId = arguments?.getInt("id")
        val productLink = "https://fakestoreapi.com/products/$productId"

        lifecycleScope.launch {
            if (productId != null) {
                bind(product = viewModel.showProductDetail(productId), view)
            }
        }
        binding.imageViewShare.setOnClickListener {
            shareLink(productLink)
        }
    }

    private fun bind(product: Product, view: View) {
        binding.textViewTitle.text = product.title
        binding.textViewDescription.text = product.description
        binding.textViewPrice.text = product.price.toString()
        binding.textViewRating.text = product.rating.rate.toString()
        binding.textViewCategoryTitle.text = product.category
        Glide.with(view).load(product.image).into(binding.imageView)
    }

    private fun shareLink(link: String) {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, link)
        startActivity(Intent.createChooser(shareIntent, link))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}