package io.zeringue.behavior_tree

import io.zeringue.behavior_tree.BtStatus.FAILURE
import io.zeringue.behavior_tree.BtStatus.SUCCESS

abstract class BtCondition<T> : BtNode<T> {

    abstract fun test(state: T): Boolean

    final override fun tick(state: T): BtStatus {
        return if (test(state)) SUCCESS else FAILURE
    }

}
