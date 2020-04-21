plugins {
    // See https://github.com/gradle/kotlin-dsl-samples/issues/1269#issuecomment-443991084.
    // Since we only use singletons in our Kotlin code we don't need to use the
    // "full blown kotlin-dsl" plugin. We can simply replace it with the default kotlin JVM plugin
    // as we have down below: `kotlin-dsl`
    `kotlin-dsl`
}

repositories {
    jcenter()
}