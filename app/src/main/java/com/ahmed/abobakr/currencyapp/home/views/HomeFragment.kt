package com.ahmed.abobakr.currencyapp.home.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.ahmed.abobakr.currencyapp.R
import com.ahmed.abobakr.currencyapp.databinding.FragmentHomeBinding

class HomeFragment: Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.spinnerFrom.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line,
                    resources.getStringArray(R.array.currencies_From))

        binding.spinnerTo.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line,
            resources.getStringArray(R.array.currencies_To))
    }

}