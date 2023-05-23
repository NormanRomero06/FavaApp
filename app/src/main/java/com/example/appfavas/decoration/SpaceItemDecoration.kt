package com.example.appfavas.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecoration(private val spaceHorizontal: Int, private val spaceVertical: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = spaceHorizontal // Margen izquierdo
        outRect.right = spaceHorizontal // Margen derecho
        outRect.top = spaceVertical // Margen superior
        outRect.bottom = spaceVertical // Margen inferior
    }
}

