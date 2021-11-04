package ru.kostry.nasaapi.ui.podfragment.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.kostry.nasaapi.R
import ru.kostry.nasaapi.databinding.FragmentPictureOfDayBinding
import ru.kostry.nasaapi.ui.podfragment.model.PODAppState
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
        pcdViewModel.getData().observe(viewLifecycleOwner, {renderData(it)})
        bottomSheetBehavior = BottomSheetBehavior.from(view.findViewById(R.id.bottom_sheet_container)!!)
        bottomSheetVisibility(BottomSheetBehavior.STATE_COLLAPSED)

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun bottomSheetVisibility(state: Int) {
        bottomSheetBehavior.state = state
    }

    private fun renderData(data: PODAppState) {
        when (data) {
            is PODAppState.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                if (url.isNullOrEmpty()) {
                    Toast.makeText(context, "is empty", Toast.LENGTH_SHORT).show()
                } else {
                    binding.imageView.load(url) {
                        lifecycle(this@PictureOfDayFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)
                    }
                }
            }
            is PODAppState.Loading -> {
                //showLoading()
            }
            is PODAppState.Error -> {
                //showError(data.error.message)
                Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun changeFAB(
        iconFAB: Int,
        iconNav: Int?,
        alignmentMode: Int,
    ) {
        binding.fab.setImageDrawable(context?.let { ContextCompat.getDrawable(it, iconFAB) })
        binding.bottomAppBar.apply {
            navigationIcon = iconNav?.let { ContextCompat.getDrawable(context, it) }
            fabAlignmentMode = alignmentMode
        }


    }

    fun touchFAB(){
        if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED){
            changeFAB(
                R.drawable.ic_back_fab,
                null,
                BottomAppBar.FAB_ALIGNMENT_MODE_END)
            bottomSheetVisibility(BottomSheetBehavior.STATE_HALF_EXPANDED)

        }else{
            changeFAB(
                R.drawable.ic_plus_fab,
                R.drawable.ic_hamburger_menu_bottom_bar,
                BottomAppBar.FAB_ALIGNMENT_MODE_CENTER)
            bottomSheetVisibility(BottomSheetBehavior.STATE_COLLAPSED)
        }
    }
}