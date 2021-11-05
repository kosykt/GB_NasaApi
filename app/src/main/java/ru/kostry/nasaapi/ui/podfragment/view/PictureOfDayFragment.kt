package ru.kostry.nasaapi.ui.podfragment.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.kostry.nasaapi.R
import ru.kostry.nasaapi.databinding.FragmentPictureOfDayBinding
import ru.kostry.nasaapi.ui.podfragment.viewmodel.PictureOfTheDayViewModel

class PictureOfDayFragment : Fragment() {

    private var _binding: FragmentPictureOfDayBinding? = null
    private val binding get() = _binding!!

    private val pcdViewModel: PictureOfTheDayViewModel by activityViewModels()

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
            viewModel = pcdViewModel
            pcdFragment = this@PictureOfDayFragment
        }
        bottomSheetBehavior =
            BottomSheetBehavior.from(view.findViewById(R.id.bottom_sheet_container)!!)

        bottomSheetOptions(BottomSheetBehavior.STATE_COLLAPSED, null)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    fun touchFAB() {
        if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED) {
            bottomSheetOptions(
                BottomSheetBehavior.STATE_HALF_EXPANDED,
                pcdViewModel.explanation.value.toString()
            )
        } else {
            bottomSheetOptions(BottomSheetBehavior.STATE_COLLAPSED, null)
        }
    }

    private fun bottomSheetOptions(state: Int, explanation: String?) {
        bottomSheetBehavior.state = state
        binding.bottomSheetContainer.bottomSheetDescription.text = explanation
    }
}