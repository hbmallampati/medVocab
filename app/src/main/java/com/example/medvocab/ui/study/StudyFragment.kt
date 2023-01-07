package com.example.medvocab.ui.study

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medvocab.MainViewModel
import com.example.medvocab.TypesList
import com.example.medvocab.databinding.FragmentStudyBinding

class StudyFragment : Fragment() {

    private var _binding : FragmentStudyBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var adapter : TypesOfSetsAdapter

    private val TAG = "StudyFragment"
    var isStart = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStudyBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = initAdapter(binding)
        adapter.submitList(TypesList.getAll())

//        viewModel.getMasteredTypeWise().observe(viewLifecycleOwner, Observer {
//                //Initialise RC view adapter
//            Log.d(javaClass.simpleName, "got ittttt")
//                val newList = TypesList.typesList
//            Log.d(javaClass.simpleName, "listtttt : "+ newList.get(0).masteredWords)
//                adapter.submitList(newList)
//        })
    }


    private fun initAdapter(binding: FragmentStudyBinding) : TypesOfSetsAdapter {
        binding.typeRCView.layoutManager = LinearLayoutManager(binding.typeRCView.context)
        val temp = TypesOfSetsAdapter(viewModel, requireContext())
        binding.typeRCView.adapter = temp

        return temp
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}



