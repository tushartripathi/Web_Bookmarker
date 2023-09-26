package com.example.webbookmarker.ui.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.webbookmarker.R
import com.example.webbookmarker.Room.Notes.NotesEntity
import com.example.webbookmarker.databinding.FragmentBottomSheetBinding
import com.example.webbookmarker.databinding.FragmentReadNoteBottomSheetBinding
import com.example.webbookmarker.ui.TakeNote.MainActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class ReadNoteBottomSheet : BottomSheetDialogFragment() {

    lateinit var binding : FragmentReadNoteBottomSheetBinding
    private val operationViewModel by lazy {
        (activity as MainActivity).viewModel
    }

    private val dataViewModel by lazy {
        (activity as MainActivity).dbViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReadNoteBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    var listData :List<NotesEntity>? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        binding.closeIv.setOnClickListener{
            dismiss()
        }
        var readId = operationViewModel.showNoteId.value
        listData = dataViewModel.notes.value

        showData(readId,listData)

    }

    private fun showData(readId: Long?, listData: List<NotesEntity>?) {
        val filteredNote = listData?.find { it.id == readId }

        if (filteredNote != null) {

            binding.noteTV.text = filteredNote?.notes
        } else {
            Toast.makeText(requireContext(),"no value ", Toast.LENGTH_SHORT).show()
        }
    }

}