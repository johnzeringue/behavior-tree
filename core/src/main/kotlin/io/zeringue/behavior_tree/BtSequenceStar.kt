package io.zeringue.behavior_tree

import io.zeringue.behavior_tree.BtStatus.*

class BtSequenceStar<T>(private vararg val children: BtNode<T>) : BtNode<T> {

    var runIndex = 0

    override fun tick(state: T): BtStatus {
        var result = SUCCESS

        for (i in runIndex until children.size) {
            val status = children[i].tick(state)

            if (status == RUNNING) {
                runIndex = i
                result = RUNNING
                break
            } else if (status == FAILURE) {
                runIndex = 0
                result = FAILURE
                break
            }
        }

        if (result == SUCCESS) runIndex = 0
        return result
    }

}
