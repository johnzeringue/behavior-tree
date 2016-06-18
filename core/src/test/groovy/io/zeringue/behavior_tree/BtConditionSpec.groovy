package io.zeringue.behavior_tree

import spock.lang.Specification

class BtConditionSpec extends Specification {

    def "tick returns SUCCESS if test is true"() {
        given:
        BtCondition<Void> condition = { true }

        expect:
        condition.tick(null) == BtStatus.SUCCESS
    }

    def "tick returns FAILURE if test is false"() {
        given:
        BtCondition<Void> condition = { false }

        expect:
        condition.tick(null) == BtStatus.FAILURE
    }

}
