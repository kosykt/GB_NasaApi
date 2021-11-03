package ru.kostry.nasaapi.ui.podfragment.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ru.kostry.nasaapi.databinding.FragmentPictureOfDayBinding
import ru.kostry.nasaapi.ui.podfragment.viewmodel.PictureOfTheDayViewModel

class PictureOfDayFragment : Fragment() {

    private var _binding: FragmentPictureOfDayBinding? = null
    private val binding get() = _binding!!

    private val pcdViewModel: PictureOfTheDayViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentPictureOfDayBinding.inflate(inflater, container, false)
        _binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = pcdViewModel
            pcdFragment = this@PictureOfDayFragment
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    fun touchFAB(){

        if (pcdViewModel.onMainFAB.value == true){
            pcdViewModel.setOnMainFAB(false)
            Toast.makeText(context,"true",Toast.LENGTH_SHORT).show()
        }else{
            pcdViewModel.setOnMainFAB(true)
            Toast.makeText(context,"false",Toast.LENGTH_SHORT).show()
        }
    }
}