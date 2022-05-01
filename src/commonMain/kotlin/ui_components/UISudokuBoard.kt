package ui_components

import com.soywiz.kds.Array2
import com.soywiz.korge.input.onClick
import com.soywiz.korge.view.Container
import globals.THICK_SEPARATION_LINE
import globals.TILE_LENGTH
import models.SudokuGenerator

fun Int.getQuantile(): Int {
    return this / 3
}

class UISudokuBoard : Container() {

    val board = Array2.withGen<UIGridTile>(9, 9) { x, y ->
        val tile = UIGridTile(x, y).apply {
            this.x = x * TILE_LENGTH + x.getQuantile() * THICK_SEPARATION_LINE
            this.y = y * TILE_LENGTH + y.getQuantile() * THICK_SEPARATION_LINE

            onClick {
                onClickTile(x, y)
            }
        }

        addChild(tile)

        tile
    }

    fun onClickTile(x: Int, y: Int) {
        val uiTile = board[x, y]
        if (!uiTile.isSelected) {
            clearSelection()

        }
        uiTile.toggleSelection()
    }

    fun applyNumber(num: Int) {
        board.forEach {
            if (it.isSelected) {
                it.toggleNumber(num)
            }
        }
    }

    fun applyHint(num: Int) {
        board.forEach {
            if (it.isSelected) {
                it.toggleHint(num)
            }
        }
    }

    fun clearSelection() {
        board.forEach {
            it.clearSelection()
        }
    }

    fun generateRandomBoard() {
        val randomBoard = SudokuGenerator.generate(72)
        randomBoard.each { x, y, v ->
            if (v != 0) {
                board[x, y].setStaticNumber(v)
            }
        }
    }
}