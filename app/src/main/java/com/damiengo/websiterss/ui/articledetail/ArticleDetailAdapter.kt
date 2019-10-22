package com.damiengo.websiterss.ui.articledetail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.damiengo.websiterss.R
import com.damiengo.websiterss.ui.articledetail.model.ParagraphModel

class ArticleDetailAdapter(private val context: Context): RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var adapterDataList: List<Any> = emptyList()

    companion object {
        private const val TYPE_PARAGRAPH = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when (viewType) {
            TYPE_PARAGRAPH -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.article_detail_paragraph_item, parent, false)
                ParagraphViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = adapterDataList[position]
        when (holder) {
            is ParagraphViewHolder -> holder.bind(element as ParagraphModel)
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    inner class ParagraphViewHolder(itemView: View) : BaseViewHolder<ParagraphModel>(itemView) {

        private val textView = itemView.findViewById<TextView>(R.id.paragraph_content)

        override fun bind(item: ParagraphModel) {
            textView.text = item.content
        }

    }
}