package kg.daveem.onlinestore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import kg.daveem.onlinestore.databinding.ProductListItemBinding
import kg.daveem.onlinestore.model.Product

class ProductListAdapter(
    private var values: List<Product>,
    private val onItemClickListener: (Product) -> Unit
) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductListAdapter.ViewHolder {
        val binding =
            ProductListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductListAdapter.ViewHolder, position: Int) {
        val item = values[position]
        holder.bind(item)

        holder.itemView.setOnClickListener { onItemClickListener(item) }
    }


    inner class ViewHolder(binding: ProductListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val image: ShapeableImageView = binding.imageView
        private val title: TextView = binding.textViewTitle
        private val price: TextView = binding.textViewPrice

        fun bind(item: Product) {
            Glide.with(itemView).load(item.image).into(image)
            title.text = item.title
            price.text = "Price ${item.price}"

        }
    }

    override fun getItemCount(): Int = values.size
    fun updateList(products: List<Product>) {
        values = products
        notifyDataSetChanged()
    }

    fun sortByPrice() {
        values = values.sortedBy { it.price }
        notifyDataSetChanged()
    }

    fun sortByPriceDescending() {
        values = values.sortedByDescending { it.price }
        notifyDataSetChanged()
    }

}