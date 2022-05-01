package models

import kotlin.test.Test

internal class SudokuGeneratorTest {

    @Test
    fun generate() {
        val sudokuGenerator = SudokuGenerator()

        sudokuGenerator.generate(60)
    }
}