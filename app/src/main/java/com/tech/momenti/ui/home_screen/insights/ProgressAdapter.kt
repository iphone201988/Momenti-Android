package com.tech.momenti.ui.home_screen.insights

import android.animation.ValueAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tech.momenti.R
import com.tech.momenti.data.CategoryProgress

class ProgressAdapter(
    private val items: List<CategoryProgress>
) : RecyclerView.Adapter<ProgressAdapter.ProgressViewHolder>() {

    inner class ProgressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val label: TextView = itemView.findViewById(R.id.tvLabel)
        val percentage: TextView = itemView.findViewById(R.id.tvPercentage)
        val progressForeground: View = itemView.findViewById(R.id.progressForeground)
        val progressContainer: FrameLayout = itemView.findViewById(R.id.progressContainer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgressViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_progress, parent, false)
        return ProgressViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProgressViewHolder, position: Int) {
        val item = items[position]

        holder.label.text = item.name
        holder.percentage.text = "${item.progress}%"

        // Change text color based on progress
        if (item.progress == 100) {
            holder.percentage.setTextColor(ContextCompat.getColor(holder.itemView.context, android.R.color.black))
        } else {
            holder.percentage.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.orange))
        }

        // Animate bar width
        holder.progressForeground.post {
            val parentWidth = holder.progressContainer.width
            val targetWidth = (parentWidth * item.progress) / 100

            val anim = ValueAnimator.ofInt(holder.progressForeground.width, targetWidth)
            anim.duration = 800
            anim.addUpdateListener { valueAnimator ->
                val params = holder.progressForeground.layoutParams
                params.width = valueAnimator.animatedValue as Int
                holder.progressForeground.layoutParams = params
            }
            anim.start()
        }
    }


    override fun getItemCount(): Int = items.size
}
