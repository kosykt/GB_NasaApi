package ru.kostry.nasaapi.ui.podfragment.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomappbar.BottomAppBar
import ru.kostry.nasaapi.R
import ru.kostry.nasaapi.databinding.FragmentPictureOfDayBinding
import ru.kostry.nasaapi.ui.podfragment.viewmodel.PictureOfTheDayViewModel

class PictureOfDayFragment : Fragment() {

    private var _binding: FragmentPictureOfDayBinding? = null
    private val binding get() = _binding!!

    private val pcdViewModel: PictureOfTheDayViewModel by activityViewModels()

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
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    fun touchFAB(){
        if (pcdViewModel.onMainFAB.value == true){
            changeFAB(false, R.drawable.ic_back_fab, null, BottomAppBar.FAB_ALIGNMENT_MODE_END)

        }else{
            changeFAB(true, R.drawable.ic_plus_fab, R.drawable.ic_hamburger_menu_bottom_bar, BottomAppBar.FAB_ALIGNMENT_MODE_CENTER)
        }
    }

    private fun changeFAB(position: Boolean, iconFAB: Int, iconNav: Int?, alignmentMode: Int) {
        pcdViewModel.setOnMainFAB(position)
        binding.fab.setImageDrawable(context?.let { ContextCompat.getDrawable(it, iconFAB) })
        binding.bottomAppBar.apply {
            navigationIcon = iconNav?.let { ContextCompat.getDrawable(context, it) }
            fabAlignmentMode = alignmentMode
        }
    }
}