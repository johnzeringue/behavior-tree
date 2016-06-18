package io.zeringue.behavior_tree

import io.zeringue.behavior_tree.test.PlaybackNodeVerifier
import org.junit.Rule
import spock.lang.Specification

import static io.zeringue.behavior_tree.BtStatus.*

class BtSequenceSpec extends Specification {

    @Rule
    PlaybackNodeVerifier playbackNode

    def "tick returns SUCCESS without any children"() {
        given:
        def sequence = new BtSequence()

        expect:
        sequence.tick null == SUCCESS
    }

    def "tick returns SUCCESS after all children SUCCESS"() {
        given:
        def sequence = new BtSequence(
                playbackNode.create(SUCCESS),
                playbackNode.create(SUCCESS),
                playbackNode.create(SUCCESS))

        expect:
        sequence.tick null == SUCCESS
    }

    def "tick returns FAILURE on first child FAILURE"() {
        given:
        def sequence = new BtSequence(
                playbackNode.create(SUCCESS),
                playbackNode.create(FAILURE),
                playbackNode.create())

        expect:
        sequence.tick null == FAILURE
    }

    def "tick returns RUNNING on first child RUNNING"() {
        given:
        def sequence = new BtSequence(
                playbackNode.create(SUCCESS),
                playbackNode.create(RUNNING),
                playbackNode.create())

        expect:
        sequence.tick null == RUNNING
    }

}
