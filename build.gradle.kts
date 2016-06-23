buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:1.0.2"
    }
}

subprojects {
    apply {
        plugin("groovy")
        plugin("kotlin")
        plugin("maven")
    }

    repositories {
        mavenCentral()
    }

    dependencies {
        compile("org.jetbrains.kotlin:kotlin-stdlib:1.0.2")

        testCompile("org.codehaus.groovy:groovy-all:2.4.7")
        testCompile("org.spockframework:spock-core:1.0-groovy-2.4")
    }
}
