package io.zeringue.behavior_tree

interface BtNode<T> {

    fun tick(state: T): BtStatus

}
