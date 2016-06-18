package io.zeringue.behavior_tree.test

import io.zeringue.behavior_tree.BtStatus
import org.junit.Assert.assertTrue
import org.junit.rules.Verifier

class PlaybackNodeVerifier : Verifier() {

    private val nodes = mutableListOf<PlaybackNode<*>>()

    fun <T> create(vararg statuses: BtStatus): PlaybackNode<T> {
        return PlaybackNode<T>(*statuses).apply { nodes.add(this) }
    }

    override fun verify() {
        nodes.forEach { node -> assertTrue(node.isFinished) }
        nodes.clear()
    }

}