package ru.kostry.nasaapi.ui.podfragment.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.kostry.nasaapi.R
import ru.kostry.nasaapi.databinding.FragmentPictureOfDayBinding
import ru.kostry.nasaapi.ui.podfragment.viewmodel.PODApiStatus
import ru.kostry.nasaapi.ui.podfragment.viewmodel.PictureOfTheDayViewModel

class PictureOfDayFragment : Fragment() {

    private var _binding: FragmentPictureOfDayBinding? = null
    private val binding get() = _binding!!

    private val podViewModel: PictureOfTheDayViewModel by activityViewModels()

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val fragmentBinding = FragmentPictureOfDayBinding.inflate(inflater, container, false)
        _binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = podViewModel
            pcdFragment = this@PictureOfDayFragment
        }

        bottomSheetBehavior =
            BottomSheetBehavior.from(view.findViewById(R.id.bottom_sheet_container)!!)

        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }
        val observer = Observer<PODApiStatus>{renderExplanation(it)}
        podViewModel.status.observe(viewLifecycleOwner, observer)
    }

    private fun renderExplanation(apiStatus: PODApiStatus) {
        when(apiStatus){
            PODApiStatus.DONE -> {
                binding.bottomSheetContainer.bottomSheetDescription.text = podViewModel.explanation.value
                binding.titleTextView.text = podViewModel.title.value
            }
            PODApiStatus.LOADING -> {
                binding.bottomSheetContainer.bottomSheetDescription.text = PODApiStatus.LOADING.toString()
                binding.titleTextView.text = PODApiStatus.LOADING.toString()
            }
            PODApiStatus.ERROR -> {
                binding.titleTextView.text = PODApiStatus.ERROR.toString()
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}

