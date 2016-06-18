package io.zeringue.behavior_tree.test

import io.zeringue.behavior_tree.BtNode
import io.zeringue.behavior_tree.BtStatus

class PlaybackNode<T>(private vararg val statuses: BtStatus) : BtNode<T> {

    private var statusIndex = 0

    val isFinished: Boolean
        get() = statusIndex == statuses.size

    override fun tick(state: T): BtStatus {
        if (isFinished) throw IllegalStateException()
        return statuses[statusIndex++]
    }

}