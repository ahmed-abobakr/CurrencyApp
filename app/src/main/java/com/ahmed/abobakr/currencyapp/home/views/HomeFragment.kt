package com.ahmed.abobakr.currencyapp.home.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahmed.abobakr.currencyapp.R
import com.ahmed.abobakr.currencyapp.base.BaseFragment
import com.ahmed.abobakr.currencyapp.base.UiState
import com.ahmed.abobakr.currencyapp.databinding.FragmentHomeBinding
import com.ahmed.abobakr.currencyapp.home.viewmodels.HomeUiState
import com.ahmed.abobakr.currencyapp.home.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: BaseFragment<HomeViewModel>() {

    override val viewModel: HomeViewModel by viewModels()

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

        binding.editFrom.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                if(binding.spinnerFrom.selectedItemPosition != 0 && binding.spinnerTo.selectedItemPosition != 0) {
                    viewModel.convertBetweenCurrencies(
                        binding.spinnerFrom.selectedItem.toString(),
                        binding.spinnerTo.selectedItem.toString(),
                        binding.editFrom.text.toString().toDouble()
                    )
                }else {
                    Toast.makeText(requireContext(), getString(R.string.error_selection_currencies), Toast.LENGTH_LONG).show()
                }
            }
            false
        }

        binding.btnSwipe.setOnClickListener {
            if(binding.spinnerFrom.selectedItemPosition != 0 && binding.spinnerTo.selectedItemPosition != 0) {
                val selectedFromPosition = binding.spinnerFrom.selectedItemPosition
                binding.spinnerFrom.setSelection(binding.spinnerTo.selectedItemPosition)
                binding.spinnerTo.setSelection(selectedFromPosition)
            }else {
                Toast.makeText(requireContext(), getString(R.string.error_selection_currencies), Toast.LENGTH_LONG).show()
            }
        }

        binding.btnDetails.setOnClickListener {
            viewModel.clearUIStatValue()
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                binding.spinnerFrom.selectedItem.toString(),
                binding.spinnerTo.selectedItem.toString()
            ))
        }

    }

    override fun render(state: UiState) {
        when(state){
            is HomeUiState.Success -> {
                binding.editTo.setText(state.result)
            }

            is UiState.Error -> Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
        }
    }



}