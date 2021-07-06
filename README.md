# Reproducer for [graal#3438](https://github.com/oracle/graal/issues/3438)

The expected outcome is for `net.ckozak.Main` to print `success` and exit with status zero. This occurs on hotspot,
however the project does not build using graalvm.

### To reproduce using graal 21.1.0

Attempt to build a native image.
```bash
./gradlew nativeImage
```

This shouuld have produced an artifact `./build/graal/reproducer` which can be run successfully. Unfortunately the build fails with:

```log
[graalvm-serviceloader-reproducer]$ ./gradlew nativeImage
To honour the JVM settings for this build a single-use Daemon process will be forked. See https://docs.gradle.org/6.9/userguide/gradle_daemon.html#sec:disabling_the_daemon.
Daemon will be stopped at the end of the build 
<====
> Task :nativeImage
[reproducer:169730]    classlist:     935.21 ms,  0.96 GB
[reproducer:169730]        (cap):     763.50 ms,  0.96 GB
[reproducer:169730]        setup:   1,920.93 ms,  0.96 GB
[reproducer:169730]     analysis:   3,976.19 ms,  1.20 GB
Fatal error:java.lang.IllegalArgumentException: java.lang.IllegalArgumentException: Class net.ckozak.ExampleAbstractImplementation cannot be instantiated reflectively. It must be a non-abstract instance type.
        at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
        at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)
        at java.base/jdk.internal.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
        at java.base/java.lang.reflect.Constructor.newInstance(Constructor.java:490)
        at java.base/java.util.concurrent.ForkJoinTask.getThrowableException(ForkJoinTask.java:600)
        at java.base/java.util.concurrent.ForkJoinTask.get(ForkJoinTask.java:1006)
        at com.oracle.svm.hosted.NativeImageGenerator.run(NativeImageGenerator.java:499)
        at com.oracle.svm.hosted.NativeImageGeneratorRunner.buildImage(NativeImageGeneratorRunner.java:370)
        at com.oracle.svm.hosted.NativeImageGeneratorRunner.build(NativeImageGeneratorRunner.java:531)
        at com.oracle.svm.hosted.NativeImageGeneratorRunner.main(NativeImageGeneratorRunner.java:119)
        at com.oracle.svm.hosted.NativeImageGeneratorRunner$JDK9Plus.main(NativeImageGeneratorRunner.java:568)
Caused by: java.lang.IllegalArgumentException: Class net.ckozak.ExampleAbstractImplementation cannot be instantiated reflectively. It must be a non-abstract instance type.
        at org.graalvm.sdk/org.graalvm.nativeimage.hosted.RuntimeReflection.registerForReflectiveInstantiation(RuntimeReflection.java:135)
        at com.oracle.svm.hosted.ServiceLoaderFeature.handleType(ServiceLoaderFeature.java:324)
        at com.oracle.svm.hosted.ServiceLoaderFeature.duringAnalysis(ServiceLoaderFeature.java:178)
        at com.oracle.svm.hosted.NativeImageGenerator.lambda$runPointsToAnalysis$14(NativeImageGenerator.java:765)
        at com.oracle.svm.hosted.FeatureHandler.forEachFeature(FeatureHandler.java:71)
        at com.oracle.svm.hosted.NativeImageGenerator.runPointsToAnalysis(NativeImageGenerator.java:765)
        at com.oracle.svm.hosted.NativeImageGenerator.doRun(NativeImageGenerator.java:582)
        at com.oracle.svm.hosted.NativeImageGenerator.lambda$run$2(NativeImageGenerator.java:495)
        at java.base/java.util.concurrent.ForkJoinTask$AdaptedRunnableAction.exec(ForkJoinTask.java:1407)
        at java.base/java.util.concurrent.ForkJoinTask.doExec(ForkJoinTask.java:290)
        at java.base/java.util.concurrent.ForkJoinPool$WorkQueue.topLevelExec(ForkJoinPool.java:1020)
        at java.base/java.util.concurrent.ForkJoinPool.scan(ForkJoinPool.java:1656)
        at java.base/java.util.concurrent.ForkJoinPool.runWorker(ForkJoinPool.java:1594)
        at java.base/java.util.concurrent.ForkJoinWorkerThread.run(ForkJoinWorkerThread.java:183)
Error: Image build request failed with exit status 1

> Task :nativeImage FAILED

FAILURE: Build failed with an exception.
```

### Running on Hotspot

This will compile and run the project:
```bash
./gradlew run
```

## IDE configuration

### Intellij Idea

Either use gradle integration (preferred) or run the `idea` gradle task (`./gradlew idea`) to create an `.ipr` file which can be opened with the IDE.

### Eclipse

Run the `./graldew eclipse` task to generate project files which may be opened using the IDE.