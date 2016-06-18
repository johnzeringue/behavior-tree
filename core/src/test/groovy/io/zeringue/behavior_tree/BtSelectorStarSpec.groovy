package io.zeringue.behavior_tree

import io.zeringue.behavior_tree.test.PlaybackNodeVerifier
import org.junit.Rule
import spock.lang.Specification

import static io.zeringue.behavior_tree.BtStatus.*

class BtSelectorStarSpec extends Specification {

    @Rule
    PlaybackNodeVerifier playbackNode

    def "tick returns FAILURE without any children"() {
        given:
        def selector = new BtSelectorStar()

        expect:
        selector.tick null == FAILURE
    }

    def "tick returns SUCCESS on first child SUCCESS"() {
        given:
        def selector = new BtSelectorStar(
                playbackNode.create(FAILURE),
                playbackNode.create(SUCCESS),
                playbackNode.create())

        expect:
        selector.tick null == SUCCESS
    }

    def "tick returns FAILURE after all children FAILURE"() {
        given:
        def selector = new BtSelectorStar(
                playbackNode.create(FAILURE),
                playbackNode.create(FAILURE),
                playbackNode.create(FAILURE))

        expect:
        selector.tick null == FAILURE
    }

    def "tick returns RUNNING on first child RUNNING"() {
        given:
        def selector = new BtSelectorStar(
                playbackNode.create(FAILURE),
                playbackNode.create(RUNNING),
                playbackNode.create())

        expect:
        selector.tick null == RUNNING
    }

    def "second tick starts on last child RUNNING"() {
        given:
        def selector = new BtSelectorStar(
                playbackNode.create(FAILURE),
                playbackNode.create(RUNNING, FAILURE),
                playbackNode.create(SUCCESS))

        when:
        selector.tick null

        then:
        selector.tick null == SUCCESS
    }

    def "SUCCESS resets selector"() {
        given:
        def selector = new BtSelectorStar(
                playbackNode.create(FAILURE, SUCCESS),
                playbackNode.create(RUNNING, FAILURE),
                playbackNode.create(SUCCESS))

        when:
        selector.tick null
        selector.tick null

        then:
        selector.tick null == SUCCESS
    }

}
