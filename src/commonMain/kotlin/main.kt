import com.soywiz.korge.*
import com.soywiz.korge.input.onClick
import com.soywiz.korge.ui.UIButton
import com.soywiz.korge.ui.UICheckBox
import com.soywiz.korge.ui.uiButton
import com.soywiz.korge.ui.uiCheckBox
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*
import globals.BUTTON_HEIGHT
import globals.BUTTON_WIDTH
import ui_components.UISudokuBoard

suspend fun main() = Korge(width = 512, height = 512, bgcolor = Colors["#2b2b2b"]) {

//	val tile = UIGridTile(1, 1).addTo(this)

	val sudokuBoard = UISudokuBoard().addTo(this)

	lateinit var checkBox: UICheckBox

	val buttons = container {
		checkBox = uiCheckBox(text = "Note mode")
		uiButton("Clear selection") {
			alignLeftToRightOf(checkBox, padding = 20.0)
			onClick {
				sudokuBoard.clearSelection()
			}
		}
	}

	val numbers = container {
		repeat(9) {
			val num = it + 1
			UIButton(BUTTON_WIDTH, BUTTON_HEIGHT, num.toString()).apply {
				x = BUTTON_WIDTH * it
				onClick {
					if (checkBox.checked) {
						sudokuBoard.applyHint(num)
					} else {
						sudokuBoard.applyNumber(num)
					}
				}
			}.addTo(this)
		}
	}



	sudokuBoard.centerXOnStage()
	numbers.apply {
		alignTopToBottomOf(sudokuBoard)
		centerXOnStage()
	}
	buttons.apply {
		alignTopToBottomOf(numbers)
		alignLeftToLeftOf(numbers)
	}

	sudokuBoard.generateRandomBoard()
}