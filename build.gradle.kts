plugins {
    `java-library`
    `maven-publish`
    idea
}

group = "me.tecc"
version = "0.1.0-alpha"

repositories {
    mavenCentral()
    maven {
        name = "sonatype-oss"
        url = uri("https://oss.sonatype.org/content/groups/public")
    }
}

dependencies {
    compileOnly("org.jetbrains:annotations:23.0.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    // testImplementation("org.yaml:snakeyaml:1.30") // for better request parsing tests and stuff, so they don't have to be hard-coded
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8

    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("main") {
            from(components["java"])

            pom {
                groupId = project.group.toString()
                artifactId = project.name
                version = project.version.toString()

                name.set("tecc's HTTP utilities")
                description.set("Simple HTTP utilities. Directed as client and server developers.")
                url.set("https://tecc.me/docs/httputils");

                licenses {
                    license {
                        name.set("The BSD 3-Clause \"New\" or \"Revised\" Licence (BSD)")
                        url.set("https://opensource.org/licenses/BSD-3-Clause")
                    }
                }

                developers {
                    developer {
                        id.set("tecc")
                        email.set("tecc@tecc.me")
                    }
                }
                scm {
                    connection.set("scm:git:https://github.com/tecc/HttpUtils.git")
                    developerConnection.set("scm:git:ssh://github.com/tecc/HttpUtils.git")
                    url.set("https://github.com/tecc/HttpUtils")
                }
            }
        }
    }
    repositories {
        maven {
            name = "tm"
            url = uri(layout.buildDirectory.dir("test"))
        }
    }
}