package com.example.gridanimation.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gridanimation.R
import kotlinx.android.synthetic.main.fragment_coniguration.*

/**
 * screen to update the grid configuration
 */
class ConfigurationFragment : Fragment() {

    companion object {
        fun newInstance() = ConfigurationFragment()
    }

    private lateinit var viewModel: ConfigurationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ConfigurationViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_coniguration, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bt_submit.setOnClickListener {
            if (et_animation_duration.text.isNotEmpty()) {
                viewModel.animationDuration = et_animation_duration.text.toString().toLong()
            }
            if (et_grid_spacing.text.isNotEmpty()) {
                viewModel.gridSpacing = et_grid_spacing.text.toString().toInt()
            }
            //Close the  fragment on submit button click
            activity?.onBackPressed()
        }
    }
}