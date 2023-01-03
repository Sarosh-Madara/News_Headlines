package com.newsheadlines.app.newslist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.newsheadlines.app.R
import com.newsheadlines.app.data.model.Article
import com.newsheadlines.app.util.Constants.DATE_FORMAT_M_D
import com.newsheadlines.app.util.Constants.DATE_FORMAT_YMD_T_HMS_Z
import com.newsheadlines.app.util.changeDateFormat
import com.newsheadlines.app.util.gone
import com.newsheadlines.app.util.loadImageFromURL

class NewsListAdapter(private val context: Context, private val dataSet: Array<Article>, private val onNewsItemClick: (article : Article) -> Unit) :
    RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtTitle: TextView
        val txtDescription: TextView
        val txtSource: TextView
        val txtAuthor: TextView
        val txtDate: TextView
        val imgNewsItem: ImageView

        init {
            // Define click listener for the ViewHolder's View
            txtTitle = view.findViewById(R.id.txtTitle)
            txtDescription = view.findViewById(R.id.txtDescription)
            txtAuthor = view.findViewById(R.id.txtAuthor)
            txtSource = view.findViewById(R.id.txtSource)
            txtDate = view.findViewById(R.id.txtDate)
            imgNewsItem = view.findViewById(R.id.imgNewsItem)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.row_news_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.txtTitle.text = dataSet[position].title
        viewHolder.txtDescription.text = dataSet[position].description
        viewHolder.txtAuthor.text = dataSet[position].author
        viewHolder.txtSource.text = dataSet[position].source.name
        viewHolder.txtDate.text = dataSet[position].publishedAt.changeDateFormat(
            DATE_FORMAT_YMD_T_HMS_Z,
            DATE_FORMAT_M_D
        )
        if (dataSet[position].urlToImage.isNotBlank()) {
            context.loadImageFromURL(dataSet[position].urlToImage, viewHolder.imgNewsItem)
        } else {
            viewHolder.imgNewsItem.gone()
        }

        viewHolder.itemView.setOnClickListener {
            onNewsItemClick.invoke(dataSet[position])
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
