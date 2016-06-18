package io.zeringue.behavior_tree

import io.zeringue.behavior_tree.test.PlaybackNodeVerifier
import org.junit.Rule
import spock.lang.Specification

import static io.zeringue.behavior_tree.BtStatus.*

class BtSequenceStarSpec extends Specification {

    @Rule
    PlaybackNodeVerifier playbackNode

    def "tick returns SUCCESS without any children"() {
        given:
        def sequence = new BtSequenceStar()

        expect:
        sequence.tick null == SUCCESS
    }

    def "tick returns SUCCESS after all children SUCCESS"() {
        given:
        def sequence = new BtSequenceStar(
                playbackNode.create(SUCCESS),
                playbackNode.create(SUCCESS),
                playbackNode.create(SUCCESS))

        expect:
        sequence.tick null == SUCCESS
    }

    def "tick returns FAILURE on first child FAILURE"() {
        given:
        def sequence = new BtSequenceStar(
                playbackNode.create(SUCCESS),
                playbackNode.create(FAILURE),
                playbackNode.create())

        expect:
        sequence.tick null == FAILURE
    }

    def "tick returns RUNNING on first child RUNNING"() {
        given:
        def sequence = new BtSequenceStar(
                playbackNode.create(SUCCESS),
                playbackNode.create(RUNNING),
                playbackNode.create())

        expect:
        sequence.tick null == RUNNING
    }

    def "second tick starts on last child RUNNING"() {
        given:
        def sequence = new BtSequenceStar(
                playbackNode.create(SUCCESS),
                playbackNode.create(RUNNING, FAILURE),
                playbackNode.create())

        when:
        sequence.tick null

        then:
        sequence.tick null == FAILURE
    }

    def "FAILURE resets sequence"() {
        given:
        def sequence = new BtSequenceStar(
                playbackNode.create(SUCCESS, FAILURE),
                playbackNode.create(RUNNING, FAILURE),
                playbackNode.create())

        when:
        sequence.tick null
        sequence.tick null

        then:
        sequence.tick null == FAILURE
    }

}
