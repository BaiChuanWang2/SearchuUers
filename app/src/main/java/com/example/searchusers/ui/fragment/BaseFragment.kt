package com.example.searchusers.ui.fragment

import android.content.Context
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> (activity as AppCompatActivity).onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    fun hideKeyboard(ev: AppCompatEditText) {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(ev.windowToken, 0)
    }
}