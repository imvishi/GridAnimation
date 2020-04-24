package com.example.gridanimation.presentation

import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gridanimation.R
import com.example.gridanimation.utils.ScreenUtils
import kotlinx.android.synthetic.main.main_fragment.*

class GridFragment : Fragment(),
    ItemClickListener {

    companion object {
        fun newInstance() = GridFragment()
        private const val TAG = "GridFragment"
    }

    private lateinit var gridAdapter: GridAdapter
    private lateinit var viewModel: ConfrigurationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ConfrigurationViewModel::class.java)
        gridAdapter = GridAdapter(
            requireContext(),
            this
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.main_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gridAdapter.apply {
            updateGridSpacing(viewModel.gridSpacing)
            updateAnimationDuration(viewModel.animationDuration)
            updateAlphabetList(viewModel.alphabetList)
        }
        val spanCount = ScreenUtils.getSpanCount(requireContext())
        alphabetRecycleView.apply {
            adapter = gridAdapter
            layoutManager = GridLayoutManager(requireContext(), spanCount)
            addItemDecoration(AlphabetItemDecorator())
        }
        configure.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, ConfrigurationFragment.newInstance())
                .addToBackStack(TAG)
                .commit()
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.alphabetList = this.gridAdapter.alphabetList
    }

    override fun onItemClick(position: Int, view: View) {
        gridAdapter.removeAlphabet(position, view)
    }

    /**
     * Item Decorator for adding equal spacing between items.
     */
    inner class AlphabetItemDecorator : RecyclerView.ItemDecoration() {

        private val margin = viewModel.gridSpacing

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
