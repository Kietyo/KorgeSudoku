package models

import com.soywiz.kds.*
import kotlin.random.Random

private fun <K, V> MutableMapList<K, V>.put(key: K, value: V) {
    getOrPut(key) {
        ArrayList<V>()
    }.add(value)
}

private fun <E> List<E>.getRandom(): E {
    return this.getCyclic(Random.nextInt())
}

fun getCountToCoordinates(data: Array2<TileOptions>): MutableMapList<Int, TileOptions> {
    val countToCoordinates = linkedHashMapListOf<Int, TileOptions>()

    data.each { x, y, v ->
        if (v.size > 0) countToCoordinates.put(v.size, v)
    }

    return countToCoordinates
}

object SudokuGenerator {
    fun generate(numTilesToFill: Int): IntArray2 {
        val optionsBoard = Array2.withGen(9, 9) {x, y ->
            TileOptions(x, y)
        }

        val board = IntArray2(9, 9, 0)

        repeat(numTilesToFill) {
            val countToCoordinates = getCountToCoordinates(optionsBoard)
            val minCount = countToCoordinates.keys.minOf { it }
            val tiles = countToCoordinates[minCount]!!

            val selectedTileOptions = tiles.getRandom()
            val chosenNumber = selectedTileOptions.getRandomOption()
            board[selectedTileOptions.x, selectedTileOptions.y] = chosenNumber

            // No longer have any more options since we've already chosen a number for the tile
            selectedTileOptions.options.clear()

            optionsBoard.filter {
                it.y == selectedTileOptions.y
            }.map {
                it.options.remove(chosenNumber)
            }

            optionsBoard.filter {
                it.x == selectedTileOptions.x
            }.map {
                it.options.remove(chosenNumber)
            }

            optionsBoard.filter {
                it.getXQuantile() == selectedTileOptions.getXQuantile() &&
                        it.getYQuantile() == selectedTileOptions.getYQuantile()
            }.map {
                it.options.remove(chosenNumber)
            }
        }

        return board
    }
}


data class TileOptions(
    val x: Int,
    val y: Int,
    val options: MutableSet<Int> = mutableSetOf<Int>(
        1, 2, 3, 4, 5, 6, 7, 8, 9
    )
) {
    val size: Int
        get() = options.size

    fun getRandomOption(): Int {
        return options.toList().getRandom()
    }

    fun getXQuantile(): Int {
        return x / 3
    }

    fun getYQuantile(): Int {
        return y / 3
    }
}
