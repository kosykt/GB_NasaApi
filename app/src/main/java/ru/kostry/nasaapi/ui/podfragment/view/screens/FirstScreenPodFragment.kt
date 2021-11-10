package ru.kostry.nasaapi.ui.podfragment.view.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import ru.kostry.nasaapi.databinding.FragmentFirstScreenPodBinding
import ru.kostry.nasaapi.ui.podfragment.viewmodel.PODApiStatus
import ru.kostry.nasaapi.ui.podfragment.viewmodel.PictureOfTheDayViewModel

class FirstScreenPodFragment : Fragment() {

    private var _binding: FragmentFirstScreenPodBinding? = null
    private val binding get() = _binding!!

    private val podViewModel: PictureOfTheDayViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val fragmentBinding = FragmentFirstScreenPodBinding.inflate(inflater, container, false)
        _binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = podViewModel
            firstScreenFragment = this@FirstScreenPodFragment
        }

        val observer = Observer<PODApiStatus> {renderData(it)}
        podViewModel.status.observe(viewLifecycleOwner, observer)
    }
    
    private fun renderData(apiStatus: PODApiStatus) {
        when (apiStatus) {
            PODApiStatus.DONE -> {
                checkMediaType()
            }
                PODApiStatus.LOADING -> {
                binding.podTitleTextView.text = PODApiStatus.LOADING.toString()
//                binding.podDateTextView.text = PODApiStatus.LOADING.toString()
            }
            PODApiStatus.ERROR -> {
                binding.podTitleTextView.text = PODApiStatus.ERROR.toString()
//                binding.podDateTextView.text = PODApiStatus.ERROR.toString()
            }
        }
    }

    private fun checkMediaType() {
        TODO("Not yet implemented")
    }

}