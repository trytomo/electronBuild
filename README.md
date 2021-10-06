# electronBuild

Pada /app/build.gradle sesuaikan ini

    sourceSets {
        main {
            jniLibs.srcDirs = ['$YOUR_DIRECTORY_HERE/app/src/main/JniLibs']
        }
    }
