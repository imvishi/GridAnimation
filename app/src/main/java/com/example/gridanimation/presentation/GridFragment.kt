package com.example.gridanimation.presentation

import android.animation.*
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
import kotlinx.android.synthetic.main.fragment_main.*

class GridFragment : Fragment(),
    ItemClickListener {

    companion object {
        fun newInstance() = GridFragment()
        private const val TAG = "GridFragment"
    }

    private lateinit var gridAdapter: GridAdapter
    private lateinit var viewModel: ConfigurationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ConfigurationViewModel::class.java)
        gridAdapter = GridAdapter(ScreenUtils.getSpanCount(requireContext()), this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gridAdapter.apply {
            updateGridSpacing(viewModel.gridSpacing)
            updateAnimationDuration(viewModel.animationDuration)
            updateAlphabetList(viewModel.alphabetList)
        }
        val spanCount = ScreenUtils.getSpanCount(requireContext())
        rv_alphabet.apply {
            adapter = gridAdapter
            layoutManager = GridLayoutManager(requireContext(), spanCount)
            addItemDecoration(AlphabetItemDecorator())
        }
        configure.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, ConfigurationFragment.newInstance())
                .addToBackStack(TAG)
                .commit()
        }
    }

    override fun onItemClick(position: Int) {
        val view = rv_alphabet.findViewHolderForAdapterPosition(position)!!.itemView
        (AnimatorInflater.loadAnimator(context, R.animator.item_delete) as ObjectAnimator).also {
            it.target = view
            it.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    gridAdapter.let {
                        it.updateItemClickedPosition(position)
                        it.alphabetList.removeAt(position)
                        it.notifyDataSetChanged()
                    }
                    viewModel.alphabetList.removeAt(position)
                    // Removing the view from recycler view cache after item deletion, so that it
                    // can't be reused by recycle view. After animation This view has rotated
                    // with 180 degree and will show reversed alphabet if reused.
                    rv_alphabet.removeView(view)
                }
            })
            it.start()
        }
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
