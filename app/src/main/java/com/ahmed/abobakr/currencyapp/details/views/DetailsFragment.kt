package com.ahmed.abobakr.currencyapp.details.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmed.abobakr.currencyapp.base.BaseFragment
import com.ahmed.abobakr.currencyapp.base.UiState
import com.ahmed.abobakr.currencyapp.databinding.FragmentDetailsBinding
import com.ahmed.abobakr.currencyapp.details.viewmodels.DetailsUiState
import com.ahmed.abobakr.currencyapp.details.viewmodels.DetailsViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment: BaseFragment<DetailsViewModel>() {

    private lateinit var binding: FragmentDetailsBinding

    override val viewModel: DetailsViewModel by viewModels()

    private val args: DetailsFragmentArgs by navArgs()

    private lateinit var historicalAdapter: HistoricalDataAdapter
    private lateinit var baseCurrenciesAdapter: BaseCurrenciesConventionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val from = args.from
        val to = args.to

        historicalAdapter = HistoricalDataAdapter()
        binding.rvHistorical.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHistorical.adapter = historicalAdapter

        baseCurrenciesAdapter = BaseCurrenciesConventionAdapter()
        binding.rvOtherCurrencies.layoutManager = LinearLayoutManager(requireContext())
        binding.rvOtherCurrencies.adapter = baseCurrenciesAdapter

        viewModel.getHistoricalData(from, to)
        viewModel.convertBaseCurrencies(from)
    }

    override fun render(state: UiState) {
        when(state){
            is DetailsUiState.HistoricalUiState -> {
                displayLineChartData(state.result)
                historicalAdapter.submitList(state.result)
            }
            is DetailsUiState.BaseCurrenciesUiState -> {
                baseCurrenciesAdapter.submitList(state.result)
            }
        }
    }

    private fun displayLineChartData(items: List<Double>){
        val lineValues = ArrayList<Entry>()
        var yAxis = 0.0f
        for(item in items){
            lineValues.add(Entry(yAxis, item.toFloat()))
            yAxis += 1F
        }
        val linedataset = LineDataSet(lineValues, "${args.from} to ${args.to}")

        linedataset.circleRadius = 10f
        linedataset.setDrawFilled(true)
        linedataset.valueTextSize = 20F
        linedataset.mode = LineDataSet.Mode.CUBIC_BEZIER

        val data = LineData(linedataset)
        binding.chart.data = data
        binding.chart.animateXY(2000, 2000, Easing.EaseInCubic)
    }

}