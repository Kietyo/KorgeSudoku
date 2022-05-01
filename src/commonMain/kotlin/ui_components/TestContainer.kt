package ui_components

import com.soywiz.korge.ui.uiText
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import globals.TILE_LENGTH
import globals.TILE_INNER_RATIO

class TestContainer() : Container() {
    init {
        val bg = solidRect(TILE_LENGTH, TILE_LENGTH, color = Colors.BLACK)
        val inner = solidRect(
            TILE_LENGTH * TILE_INNER_RATIO, TILE_LENGTH * TILE_INNER_RATIO,
            color = Colors.WHITE.withAd(0.9)
        ) {
            centerOn(bg)
        }

        val numberText = uiText("9", width = TILE_LENGTH) {
            centerOn(inner)
        }
    }
}