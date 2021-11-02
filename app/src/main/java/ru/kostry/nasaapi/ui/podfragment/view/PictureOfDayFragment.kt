package ru.kostry.nasaapi.ui.podfragment.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ru.kostry.nasaapi.databinding.FragmentPictureOfDayBinding
import ru.kostry.nasaapi.ui.podfragment.viewmodel.PictureOfTheDayViewModel

class PictureOfDayFragment : Fragment() {

    private var binding: FragmentPictureOfDayBinding? = null
    private val sharedViewModel: PictureOfTheDayViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentPictureOfDayBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}