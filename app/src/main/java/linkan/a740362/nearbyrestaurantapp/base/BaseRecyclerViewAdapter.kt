package linkan.a740362.nearbyrestaurantapp.base

import  linkan.a740362.nearbyrestaurantapp.data.network.base.Result

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import linkan.a740362.nearbyrestaurantapp.databinding.ConnectionErrorRowLayoutBinding
import linkan.a740362.nearbyrestaurantapp.databinding.SearchErrorLayoutBinding

abstract class BaseRecyclerViewAdapter<T, K : BaseRecyclerViewAdapter<T, K>.BaseViewHolder>(private val data: MutableList<T>) :
    RecyclerView.Adapter<K>() {


    var loading = false

    companion object {

        private val VIEW_TYPE_EMPTY = 0

        private val VIEW_TYPE_NORMAL = 1

        val VIEW_TYPE_SEARCH = 2
    }


    private var errorViewModel: EmptyErrorViewModel = EmptyErrorViewModel()

    var baseRetryLiveData: LiveData<Result<String>> = errorViewModel.emptyErrorLiveData

    private var searchErrorViewModel: SearchErrorViewModel = SearchErrorViewModel()


    protected abstract fun mOnCreateViewHolder(parent: ViewGroup, viewType: Int): K

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): K {
        when (viewType) {

            VIEW_TYPE_NORMAL ->

                return mOnCreateViewHolder(parent, viewType)


            VIEW_TYPE_EMPTY -> {

                val errorRowLayoutBinding = ConnectionErrorRowLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                return ConnectionErrorViewHolder(errorRowLayoutBinding) as K
            }

            VIEW_TYPE_SEARCH -> {

                val searchErrorLayoutBinding = SearchErrorLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )

                return SearchErrorViewHolder(searchErrorLayoutBinding) as K

            }

            else -> return mOnCreateViewHolder(parent, viewType)
        }
    }


    override fun onBindViewHolder(holder: K, position: Int) {
        if (holder != null && !data.isEmpty())
            holder.onBind(data[position])
        else if (data.isEmpty())
            holder.onBind(null)

    }


    override fun getItemCount(): Int {
        return if (!data.isEmpty()) {
            data.size
        } else {
            1
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (data.isNotEmpty()) {
            VIEW_TYPE_NORMAL
        } else
            VIEW_TYPE_EMPTY
    }


    fun addItems(repoList: List<T>) {
        data.addAll(repoList)
        notifyDataSetChanged()
    }


    fun clearItems() {
        data.clear()
    }

    fun getData(): List<T?> {
        return data
    }


    override fun onViewDetachedFromWindow(holder: K) {
        holder.viewDetachedFromWindow()
        super.onViewDetachedFromWindow(holder)
    }


    fun setErrorUIVisibility(visibility: Boolean) {
        errorViewModel.setIsVisible(visibility)
    }

    fun setSearchErrorUIVisibility(visibility: Boolean) {
        searchErrorViewModel.setIsVisible(visibility)
    }

    // Connection ViewHolder
    private inner class ConnectionErrorViewHolder(private val mBinding: ConnectionErrorRowLayoutBinding) :
        BaseRecyclerViewAdapter<T, K>.BaseViewHolder(mBinding.root) {

        override fun onBind(model: T?) {


            if (data?.size != 0)
                errorViewModel.setIsVisible(false)

            mBinding.viewModel = errorViewModel

            mBinding.executePendingBindings()

        }

        override fun viewDetachedFromWindow() {

        }

    }

    // Search Error ViewHolder
    private inner class SearchErrorViewHolder(private val mBinding: SearchErrorLayoutBinding) :
        BaseRecyclerViewAdapter<T, K>.BaseViewHolder(mBinding.root) {

        override fun onBind(model: T?) {

            mBinding.viewModel = searchErrorViewModel

            mBinding.executePendingBindings()

        }

        override fun viewDetachedFromWindow() {

        }

    }

    abstract inner class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        abstract fun onBind(model: T?)

        abstract fun viewDetachedFromWindow()

    }

}