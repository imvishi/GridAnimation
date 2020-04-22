package com.example.gridanimation.ui.main

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import androidx.recyclerview.widget.RecyclerView
import com.example.gridanimation.R
import kotlinx.android.synthetic.main.grid_alphabet.view.*

/**
 * Adapter to render the Alphabet list.
 */
class GridAdapter(
    val context: Context,
    val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<GridAdapter.GridViewHolder>() {

    private var parentViewSize = 0
    var alphabetList = mutableListOf<Char>()
    var itemClickedPosition = -1
    val margin = context.resources.getDimension(R.dimen.item_margin).toInt()

    init {
        ('A'..'Z').forEach { alphabetList.add(it) }
    }

    fun removeAlphabet(position: Int, view: View) {
        itemClickedPosition = position
        val anim = (AnimatorInflater.loadAnimator(context, R.animator.item_delete) as ObjectAnimator).also {
            it.target = view
            it.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    alphabetList.removeAt(position)
                    notifyItemRangeChanged(position, alphabetList.size + 1)
                }
            })
        }
        anim.start()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        parentViewSize = minOf(parent.measuredHeight, parent.measuredWidth) - SPAN_COUNT*margin * 2
        val view = LayoutInflater.from(context).inflate(
            R.layout.grid_alphabet, parent, false
        ).apply {
            layoutParams.width = parentViewSize / SPAN_COUNT
            layoutParams.height = parentViewSize / SPAN_COUNT
        }
        return GridViewHolder(view)
    }

    override fun getItemCount() = alphabetList.size

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class GridViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val textView = view.alphabet
        fun bind(position: Int) {
            textView.text = alphabetList[position].toString()
            view.setOnClickListener {
                itemClickListener.onItemClick(position, view)
            }
            val fromDelta = view.layoutParams.height.toFloat() + 2 * margin
            val anim = if (position % SPAN_COUNT == SPAN_COUNT - 1) {
                TranslateAnimation(-fromDelta * (SPAN_COUNT - 1), 0f, fromDelta, 0f)
            } else {
                TranslateAnimation(fromDelta, 0f, 0f, 0f)
            }
            if (itemClickedPosition != -1) {
                val duration = (position - itemClickedPosition) * 500L
                anim.duration = 500L
                anim.startOffset = duration + 500
                view.startAnimation(anim)
            }
        }
    }
}