package com.example.webbookmarker

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.webbookmarker.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheet : BottomSheetDialogFragment() {


    lateinit var binding : FragmentBottomSheetBinding
    private val viewModel by lazy {
        (activity as MainActivity).viewModel
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        binding.closeIv.setOnClickListener{
            dismiss()
        }
        binding.addBtn.setOnClickListener{
            //Add value with associated place
            var axis = viewModel.yAxisPosition.value
            Toast.makeText(activity, "Add btn "+axis, Toast.LENGTH_LONG).show()
            Log.d("sdssdf","cliekd")
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("sdssfs","onDestroy()")
        viewModel.setLongPressValue(false)
    }
}