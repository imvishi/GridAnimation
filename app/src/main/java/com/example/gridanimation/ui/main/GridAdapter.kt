package com.example.gridanimation.ui.main

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


    var alphabetList = mutableListOf<Char>()
    val margin = context.resources.getDimension(R.dimen.item_margin).toInt()

    init {
        ('A'..'Z').forEach { alphabetList.add(it) }
    }

    fun removeAlphabet(position: Int, view: View) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val viewSize = minOf(parent.measuredHeight, parent.measuredWidth) - SPAN_COUNT*margin * 2
        val view = LayoutInflater.from(context).inflate(
            R.layout.grid_alphabet, parent, false
        ).apply {
            layoutParams.width = viewSize / SPAN_COUNT
            layoutParams.height = viewSize / SPAN_COUNT
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
        }
    }
}