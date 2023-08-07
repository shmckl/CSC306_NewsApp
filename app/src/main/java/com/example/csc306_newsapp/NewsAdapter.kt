package com.example.csc306_newsapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.OnReceiveContentListener
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

/**
 * Adapter to populate news model
 */
class NewsAdapter(private val listener: View) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private val imageModelArrayList = ArrayList<NewsData>()
    /*
     * Inflate our views using the layout defined in row_layout.xml
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        /*val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.news_list_item, parent, false)

        v.setOnClickListener{

        //openWeb()
        }
        return ViewHolder(v)*/
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_list_item, parent, false)
        val viewHolder = ViewHolder(view)
        /*view.setOnClickListener{
            listener.onClicked(imageModelArrayList[viewHolder.adapterPosition])
        }*/
        return viewHolder
    }

    private fun openWeb(context: Context, link: String?){
        val url = Uri.parse(link)
        val intent = Intent(Intent(Intent.ACTION_VIEW, url))
        startActivity(context, intent, null)
    }

    /*
     * Bind the data to the child views of the ViewHolder
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = imageModelArrayList[position]

        if (info.imageUrl != null) {
            Picasso.get().load(info.imageUrl).into(holder.imgView)
        }
        if (info.title != null) {
            holder.txtMsg.text = info.title
        }
        if (info.description != null) {
            holder.txtDesc.text = info.description
        }
        if (info.source != null) {
            holder.txtSrc.text = info.source
        }
    }

    /*
     * Get the maximum size of the
     */
    override fun getItemCount(): Int {
        return imageModelArrayList.size
    }

    fun updateNews(newsData: ArrayList<NewsData>){
        imageModelArrayList.clear()
        imageModelArrayList.addAll(newsData)
        notifyDataSetChanged()
    }

    /*
     * The parent class that handles layout inflation and child view use
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{

        var imgView = itemView.findViewById<View>(R.id.img_headline) as ImageView
        var txtMsg = itemView.findViewById<View>(R.id.text_title) as TextView
        var txtDesc = itemView.findViewById<View>(R.id.article_description) as TextView
        var txtSrc = itemView.findViewById<View>(R.id.text_source) as TextView

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val msg = txtMsg.text
            val snackbar = Snackbar.make(v, "$msg" + R.string.msg, Snackbar.LENGTH_LONG)
            snackbar.show()
        }
    }

    /*interface OnNewsClick{
        fun onClicked(news:NewsData)
    }*/
}
