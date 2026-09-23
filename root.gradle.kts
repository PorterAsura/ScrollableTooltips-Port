import gg.essential.gradle.util.versionFromBuildIdAndBranch

plugins {
    kotlin("jvm") version "2.3.0" apply false
    id("gg.essential.multi-version.root")
    id("gg.essential.loom") version "1.13.44" apply false
}

version = "1.4.2"

preprocess {
    strictExtraMappings.set(true)

    val fabric11902 = createNode("1.19.2-fabric", 11902, "yarn")
    val fabric12006 = createNode("1.20.6-fabric", 12006, "yarn")
    val fabric12105 = createNode("1.21.5-fabric", 12105, "yarn")
    val fabric12106 = createNode("1.21.6-fabric", 12106, "yarn")
    val fabric12107 = createNode("1.21.7-fabric", 12107, "yarn")
    val fabric12109 = createNode("1.21.9-fabric", 12109, "yarn")
    val fabric12111 = createNode("1.21.11-fabric", 12111, "yarn")
    val fabric262 = createNode("26.2-fabric", 26200, "official")

    val forge11902 = createNode("1.19.2-forge", 11902, "srg")
    val forge12006 = createNode("1.20.6-forge", 12006, "srg")
    val forge12105 = createNode("1.21.5-forge", 12105, "srg")

    val neoForge12006 = createNode("1.20.6-neoforge", 12006, "srg")
    val neoForge12105 = createNode("1.21.5-neoforge", 12105, "srg")

    fabric12006.link(fabric11902)
    fabric12105.link(fabric12006)
    fabric12106.link(fabric12105)
    fabric12107.link(fabric12106)
    fabric12109.link(fabric12107)
//    fabric12110.link(fabric12109)
    fabric12111.link(fabric12109)

    forge11902.link(fabric11902)
    forge12006.link(forge11902)
    forge12105.link(forge12006)

    neoForge12006.link(forge12006)
    neoForge12105.link(forge12105)
}