package io.zeringue.behavior_tree

import io.zeringue.behavior_tree.BtStatus.SUCCESS

class BtSequence<T>(private vararg val children: BtNode<T>) : BtNode<T> {

    override fun tick(state: T): BtStatus {
        var result = SUCCESS

        for (child in children) {
            val status = child.tick(state)

            if (status != SUCCESS) {
                result = status
                break
            }
        }

        return result
    }

}
