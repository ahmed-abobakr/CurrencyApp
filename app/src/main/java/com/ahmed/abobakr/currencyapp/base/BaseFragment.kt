package com.ahmed.abobakr.currencyapp.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

abstract class BaseFragment<VM: BaseViewModel>: Fragment() {

    protected abstract val viewModel: BaseViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.uiState.observe(viewLifecycleOwner){ baseRender(it)}
    }

    private fun baseRender(state: UiState) {
        when (state) {
            is UiState.Loading -> showLoading()
            is UiState.Error -> showError(state.message)
            else -> {
                hideLoading()
                render(state)
            }
        }
    }

    abstract fun render(state: UiState)

    private fun showLoading() { }

    private fun hideLoading() { }

    private fun showError(message: String){
        hideLoading()
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }


}