package com.example.webbookmarker.ui.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.webbookmarker.databinding.FragmentBottomSheetBinding
import com.example.webbookmarker.ui.TakeNote.MainActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddNoteBottomSheet : BottomSheetDialogFragment() {


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

            if(!binding.noteEt.text.isNullOrEmpty()) {
                var axis = viewModel.yAxisPosition.value
                viewModel.setNotesValue(binding.noteEt.text.toString())
                dismiss()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.setLongPressValue(false)
    }
}