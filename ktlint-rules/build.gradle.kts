plugins {
    kotlin("jvm")
}

dependencies {
    compileOnly("com.pinterest.ktlint:ktlint-cli-ruleset-core:1.0.1")
    compileOnly("com.pinterest.ktlint:ktlint-rule-engine-core:1.0.1")
    
    testImplementation("com.pinterest.ktlint:ktlint-cli-ruleset-core:1.0.1")
    testImplementation("com.pinterest.ktlint:ktlint-rule-engine-core:1.0.1")
    testImplementation("com.pinterest.ktlint:ktlint-test:1.0.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testImplementation("org.assertj:assertj-core:3.24.2")
}

tasks.test {
    useJUnitPlatform()
}