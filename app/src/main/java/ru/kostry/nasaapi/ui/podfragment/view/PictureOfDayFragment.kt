package ru.kostry.nasaapi.ui.podfragment.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomappbar.BottomAppBar
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
        savedInstanceState: Bundle?
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

        bottomSheetBehavior = BottomSheetBehavior.from(view.findViewById(R.id.bottom_sheet_container)!!)

        changeFAB(R.drawable.ic_plus_fab, BottomAppBar.FAB_ALIGNMENT_MODE_CENTER)
        bottomSheetVisibility(BottomSheetBehavior.STATE_COLLAPSED, true)
        setTextToBottomSheet(null, null)

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    fun touchFAB(){
        if (pcdViewModel.positionFABisCenter.value == true && bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED){
            changeFAB(R.drawable.ic_back_fab, BottomAppBar.FAB_ALIGNMENT_MODE_END)
            bottomSheetVisibility(BottomSheetBehavior.STATE_HALF_EXPANDED, false)
            setTextToBottomSheet(pcdViewModel.title.value.toString(), pcdViewModel.explanation.value.toString())
        }else {
            changeFAB(R.drawable.ic_plus_fab, BottomAppBar.FAB_ALIGNMENT_MODE_CENTER)
            bottomSheetVisibility(BottomSheetBehavior.STATE_COLLAPSED, true)
            setTextToBottomSheet(null, null)
        }
    }

    private fun changeFAB(iconFAB: Int, alignmentMode: Int, ) {
        binding.fab.setImageDrawable(context?.let { ContextCompat.getDrawable(it, iconFAB) })
        binding.bottomAppBar.fabAlignmentMode = alignmentMode
    }

    private fun bottomSheetVisibility(state: Int, position: Boolean) {
        bottomSheetBehavior.state = state
        pcdViewModel.setPositionFABisCenter(position)
    }

    private fun setTextToBottomSheet(title: String?, explanation: String?) {
        binding.bottomSheetContainer.bottomSheetDescriptionHeader.text = title
        binding.bottomSheetContainer.bottomSheetDescription.text = explanation
    }
}