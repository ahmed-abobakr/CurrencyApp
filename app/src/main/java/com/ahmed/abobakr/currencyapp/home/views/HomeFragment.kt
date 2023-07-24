package com.ahmed.abobakr.currencyapp.home.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.ahmed.abobakr.currencyapp.R
import com.ahmed.abobakr.currencyapp.base.BaseFragment
import com.ahmed.abobakr.currencyapp.base.UiState
import com.ahmed.abobakr.currencyapp.databinding.FragmentHomeBinding
import com.ahmed.abobakr.currencyapp.home.data.ConvertCurrencyResponse
import com.ahmed.abobakr.currencyapp.home.viewmodels.HomeUiState
import com.ahmed.abobakr.currencyapp.home.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat

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
    }

    override fun render(state: UiState) {
        when(state){
            is HomeUiState.Success -> {
                displayConvertCurrencyResult(state.result)
            }

            is UiState.Error -> Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun displayConvertCurrencyResult(data: ConvertCurrencyResponse){
        val decimalFormat = DecimalFormat("#.##")
        binding.editTo.setText(decimalFormat.format(data.result))
    }

}