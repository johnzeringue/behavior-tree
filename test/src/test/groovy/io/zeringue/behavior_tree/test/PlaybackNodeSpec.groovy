package io.zeringue.behavior_tree.test

import spock.lang.Specification

import static io.zeringue.behavior_tree.BtStatus.*

class PlaybackNodeSpec extends Specification {

    def "is finished after all statuses are played"() {
        given:
        def node = new PlaybackNode(RUNNING, FAILURE)

        when:
        node.tick null
        node.tick null

        then:
        node.finished
    }

    def "is finished given no statuses"() {
        given:
        def node = new PlaybackNode()

        expect:
        node.finished
    }

    def "is not finished before any statuses are played"() {
        given:
        def node = new PlaybackNode(FAILURE, SUCCESS)

        expect:
        !node.finished
    }

    def "is not finished after some statuses are played"() {
        given:
        def node = new PlaybackNode(RUNNING, SUCCESS)

        when:
        node.tick null

        then:
        !node.finished
    }

    def "plays back #statuses in the order specified"(statuses) {
        given:
        def node = new PlaybackNode(*statuses)

        expect:
        statuses.every { status -> node.tick null == status }

        where:
        statuses           | _
        [SUCCESS]          | _
        [FAILURE]          | _
        [RUNNING]          | _
        [SUCCESS, SUCCESS] | _
        [SUCCESS, FAILURE] | _
        [SUCCESS, RUNNING] | _
        [FAILURE, SUCCESS] | _
        [FAILURE, FAILURE] | _
        [FAILURE, RUNNING] | _
        [RUNNING, SUCCESS] | _
        [RUNNING, FAILURE] | _
        [RUNNING, RUNNING] | _
    }

    def "throws exception on tick after all statuses are played"() {
        given:
        def node = new PlaybackNode(SUCCESS, FAILURE)

        when:
        node.tick null
        node.tick null
        node.tick null

        then:
        thrown IllegalStateException
    }

}
