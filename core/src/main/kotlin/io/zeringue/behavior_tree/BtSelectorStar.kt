package io.zeringue.behavior_tree

import io.zeringue.behavior_tree.BtStatus.*

class BtSelectorStar<T>(private vararg val children: BtNode<T>): BtNode<T> {

    private var runIndex = 0

    override fun tick(state: T): BtStatus {
        var result = FAILURE

        for (i in runIndex until children.size) {
            val status = children[i].tick(state)

            if (status == RUNNING) {
                runIndex = i
                result = status
                break
            } else if (status == SUCCESS) {
                runIndex = 0
                result = status
                break
            }
        }

        if (result == FAILURE) runIndex = 0
        return result
    }

}
