package com.example.gridanimation.ui.main

import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gridanimation.R
import kotlinx.android.synthetic.main.main_fragment.*

const val SPAN_COUNT = 4
class MainFragment : Fragment(), ItemClickListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var gridAdapter: GridAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gridAdapter = GridAdapter(requireContext(), this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.main_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        alphabetRecycleView.apply {
            adapter = gridAdapter
            layoutManager = GridLayoutManager(requireContext(), SPAN_COUNT)
            addItemDecoration(AlphabetItemDecorator())
        }
    }

    override fun onItemClick(position: Int) {
        gridAdapter.removeAlphabet(position)
    }

    /**
     * Item Decorator for adding equal spacing between items.
     */
    inner class AlphabetItemDecorator : RecyclerView.ItemDecoration() {

        private val margin = resources.getDimension(R.dimen.item_margin).toInt()

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.apply {
                top = margin
                bottom = margin
                right = margin
                left = margin
            }
        }
    }
}
