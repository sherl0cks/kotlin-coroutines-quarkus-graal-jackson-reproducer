# Reproducer

## Primary Issue
This a modified version of this codebase to reproduce an issue with Jackson serialization that occurs when building a 
native image from a kotlin/quarkus app that uses coroutines. The issue is NOT present when coroutines are removed from 
the classpath, either by removing the maven dependencies or removing references to `runBlocking` such that Graal
removes the coroutine classes during native image build.

The primary issue has been reproduced with the following setups:

- Quarkus 1.1.1.Final / Graal 19.2.1 / Kotlin 1.3.21 / Kotlin Coroutines 1.3.3
- Quarkus 1.3.0.Final / Graal 19.2.1 / Kotlin 1.3.61 / Kotlin Coroutines 1.3.3
- Quarkus 1.3.0.Final / Graal 19.3.1 / Kotlin 1.3.61 / Kotlin Coroutines 1.3.3
- Quarkus 1.3.0.Final / Graal 20.0.0 / Kotlin 1.3.61 / Kotlin Coroutines 1.3.3
- Quarkus 1.3.0.Final / Graal 20.0.0 / Kotlin 1.3.70 / Kotlin Coroutines 1.3.5
  - Done by overriding Quarkus BOM managed version for Kotlin dependencies

## Secondary Issue 
There is a secondary issue discovered in the process of creating this reproducer, which is that the current Quarkus BOM
does not sufficiently manage the Kotlin dependencies needed for coroutines. Kotlin Coroutines releases depend on a
specific version of the Kotlins std-lib. Thus, if Quarkus plans to be compatble with Kotlin coroutines 
(which is unclear at this moment), then the quarkus BOM needs to include coroutines as managed dependencies.

## Important Related Issues
- https://github.com/oracle/graal/issues/366