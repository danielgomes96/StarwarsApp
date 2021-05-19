package com.daniel.base

import android.annotation.SuppressLint
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior

class BottomSheetHelperCallback(
    private val onCollapsed: () -> Unit,
    private val onExpanded: () -> Unit
) : BottomSheetBehavior.BottomSheetCallback() {
    override fun onSlide(bottomSheet: View, slideOffset: Float) { }

    @SuppressLint("SwitchIntDef")
    override fun onStateChanged(bottomSheet: View, newState: Int) {
        when (newState) {
            BottomSheetBehavior.STATE_COLLAPSED -> {
                onCollapsed()
            }
            BottomSheetBehavior.STATE_EXPANDED -> {
                onExpanded()
            }
        }
    }
}
