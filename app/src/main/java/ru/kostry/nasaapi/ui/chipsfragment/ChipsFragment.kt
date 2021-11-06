package ru.kostry.nasaapi.ui.chipsfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.kostry.nasaapi.databinding.FragmentChipsBinding

class ChipsFragment : Fragment() {

    private var _binding: FragmentChipsBinding? = null
    var binding = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentChipsBinding.inflate(inflater, container, false)
        _binding = fragmentBinding
        return fragmentBinding.root
    }
}