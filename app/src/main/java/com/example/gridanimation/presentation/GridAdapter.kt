package com.example.gridanimation.presentation

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
import com.example.gridanimation.utils.ScreenUtils
import kotlinx.android.synthetic.main.grid_alphabet.view.*

/**
 * Adapter to render the Alphabet list.
 */
class GridAdapter(
    val context: Context,
    val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<GridAdapter.GridViewHolder>() {

    private var margin = 0
    private var animationDuration = 500L
    var alphabetList = mutableListOf<Char>()
    var itemClickedPosition = -1
    val spanCount = ScreenUtils.getSpanCount(context)

    fun removeAlphabet(position: Int, view: View) {
        itemClickedPosition = position
        val anim = (AnimatorInflater.loadAnimator(context, R.animator.item_delete) as ObjectAnimator).also {
            it.target = view
            it.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    alphabetList.removeAt(position)
                    notifyDataSetChanged()
                }
            })
        }
        anim.start()
    }

    /**
     * method used to update the grid spacing
     */
    fun updateGridSpacing(spacing: Int) {
        margin = spacing
    }

    /**
     * method used to update the animation duration
     */
    fun updateAnimationDuration(animationDuration: Long) {
        this.animationDuration = animationDuration
    }

    /**
     * method used to update the alphabet list
     */
    fun updateAlphabetList(list: List<Char>) {
        alphabetList = list.toMutableList()
        itemClickedPosition = -1
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val parentViewSize = parent.measuredWidth - spanCount * margin * 2
        val view = LayoutInflater.from(context).inflate(
            R.layout.grid_alphabet, parent, false
        ).apply {
            layoutParams.width = parentViewSize / spanCount
            layoutParams.height = parentViewSize / spanCount
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
            val anim = if (position % spanCount == spanCount- 1) {
                TranslateAnimation(-fromDelta * (spanCount - 1), 0f, fromDelta, 0f)
            } else {
                TranslateAnimation(fromDelta, 0f, 0f, 0f)
            }
            if (itemClickedPosition != -1) {
                val duration = (position - itemClickedPosition) * animationDuration
                anim.duration = animationDuration
                anim.startOffset = duration + animationDuration
                view.startAnimation(anim)
            }
        }
    }
}