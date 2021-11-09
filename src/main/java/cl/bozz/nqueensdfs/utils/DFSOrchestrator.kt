package cl.bozz.nqueensdfs.utils

import cl.bozz.nqueensdfs.models.Board
import cl.bozz.nqueensdfs.utils.BoardPrinter.printBoard
import cl.bozz.nqueensdfs.utils.ChildBoardStateGenerator.generateChildBoardStates
import cl.bozz.nqueensdfs.utils.MirrorUtil.getAllMirrors
import cl.bozz.nqueensdfs.utils.TotalPermutationsCalculator.totalPermutations
import java.util.*

object DFSOrchestrator {

    fun orchestrateDFS(
        boardSize: Int
    ) {

        val totalPermutations = totalPermutations(boardSize)
        // Instantiate auxiliary objects and metrics
        val boardStateStack = Stack<Board>()
        val terminalBoardStates = mutableSetOf<Set<Int>>()
        val initialBoard = setOf<Int>()
        val boardStateHashes = mutableSetOf<String>()
        val initialBoardState = Board(initialBoard, 0, boardStateHashes)
        boardStateStack.add(initialBoardState)

        var totalTerminalBoards: Long = 0
        var totalPrunedBoards: Long = 0
        var totalBoardsProcessed: Long = 0
        // Main loop:
        while (!boardStateStack.empty()) {
            // 1. Pop from stack
            val boardState = boardStateStack.pop()
            totalBoardsProcessed++
            if (totalBoardsProcessed % 100 * boardSize == 0L) {
                println("Processed $totalBoardsProcessed/$totalPermutations boards; found $totalTerminalBoards terminals, pruned $totalPrunedBoards")
            }

            // 2. Filter out terminal boards and count them towards total
            if (boardState.size == boardSize) {
                terminalBoardStates.add(boardState.queenPositions)
                totalTerminalBoards += boardState.hashes.size.toLong()
                continue
            }

            // 3. Generate child boards. For each...
            val childBoardStates = generateChildBoardStates(boardState, boardSize)
            for (childBoardState in childBoardStates) {

                // 3.a. Filter out the board if any of the hashes is already registered
                //      Since they're all topologically identical, there's no need to keep any of them
                val shouldPrune = childBoardState.hashes.any { o: String -> boardStateHashes.contains(o) }
                if (shouldPrune) {
                    totalPrunedBoards++
                    continue
                }

                // 3.b. Register all new hashes to avoid repetitions in the future
                boardStateHashes.addAll(childBoardState.hashes)
                boardStateStack.add(childBoardState)
            }
        }

        // Emit metrics

        // Emit metrics
        terminalBoardStates.forEach { boardState -> printBoard(boardState, boardSize) }
        println("Max board permutations: $totalPermutations")
        println("Total terminal boards: $totalTerminalBoards")
        println("Unique terminal boards identified: " + terminalBoardStates.size)
        println("Total boards processed: $totalBoardsProcessed")
        println("Total boards pruned: $totalPrunedBoards")
    }
}