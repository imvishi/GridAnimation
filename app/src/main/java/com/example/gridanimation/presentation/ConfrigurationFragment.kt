package com.example.gridanimation.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gridanimation.R
import kotlinx.android.synthetic.main.configuration_fragment.*

/**
 * screen to update the grid configuration
 */
class ConfrigurationFragment : Fragment() {

    companion object {
        fun newInstance() = ConfrigurationFragment()
    }

    private lateinit var viewModel: ConfrigurationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ConfrigurationViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.configuration_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        submit.setOnClickListener {
            if (animationDuration.text.isNotEmpty()) {
                viewModel.animationDuration = animationDuration.text.toString().toLong()
            }
            if (gridSpacing.text.isNotEmpty()) {
                viewModel.gridSpacing = gridSpacing.text.toString().toInt()
            }
            //Close the  fragment on submit button click
            activity?.onBackPressed()
        }
    }
}