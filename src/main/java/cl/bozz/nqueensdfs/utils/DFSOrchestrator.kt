package cl.bozz.nqueensdfs.utils

import cl.bozz.nqueensdfs.models.Board
import cl.bozz.nqueensdfs.utils.BoardPrinter.printBoard
import cl.bozz.nqueensdfs.utils.ChildBoardStateGenerator.generateChildBoardStates
import cl.bozz.nqueensdfs.utils.HashStringUtils.generateHashStringsFromMirrors
import cl.bozz.nqueensdfs.utils.InitialBoardGenerator.generateInitialBoard
import cl.bozz.nqueensdfs.utils.MirrorUtil.getAllMirrors
import cl.bozz.nqueensdfs.utils.TotalPermutationsCalculator.totalPermutations
import java.util.*

object DFSOrchestrator {

    fun orchestrateDFS(
       n: Int
    ) {

        val totalPermutations = totalPermutations(n)
        // Instantiate auxiliary objects and metrics
        val boardStateStack = Stack<Board>()
        val terminalBoardStates = mutableSetOf<BooleanArray>()
        val initialBoardState = generateInitialBoard(n)
        boardStateStack.add(initialBoardState)
        val mirroredQueens = getAllMirrors(initialBoardState.queenPositions, n)
        val boardStateHashes = generateHashStringsFromMirrors(mirroredQueens, n).toMutableSet()

        var totalTerminalBoards: Long = 0
        var totalPrunedBoards: Long = 0
        var totalBoardsProcessed: Long = 0
        // Main loop:
        while (!boardStateStack.empty()) {
            // 1. Pop from stack
            val boardState = boardStateStack.pop()
            totalBoardsProcessed++
            if (totalBoardsProcessed % 100 * n == 0L) {
                println("Processed $totalBoardsProcessed/$totalPermutations boards; found $totalTerminalBoards terminals, pruned $totalPrunedBoards")
            }

            // 2. Filter out terminal boards and count them towards total
            if (boardState.size == n) {
                terminalBoardStates.add(boardState.queenPositions)
                // TODO: generating the hashes here again is kinda wasteful... maybe store them?
                val mirroredQueens2 = getAllMirrors(boardState.queenPositions, n)
                totalTerminalBoards += generateHashStringsFromMirrors(mirroredQueens2, n).size.toLong()
                continue
            }

            // 3. Generate child boards. For each...
            val childBoardStates = generateChildBoardStates(boardState, n)
            for (childBoardState in childBoardStates) {
                // 3.a. Generate all 90-degree rotations for each board, and all their mirrors as well
                //      Using a Set here ensures there's no accidental repetition.
                //      This is a *huge* time saver! It prunes the DFS tree early to a small fraction of its real size.
                val mirroredQueens3 = getAllMirrors(childBoardState.queenPositions, n)
                val childBoardHashes = generateHashStringsFromMirrors(mirroredQueens3, n)

                // 3.b. Filter out the board if any of the hashes is already registered
                //      Since they're all topologically identical, there's no need to keep any of them
                val shouldPrune = childBoardHashes.any { o: String -> boardStateHashes.contains(o) }
                if (shouldPrune) {
                    totalPrunedBoards++
                    continue
                }

                // 3.c. Register all new hashes to avoid repetitions in the future
                boardStateHashes.addAll(childBoardHashes)
                boardStateStack.add(childBoardState)
            }
        }

        // Emit metrics

        // Emit metrics
        terminalBoardStates.forEach { boardState -> printBoard(boardState, n) }
        println("Max board permutations: $totalPermutations")
        println("Total terminal boards: $totalTerminalBoards")
        println("Unique terminal boards identified: " + terminalBoardStates.size)
        println("Total boards processed: $totalBoardsProcessed")
        println("Total boards pruned: $totalPrunedBoards")
    }
}