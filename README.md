# electronBuild

Pada /app/build.gradle sesuaikan ini

    sourceSets {
        main {
            jniLibs.srcDirs = ['$YOUR_DIRECTORY_HERE/stock-management/app/src/main/JniLibs']
        }
    }
