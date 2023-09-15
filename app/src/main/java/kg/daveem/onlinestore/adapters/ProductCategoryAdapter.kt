package kg.daveem.onlinestore.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import kg.daveem.onlinestore.R
import kg.daveem.onlinestore.databinding.ProductCategoryItemBinding
import kg.daveem.onlinestore.model.Category

class ProductCategoryAdapter(
    private var values: List<Category>,
    private val onItemClickListener: (Category) -> Unit
) : RecyclerView.Adapter<ProductCategoryAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ProductCategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductCategoryAdapter.ViewHolder, position: Int) {
        val item = values[position]
        holder.bind(item)

        holder.itemView.setOnClickListener { onItemClickListener(item) }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: ProductCategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val categoryName: TextView = binding.textViewCategory
        private val categoryAnimation: LottieAnimationView = binding.animationViewCategory


        fun bind(item: Category) {
            categoryName.text = item.category
            categoryAnimation.setAnimation(R.raw.electronics_category_anim)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Category>) {
        values = newList
        notifyDataSetChanged()
    }
}