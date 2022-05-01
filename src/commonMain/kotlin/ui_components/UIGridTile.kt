package ui_components

import com.soywiz.kds.Array2
import com.soywiz.korge.input.onClick
import com.soywiz.korge.input.onOut
import com.soywiz.korge.input.onOver
import com.soywiz.korge.ui.UIText
import com.soywiz.korge.ui.uiText
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import globals.TILE_LENGTH
import globals.TILE_INNER_RATIO

class UIGridTile(
    val gridX: Int,
    val gridY: Int
) : Container() {

    val BASE_COLOR = Colors.WHITE

    var isStatic = false

    val STATIC_ALPHA = 0.8
    val NORMAL_ALPHA = 0.9
    val HOVER_ALPHA = 0.95
    val SELECTED_ALPHA = 1.0

    val HINT_TEXT_ALPHA = 0.5
    val SELECTED_NUMBER_TEXT_ALPHA = 0.95

    var chosenNumber: Int? = null

    var isSelected = false

    var inner: SolidRect

    var hintLayer: Container
    var hintNumberTexts: List<Text>

    var numberText: Text

    init {
        val bg = solidRect(TILE_LENGTH, TILE_LENGTH, color = Colors.BLACK)
        inner = solidRect(
            TILE_LENGTH * TILE_INNER_RATIO, TILE_LENGTH * TILE_INNER_RATIO,
            color = BASE_COLOR.withAd(NORMAL_ALPHA)
        ) {
            centerOn(bg)
        }

        hintLayer = container()
        hintNumberTexts = mutableListOf<Text>().apply {
            repeat(9) {
                val num = it + 1
                val xRatio = getXHintRatio(num)
                val yRatio = getYHintRatio(num)

                add(hintLayer.text(
                    num.toString(), textSize = 10.0, color = Colors.BLACK.withAd
                        (HINT_TEXT_ALPHA)
                ) {
                    alignX(bg, xRatio, true)
                    alignY(bg, yRatio, true)
                    visible = false
                })
            }
        }.toList()

        numberText = text("", color = Colors.BLACK.withAd(SELECTED_NUMBER_TEXT_ALPHA))
        numberText.centerOn(inner)


        onOver {
            if (isSelected || isStatic) return@onOver
            inner.color = BASE_COLOR.withAd(HOVER_ALPHA)
        }
        onOut {
            if (isSelected || isStatic) return@onOut
            inner.color = BASE_COLOR.withAd(NORMAL_ALPHA)
        }
    }

    fun toggleSelection() {
        if (isStatic) return
        if (isSelected) {
            inner.color = BASE_COLOR.withAd(HOVER_ALPHA)
        } else {
            inner.color = BASE_COLOR.withAd(SELECTED_ALPHA)
        }
        isSelected = !isSelected
    }

    fun clearSelection() {
        if (!isSelected) return
        isSelected = false
        inner.color = BASE_COLOR.withAd(NORMAL_ALPHA)
    }

    fun getXHintRatio(num: Int): Double {
        require(num in 1..9)
        return (1 + ((num - 1) % 3) * 2) / 6.0
    }

    fun getYHintRatio(num: Int): Double {
        require(num in 1..9)
        return (1 + (num - 1) / 3 * 2) / 6.0
    }

    fun toggleHint(num: Int) {
        require(num in 1..9)
        hintNumberTexts[num - 1].apply {
            visible = !visible
        }
    }

    fun toggleNumber(num: Int) {
        require(num in 1..9)

        if (chosenNumber == num) {
            numberText.text = ""
            chosenNumber = null
            return
        }

        numberText.text = num.toString()
        numberText.centerOn(inner)
        chosenNumber = num
    }

    fun setStaticNumber(num: Int) {
        toggleNumber(num)
        inner.color = BASE_COLOR.withAd(STATIC_ALPHA)
        isStatic = true
    }

}