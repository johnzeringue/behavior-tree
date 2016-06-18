package io.zeringue.behavior_tree

import io.zeringue.behavior_tree.BtStatus.FAILURE

class BtSelector<T>(private vararg val children: BtNode<T>) : BtNode<T> {

    override fun tick(state: T): BtStatus {
        var result = FAILURE

        for (child in children) {
            val status = child.tick(state)

            if (status != FAILURE) {
                result = status
                break
            }
        }

        return result
    }

}
