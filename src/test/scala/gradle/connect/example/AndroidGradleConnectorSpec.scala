package gradle.connect.example

import java.io.File

import com.android.builder.model.{AndroidProject, ArtifactMetaData}
import org.gradle.tooling.GradleConnector
import org.scalatest.FlatSpec

import scala.collection.JavaConverters._

class AndroidGradleConnectorSpec extends FlatSpec {
  val BUILD_GENERATED_DIR = "testData/android-app/build/generated"
  val APP_DIR = "testData/android-app/app"
  val APP_SRC_DIR = s"$APP_DIR/src"
  val APP_BUILD_DIR = s"$APP_DIR/build"
  val APP_BUILD_GENERATED_DIR = s"$APP_BUILD_DIR/generated"
  val APP_BUILD_INTERMEDIATES_DIR = s"$APP_BUILD_DIR/intermediates"

  "GradleConnector" should "return AndroidProject model" in {
    val projectDir = new File("testData/android-app")
    val appDir = new File(projectDir, "app")
    val connector = GradleConnector.newConnector()
    val project = connector.forProjectDirectory(appDir).connect()
    val android = project.getModel(classOf[AndroidProject])

    android.getAaptOptions match {
      case aaptOptions =>
        assert(aaptOptions.getAdditionalParameters == null)
        assert(!aaptOptions.getFailOnMissingConfigEntry)
        assert(aaptOptions.getIgnoreAssets == null)
        assert(aaptOptions.getNoCompress == null)
    }

    assert(android.getApiVersion == 3)
    assert(!android.getBootClasspath.isEmpty)
    assert(android.getBuildFolder.getAbsolutePath.contains(APP_BUILD_DIR))
    assert(android.getBuildToolsVersion == "24.0.0")

    android.getBuildTypes.asScala.toList match {
      case buildTypes =>
        assert(buildTypes.size == 2)
        buildTypes.head match {
          case debug =>
            debug.getBuildType match {
              case buildType =>
                assert(buildType.getName == "debug")
                assert(buildType.getRenderscriptOptimLevel == 3)
                assert(buildType.getSigningConfig == null)
                assert(buildType.getVersionNameSuffix == null)
                assert(buildType.isDebuggable)
                assert(!buildType.isEmbedMicroApp)
                assert(!buildType.isJniDebuggable)
                assert(!buildType.isMinifyEnabled)
                assert(!buildType.isPseudoLocalesEnabled)
                assert(!buildType.isRenderscriptDebuggable)
                assert(!buildType.isTestCoverageEnabled)
                assert(buildType.isZipAlignEnabled)
                assert(buildType.getApplicationIdSuffix == null)
                assert(buildType.getBuildConfigFields.isEmpty)
                assert(buildType.getConsumerProguardFiles.isEmpty)
                assert(buildType.getJarJarRuleFiles.isEmpty)
                assert(buildType.getManifestPlaceholders.isEmpty)
                assert(buildType.getMultiDexEnabled == null)
                assert(buildType.getMultiDexKeepFile == null)
                assert(buildType.getMultiDexKeepProguard == null)
                assert(buildType.getProguardFiles.isEmpty)
                assert(buildType.getResValues.isEmpty)
                assert(buildType.getTestProguardFiles.isEmpty)
            }
            debug.getExtraSourceProviders.asScala match {
              case extraSourceProviders =>
                assert(extraSourceProviders.size == 1)

                val extraSourceProvider = extraSourceProviders.head
                assert(extraSourceProvider.getArtifactName == "_unit_test_")
                extraSourceProvider.getSourceProvider match {
                  case sourceProvider =>
                    assert(sourceProvider.getAidlDirectories.size() == 1)
                    assert(sourceProvider.getAidlDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testDebug/aidl"))
                    assert(sourceProvider.getAssetsDirectories.size() == 1)
                    assert(sourceProvider.getAssetsDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testDebug/assets"))
                    assert(sourceProvider.getCDirectories.size() == 1)
                    assert(sourceProvider.getCDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testDebug/jni"))
                    assert(sourceProvider.getCppDirectories.size() == 1)
                    assert(sourceProvider.getCppDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testDebug/jni"))
                    assert(sourceProvider.getJavaDirectories.size() == 1)
                    assert(sourceProvider.getJavaDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testDebug/java"))
                    assert(sourceProvider.getJniLibsDirectories.size() == 1)
                    assert(sourceProvider.getJniLibsDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testDebug/jniLibs"))
                    assert(sourceProvider.getManifestFile.getAbsolutePath.contains(s"$APP_SRC_DIR/testDebug/AndroidManifest.xml"))
                    assert(sourceProvider.getName == "testDebug")
                    assert(sourceProvider.getRenderscriptDirectories.size() == 1)
                    assert(sourceProvider.getRenderscriptDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testDebug/rs"))
                    assert(sourceProvider.getResDirectories.size() == 1)
                    assert(sourceProvider.getResDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testDebug/res"))
                    assert(sourceProvider.getResourcesDirectories.size() == 1)
                    assert(sourceProvider.getResourcesDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testDebug/resources"))
                    assert(sourceProvider.getShadersDirectories.size() == 1)
                    assert(sourceProvider.getShadersDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testDebug/shaders"))
                }
            }
            debug.getSourceProvider match {
              case sourceProvider =>
                assert(sourceProvider.getAidlDirectories.size() == 1)
                assert(sourceProvider.getAidlDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/debug/aidl"))
                assert(sourceProvider.getAssetsDirectories.size() == 1)
                assert(sourceProvider.getAssetsDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/debug/assets"))
                assert(sourceProvider.getCDirectories.size() == 1)
                assert(sourceProvider.getCDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/debug/jni"))
                assert(sourceProvider.getCppDirectories.size() == 1)
                assert(sourceProvider.getCppDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/debug/jni"))
                assert(sourceProvider.getJavaDirectories.size() == 1)
                assert(sourceProvider.getJavaDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/debug/java"))
                assert(sourceProvider.getJniLibsDirectories.size() == 1)
                assert(sourceProvider.getJniLibsDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/debug/jniLibs"))
                assert(sourceProvider.getManifestFile.getAbsolutePath.contains(s"$APP_SRC_DIR/debug/AndroidManifest.xml"))
                assert(sourceProvider.getName == "debug")
                assert(sourceProvider.getRenderscriptDirectories.size() == 1)
                assert(sourceProvider.getRenderscriptDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/debug/rs"))
                assert(sourceProvider.getResDirectories.size() == 1)
                assert(sourceProvider.getResDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/debug/res"))
                assert(sourceProvider.getResourcesDirectories.size() == 1)
                assert(sourceProvider.getResourcesDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/debug/resources"))
                assert(sourceProvider.getShadersDirectories.size() == 1)
                assert(sourceProvider.getShadersDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/debug/shaders"))
            }
        }

        buildTypes.last match {
          case release =>
            release.getBuildType match {
              case buildType =>
                assert(buildType.getName == "release")
                assert(buildType.getRenderscriptOptimLevel == 3)
                assert(buildType.getSigningConfig == null)
                assert(buildType.getVersionNameSuffix == null)
                assert(!buildType.isDebuggable)
                assert(buildType.isEmbedMicroApp)
                assert(!buildType.isJniDebuggable)
                assert(!buildType.isMinifyEnabled)
                assert(!buildType.isPseudoLocalesEnabled)
                assert(!buildType.isRenderscriptDebuggable)
                assert(!buildType.isTestCoverageEnabled)
                assert(buildType.isZipAlignEnabled)
                assert(buildType.getApplicationIdSuffix == null)
                assert(buildType.getBuildConfigFields.isEmpty)
                assert(buildType.getConsumerProguardFiles.isEmpty)
                assert(buildType.getJarJarRuleFiles.isEmpty)
                assert(buildType.getManifestPlaceholders.isEmpty)
                assert(buildType.getMultiDexEnabled == null)
                assert(buildType.getMultiDexKeepFile == null)
                assert(buildType.getMultiDexKeepProguard == null)
                assert(buildType.getProguardFiles.isEmpty)
                assert(buildType.getResValues.isEmpty)
                assert(buildType.getTestProguardFiles.isEmpty)
            }
            release.getExtraSourceProviders.asScala match {
              case extraSourceProviders =>
                assert(extraSourceProviders.size == 1)

                val extraSourceProvider = extraSourceProviders.head
                assert(extraSourceProvider.getArtifactName == "_unit_test_")
                extraSourceProvider.getSourceProvider match {
                  case sourceProvider =>
                    assert(sourceProvider.getAidlDirectories.size() == 1)
                    assert(sourceProvider.getAidlDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testRelease/aidl"))
                    assert(sourceProvider.getAssetsDirectories.size() == 1)
                    assert(sourceProvider.getAssetsDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testRelease/assets"))
                    assert(sourceProvider.getCDirectories.size() == 1)
                    assert(sourceProvider.getCDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testRelease/jni"))
                    assert(sourceProvider.getCppDirectories.size() == 1)
                    assert(sourceProvider.getCppDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testRelease/jni"))
                    assert(sourceProvider.getJavaDirectories.size() == 1)
                    assert(sourceProvider.getJavaDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testRelease/java"))
                    assert(sourceProvider.getJniLibsDirectories.size() == 1)
                    assert(sourceProvider.getJniLibsDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testRelease/jniLibs"))
                    assert(sourceProvider.getManifestFile.getAbsolutePath.contains(s"$APP_SRC_DIR/testRelease/AndroidManifest.xml"))
                    assert(sourceProvider.getName == "testRelease")
                    assert(sourceProvider.getRenderscriptDirectories.size() == 1)
                    assert(sourceProvider.getRenderscriptDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testRelease/rs"))
                    assert(sourceProvider.getResDirectories.size() == 1)
                    assert(sourceProvider.getResDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testRelease/res"))
                    assert(sourceProvider.getResourcesDirectories.size() == 1)
                    assert(sourceProvider.getResourcesDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testRelease/resources"))
                    assert(sourceProvider.getShadersDirectories.size() == 1)
                    assert(sourceProvider.getShadersDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testRelease/shaders"))
                }
            }
        }
    }

    assert(android.getCompileTarget == "android-24")

    android.getDefaultConfig match {
      case defaultConfig =>
        val extraSourceProviders = defaultConfig.getExtraSourceProviders.asScala
        assert(extraSourceProviders.size == 2)
        extraSourceProviders.head match {
          case androidTest =>
            androidTest.getArtifactName
            androidTest.getSourceProvider match {
              case sourceProvider =>
                assert(sourceProvider.getAidlDirectories.size() == 1)
                assert(sourceProvider.getAidlDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/androidTest/aidl"))
                assert(sourceProvider.getAssetsDirectories.size() == 1)
                assert(sourceProvider.getAssetsDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/androidTest/assets"))
                assert(sourceProvider.getCDirectories.size() == 1)
                assert(sourceProvider.getCDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/androidTest/jni"))
                assert(sourceProvider.getCppDirectories.size() == 1)
                assert(sourceProvider.getCppDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/androidTest/jni"))
                assert(sourceProvider.getJavaDirectories.size() == 1)
                assert(sourceProvider.getJavaDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/androidTest/java"))
                assert(sourceProvider.getJniLibsDirectories.size() == 1)
                assert(sourceProvider.getJniLibsDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/androidTest/jniLibs"))
                assert(sourceProvider.getManifestFile.getAbsolutePath.contains(s"$APP_SRC_DIR/androidTest/AndroidManifest.xml"))
                assert(sourceProvider.getName == "androidTest")
                assert(sourceProvider.getRenderscriptDirectories.size() == 1)
                assert(sourceProvider.getRenderscriptDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/androidTest/rs"))
                assert(sourceProvider.getResDirectories.size() == 1)
                assert(sourceProvider.getResDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/androidTest/res"))
                assert(sourceProvider.getResourcesDirectories.size() == 1)
                assert(sourceProvider.getResourcesDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/androidTest/resources"))
                assert(sourceProvider.getShadersDirectories.size() == 1)
                assert(sourceProvider.getShadersDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/androidTest/shaders"))
            }
        }

        extraSourceProviders.last match {
          case test =>
            test.getArtifactName
            test.getSourceProvider match {
              case sourceProvider =>
                assert(sourceProvider.getAidlDirectories.size() == 1)
                assert(sourceProvider.getAidlDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/test/aidl"))
                assert(sourceProvider.getAssetsDirectories.size() == 1)
                assert(sourceProvider.getAssetsDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/test/assets"))
                assert(sourceProvider.getCDirectories.size() == 1)
                assert(sourceProvider.getCDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/test/jni"))
                assert(sourceProvider.getCppDirectories.size() == 1)
                assert(sourceProvider.getCppDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/test/jni"))
                assert(sourceProvider.getJavaDirectories.size() == 1)
                assert(sourceProvider.getJavaDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/test/java"))
                assert(sourceProvider.getJniLibsDirectories.size() == 1)
                assert(sourceProvider.getJniLibsDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/test/jniLibs"))
                assert(sourceProvider.getManifestFile.getAbsolutePath.contains(s"$APP_SRC_DIR/test/AndroidManifest.xml"))
                assert(sourceProvider.getName == "test")
                assert(sourceProvider.getRenderscriptDirectories.size() == 1)
                assert(sourceProvider.getRenderscriptDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/test/rs"))
                assert(sourceProvider.getResDirectories.size() == 1)
                assert(sourceProvider.getResDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/test/res"))
                assert(sourceProvider.getResourcesDirectories.size() == 1)
                assert(sourceProvider.getResourcesDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/test/resources"))
                assert(sourceProvider.getShadersDirectories.size() == 1)
                assert(sourceProvider.getShadersDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/test/shaders"))
            }
        }

        defaultConfig.getProductFlavor match {
          case defaultProductFlavor =>
            assert(defaultProductFlavor.getApplicationId == "com.epy0n0ff.example")
            assert(defaultProductFlavor.getMaxSdkVersion == null)

            defaultProductFlavor.getMinSdkVersion match {
              case minSdkVersion =>
                assert(minSdkVersion.getApiLevel == 14)
                assert(minSdkVersion.getApiString == "14")
                assert(minSdkVersion.getCodename == null)
            }

            assert(defaultProductFlavor.getName == "main")
            assert(defaultProductFlavor.getRenderscriptNdkModeEnabled == null)
            assert(defaultProductFlavor.getRenderscriptSupportModeEnabled == null)
            assert(defaultProductFlavor.getRenderscriptTargetApi == null)
            assert(defaultProductFlavor.getResourceConfigurations.isEmpty)
            assert(defaultProductFlavor.getSigningConfig == null)

            defaultProductFlavor.getTargetSdkVersion match {
              case targetSdkVersion =>
                assert(targetSdkVersion.getApiLevel == 24)
                assert(targetSdkVersion.getApiString == "24")
                assert(targetSdkVersion.getCodename == null)
            }

            assert(defaultProductFlavor.getTestApplicationId == null)
            assert(defaultProductFlavor.getTestFunctionalTest == null)
            assert(defaultProductFlavor.getTestHandleProfiling == null)
            assert(defaultProductFlavor.getTestInstrumentationRunner == null)
            assert(defaultProductFlavor.getTestInstrumentationRunnerArguments.isEmpty)

            defaultProductFlavor.getVectorDrawables match {
              case vectorDrawables =>
                val generateDensities = vectorDrawables.getGeneratedDensities.asScala
                assert(generateDensities.size == 6)
                assert(generateDensities.contains("ldpi"))
                assert(generateDensities.contains("mdpi"))
                assert(generateDensities.contains("hdpi"))
                assert(generateDensities.contains("xhdpi"))
                assert(generateDensities.contains("xxhdpi"))
                assert(generateDensities.contains("xxxhdpi"))
                assert(!vectorDrawables.getUseSupportLibrary)
            }

            assert(defaultProductFlavor.getVersionCode == 1)
            assert(defaultProductFlavor.getVersionName == "1.0")
        }
    }

    android.getExtraArtifacts.asScala match {
      case extraArtifacts =>
        assert(extraArtifacts.size == 2)
        extraArtifacts.head match {
          case androidTest =>
            assert(androidTest.getName == "_android_test_")
            assert(androidTest.getType == ArtifactMetaData.TYPE_ANDROID)
            assert(androidTest.isTest)
        }
        extraArtifacts.last match {
          case unitTest =>
            assert(unitTest.getName == "_unit_test_")
            assert(unitTest.getType == ArtifactMetaData.TYPE_JAVA)
            assert(unitTest.isTest)
        }
    }
    assert(android.getFlavorDimensions.isEmpty)
    assert(android.getFrameworkSources.isEmpty)

    android.getJavaCompileOptions match {
      case javaCompilerOptions =>
        assert(javaCompilerOptions.getEncoding == "UTF-8")
        assert(javaCompilerOptions.getSourceCompatibility == "1.7")
        assert(javaCompilerOptions.getTargetCompatibility == "1.7")
    }

    android.getLintOptions match {
      case lintOptions =>
        assert(lintOptions.getCheck.isEmpty)
        assert(lintOptions.getDisable.isEmpty)
        assert(lintOptions.getEnable.isEmpty)
        assert(lintOptions.getHtmlOutput == null)
        assert(lintOptions.getHtmlReport)
        assert(lintOptions.getLintConfig == null)
        assert(lintOptions.getSeverityOverrides == null)
        assert(lintOptions.getTextOutput == null)
        assert(lintOptions.getXmlOutput == null)
        assert(lintOptions.getXmlReport)
        assert(lintOptions.isAbortOnError)
        assert(lintOptions.isAbsolutePaths)
        assert(!lintOptions.isCheckAllWarnings)
        assert(lintOptions.isExplainIssues)
        assert(!lintOptions.isIgnoreWarnings)
        assert(!lintOptions.isNoLines)
        assert(!lintOptions.isQuiet)
        assert(!lintOptions.isShowAll)
        assert(!lintOptions.isWarningsAsErrors)
    }

    assert(android.getModelVersion == "2.1.2")
    assert(android.getName == "app")
    assert(android.getNativeToolchains.isEmpty)
    assert(android.getPluginGeneration == 1)

    android.getProductFlavors.asScala match {
      case productFlavors =>
        assert(productFlavors.size == 1)
        val productFlavor = productFlavors.head
        productFlavor.getExtraSourceProviders.asScala match {
          case extraSourceProviders =>
            assert(extraSourceProviders.size == 2)
            extraSourceProviders.head match {
              case androidTest =>
                assert(androidTest.getArtifactName == "_android_test_")
                androidTest.getSourceProvider match {
                  case sourceProvider =>
                    assert(sourceProvider.getAidlDirectories.size() == 1)
                    assert(sourceProvider.getAidlDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/androidTestDevelop/aidl"))
                    assert(sourceProvider.getAssetsDirectories.size() == 1)
                    assert(sourceProvider.getAssetsDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/androidTestDevelop/assets"))
                    assert(sourceProvider.getCDirectories.size() == 1)
                    assert(sourceProvider.getCDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/androidTestDevelop/jni"))
                    assert(sourceProvider.getCppDirectories.size() == 1)
                    assert(sourceProvider.getCppDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/androidTestDevelop/jni"))
                    assert(sourceProvider.getJavaDirectories.size() == 1)
                    assert(sourceProvider.getJavaDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/androidTestDevelop/java"))
                    assert(sourceProvider.getJniLibsDirectories.size() == 1)
                    assert(sourceProvider.getJniLibsDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/androidTestDevelop/jniLibs"))
                    assert(sourceProvider.getManifestFile.getAbsolutePath.contains(s"$APP_SRC_DIR/androidTestDevelop/AndroidManifest.xml"))
                    assert(sourceProvider.getName == "androidTestDevelop")
                    assert(sourceProvider.getRenderscriptDirectories.size() == 1)
                    assert(sourceProvider.getRenderscriptDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/androidTestDevelop/rs"))
                    assert(sourceProvider.getResDirectories.size() == 1)
                    assert(sourceProvider.getResDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/androidTestDevelop/res"))
                    assert(sourceProvider.getResourcesDirectories.size() == 1)
                    assert(sourceProvider.getResourcesDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/androidTestDevelop/resources"))
                    assert(sourceProvider.getShadersDirectories.size() == 1)
                    assert(sourceProvider.getShadersDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/androidTestDevelop/shaders"))
                }
            }
            extraSourceProviders.last match {
              case unitTest =>
                assert(unitTest.getArtifactName == "_unit_test_")
                unitTest.getSourceProvider match {
                  case sourceProvider =>
                    assert(sourceProvider.getAidlDirectories.size() == 1)
                    assert(sourceProvider.getAidlDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testDevelop/aidl"))
                    assert(sourceProvider.getAssetsDirectories.size() == 1)
                    assert(sourceProvider.getAssetsDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testDevelop/assets"))
                    assert(sourceProvider.getCDirectories.size() == 1)
                    assert(sourceProvider.getCDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testDevelop/jni"))
                    assert(sourceProvider.getCppDirectories.size() == 1)
                    assert(sourceProvider.getCppDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testDevelop/jni"))
                    assert(sourceProvider.getJavaDirectories.size() == 1)
                    assert(sourceProvider.getJavaDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testDevelop/java"))
                    assert(sourceProvider.getJniLibsDirectories.size() == 1)
                    assert(sourceProvider.getJniLibsDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testDevelop/jniLibs"))
                    assert(sourceProvider.getManifestFile.getAbsolutePath.contains(s"$APP_SRC_DIR/testDevelop/AndroidManifest.xml"))
                    assert(sourceProvider.getName == "testDevelop")
                    assert(sourceProvider.getRenderscriptDirectories.size() == 1)
                    assert(sourceProvider.getRenderscriptDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testDevelop/rs"))
                    assert(sourceProvider.getResDirectories.size() == 1)
                    assert(sourceProvider.getResDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testDevelop/res"))
                    assert(sourceProvider.getResourcesDirectories.size() == 1)
                    assert(sourceProvider.getResourcesDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testDevelop/resources"))
                    assert(sourceProvider.getShadersDirectories.size() == 1)
                    assert(sourceProvider.getShadersDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testDevelop/shaders"))
                }
            }
        }
        productFlavor.getProductFlavor match {
          case developFlavor =>
            assert(developFlavor.getApplicationId == null)
            assert(developFlavor.getMaxSdkVersion == null)
            assert(developFlavor.getMinSdkVersion == null)
            assert(developFlavor.getName == "develop")
            assert(developFlavor.getRenderscriptNdkModeEnabled == null)
            assert(developFlavor.getRenderscriptSupportModeEnabled == null)
            assert(developFlavor.getRenderscriptTargetApi == null)
            assert(developFlavor.getResourceConfigurations.isEmpty)
            assert(developFlavor.getSigningConfig == null)
            assert(developFlavor.getTargetSdkVersion == null)
            assert(developFlavor.getTestApplicationId == null)
            assert(developFlavor.getTestFunctionalTest == null)
            assert(developFlavor.getTestHandleProfiling == null)
            assert(developFlavor.getTestInstrumentationRunner == null)
            assert(developFlavor.getTestInstrumentationRunnerArguments.isEmpty)
            developFlavor.getVectorDrawables match {
              case vectorDrawables =>
                assert(vectorDrawables.getGeneratedDensities == null)
                assert(vectorDrawables.getUseSupportLibrary == null)
            }
            assert(developFlavor.getVersionCode == null)
            assert(developFlavor.getVersionName == null)
            assert(developFlavor.getApplicationIdSuffix == null)
            assert(developFlavor.getBuildConfigFields.isEmpty)
            assert(developFlavor.getConsumerProguardFiles.isEmpty)
            assert(developFlavor.getDimension == null)
            assert(developFlavor.getJarJarRuleFiles.isEmpty)
            assert(developFlavor.getManifestPlaceholders.isEmpty)
            assert(developFlavor.getMultiDexEnabled == null)
            assert(developFlavor.getMultiDexKeepFile == null)
            assert(developFlavor.getMultiDexKeepProguard == null)
            assert(developFlavor.getProguardFiles.isEmpty)
            assert(developFlavor.getResValues.isEmpty)
            assert(developFlavor.getTestProguardFiles.isEmpty)
        }
        productFlavor.getSourceProvider match {
          case sourceProvider =>
            assert(sourceProvider.getAidlDirectories.size() == 1)
            assert(sourceProvider.getAidlDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/develop/aidl"))
            assert(sourceProvider.getAssetsDirectories.size() == 1)
            assert(sourceProvider.getAssetsDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/develop/assets"))
            assert(sourceProvider.getCDirectories.size() == 1)
            assert(sourceProvider.getCDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/develop/jni"))
            assert(sourceProvider.getCppDirectories.size() == 1)
            assert(sourceProvider.getCppDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/develop/jni"))
            assert(sourceProvider.getJavaDirectories.size() == 1)
            assert(sourceProvider.getJavaDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/develop/java"))
            assert(sourceProvider.getJniLibsDirectories.size() == 1)
            assert(sourceProvider.getJniLibsDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/develop/jniLibs"))
            assert(sourceProvider.getManifestFile.getAbsolutePath.contains(s"$APP_SRC_DIR/develop/AndroidManifest.xml"))
            assert(sourceProvider.getName == "develop")
            assert(sourceProvider.getRenderscriptDirectories.size() == 1)
            assert(sourceProvider.getRenderscriptDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/develop/rs"))
            assert(sourceProvider.getResDirectories.size() == 1)
            assert(sourceProvider.getResDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/develop/res"))
            assert(sourceProvider.getResourcesDirectories.size() == 1)
            assert(sourceProvider.getResourcesDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/develop/resources"))
            assert(sourceProvider.getShadersDirectories.size() == 1)
            assert(sourceProvider.getShadersDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/develop/shaders"))
        }
    }

    assert(android.getResourcePrefix == null)
    android.getSigningConfigs.asScala match {
      case signingConfigs =>
        assert(signingConfigs.size == 1)

        val signConfig = signingConfigs.head
        assert(signConfig.getKeyAlias == "AndroidDebugKey")
        assert(signConfig.getKeyPassword == "android")
        assert(signConfig.getName == "debug")
        assert(signConfig.getStoreFile.getAbsolutePath == new File(System.getProperty("user.home") + "/.android/debug.keystore").getAbsolutePath)
        assert(signConfig.getStorePassword == "android")
        assert(signConfig.getStoreType == "jks")
        assert(signConfig.isSigningReady)
    }
    assert(android.getSyncIssues.isEmpty)
    android.getVariants.asScala match {
      case variants =>
        assert(variants.size == 2)
        variants.head match {
          case developDebug =>
            assert(developDebug.getBuildType == "debug")
            assert(developDebug.getDisplayName == "develop-debug")
            developDebug.getExtraAndroidArtifacts.asScala match {
              case androidArtifacts =>
                assert(androidArtifacts.size == 1)

                val androidTestArtifact = androidArtifacts.head
                assert(androidTestArtifact.getAbiFilters == null)
                assert(androidTestArtifact.getApplicationId == "com.epy0n0ff.example.test")
                assert(androidTestArtifact.getBuildConfigFields.isEmpty)
                androidTestArtifact.getGeneratedResourceFolders.asScala match {
                  case generatedResourceFolders =>
                    assert(generatedResourceFolders.size == 2)
                    val renderScriptFolder = generatedResourceFolders.head
                    val resValuesFolder = generatedResourceFolders.last
                    assert(renderScriptFolder.getAbsolutePath.contains(s"$APP_BUILD_GENERATED_DIR/res/rs/androidTest/develop/debug"))
                    assert(resValuesFolder.getAbsolutePath.contains(s"$APP_BUILD_GENERATED_DIR/res/resValues/androidTest/develop/debug"))
                }
                androidTestArtifact.getGeneratedSourceFolders.asScala.toList match {
                  case generatedSourceFolders =>
                    assert(generatedSourceFolders.size == 4)
                    val rFolder = generatedSourceFolders.head
                    val aidlFolder = generatedSourceFolders(1)
                    val buildConfigFolder = generatedSourceFolders(2)
                    val rsFolder = generatedSourceFolders(3)
                    assert(rFolder.getAbsolutePath.contains(s"$APP_BUILD_GENERATED_DIR/source/r/androidTest/develop/debug"))
                    assert(aidlFolder.getAbsolutePath.contains(s"$APP_BUILD_GENERATED_DIR/source/aidl/androidTest/develop/debug"))
                    assert(buildConfigFolder.getAbsolutePath.contains(s"$APP_BUILD_GENERATED_DIR/source/buildConfig/androidTest/develop/debug"))
                    assert(rsFolder.getAbsolutePath.contains(s"$APP_BUILD_GENERATED_DIR/source/rs/androidTest/develop/debug"))
                }
                androidTestArtifact.getInstantRun match {
                  case instantRun =>
                    assert(instantRun.getIncrementalAssembleTaskName == "incrementalDevelopDebugAndroidTestSupportDex")
                    assert(instantRun.getInfoFile.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/restart-dex/androidTest/develop/debug/build-info.xml"))
                    assert(!instantRun.isSupportedByArtifact)
                }

                assert(androidTestArtifact.getNativeLibraries.isEmpty)
                androidTestArtifact.getOutputs.asScala.toList match {
                  case outputs =>
                    assert(outputs.size == 1)
                    val androidManifestArtifact = outputs.head
                    assert(androidManifestArtifact.getAssembleTaskName == "assembledevelopDebugAndroidTest")
                    assert(androidManifestArtifact.getGeneratedManifest.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/manifest/androidTest/develop/debug/AndroidManifest.xml"))
                }
                assert(androidTestArtifact.getResValues.isEmpty)
                assert(androidTestArtifact.getSigningConfigName == "debug")
                assert(androidTestArtifact.getSourceGenTaskName == "generateDevelopDebugAndroidTestSources")
                assert(androidTestArtifact.isSigned)
            }
            developDebug.getExtraJavaArtifacts.asScala match {
              case extraJavaArtifacts =>
                assert(extraJavaArtifacts.size == 1)

                val artifact = extraJavaArtifacts.head
                assert(artifact.getMockablePlatformJar.getAbsolutePath.contains(s"BUILD_GENERATED_DIR/mockable-android-24.jar"))
                assert(artifact.getAssembleTaskName == "assembleDevelopDebugUnitTest")
                assert(artifact.getClassesFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/classes/test/develop/debug"))
                assert(artifact.getCompileTaskName == "compileDevelopDebugUnitTestSources")

                val dependency = artifact.getDependencies
                val javaLibraries = dependency.getJavaLibraries.asScala
                assert(javaLibraries.size == 2)
                for (lib <- javaLibraries) {
                  val jar = lib.getJarFile
                  assert(lib.getDependencies.isEmpty)
                  assert(jar.getAbsolutePath.contains(".gradle/caches/"))
                  jar.getName match {
                    case "hamcrest-core-1.3.jar" =>
                    case "junit-4.12.jar" =>
                  }
                  assert(!lib.isProvided)
                  assert(lib.getRequestedCoordinates == null)
                  lib.getResolvedCoordinates.getArtifactId match {
                    case "junit" =>
                    case "hamcrest-core" =>
                  }
                  assert(lib.getResolvedCoordinates.getClassifier == null)
                  lib.getResolvedCoordinates.getGroupId match {
                    case "junit" =>
                    case "org.hamcrest" =>
                  }
                  lib.getResolvedCoordinates.getPackaging match {
                    case "jar" =>
                  }
                  lib.getResolvedCoordinates.getVersion match {
                    case "4.12" =>
                    case "1.3" =>
                  }
                }

                assert(dependency.getLibraries.isEmpty)
                assert(dependency.getProjects.isEmpty)

                assert(artifact.getGeneratedSourceFolders.isEmpty)
                for (taskName <- artifact.getIdeSetupTaskNames.asScala) {
                  taskName match {
                    case "prepareDevelopDebugUnitTestDependencies" =>
                    case "mockableAndroidJar" =>
                  }
                }
                assert(artifact.getJavaResourcesFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/sourceFolderJavaResources/test/develop/debug"))
                assert(artifact.getMultiFlavorSourceProvider == null)
                assert(artifact.getName == "_unit_test_")
                artifact.getVariantSourceProvider match {
                  case sourceProvider =>
                    assert(sourceProvider.getAidlDirectories.size() == 1)
                    assert(sourceProvider.getAidlDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testDevelopDebug/aidl"))
                    assert(sourceProvider.getAssetsDirectories.size() == 1)
                    assert(sourceProvider.getAssetsDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testDevelopDebug/assets"))
                    assert(sourceProvider.getCDirectories.size() == 1)
                    assert(sourceProvider.getCDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testDevelopDebug/jni"))
                    assert(sourceProvider.getCppDirectories.size() == 1)
                    assert(sourceProvider.getCppDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testDevelopDebug/jni"))
                    assert(sourceProvider.getJavaDirectories.size() == 1)
                    assert(sourceProvider.getJavaDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testDevelopDebug/java"))
                    assert(sourceProvider.getJniLibsDirectories.size() == 1)
                    assert(sourceProvider.getJniLibsDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testDevelopDebug/jniLibs"))
                    assert(sourceProvider.getManifestFile.getAbsolutePath.contains(s"$APP_SRC_DIR/testDevelopDebug/AndroidManifest.xml"))
                    assert(sourceProvider.getName == "testDevelopDebug")
                    assert(sourceProvider.getRenderscriptDirectories.size() == 1)
                    assert(sourceProvider.getRenderscriptDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testDevelopDebug/rs"))
                    assert(sourceProvider.getResDirectories.size() == 1)
                    assert(sourceProvider.getResDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testDevelopDebug/res"))
                    assert(sourceProvider.getResourcesDirectories.size() == 1)
                    assert(sourceProvider.getResourcesDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testDevelopDebug/resources"))
                    assert(sourceProvider.getShadersDirectories.size() == 1)
                    assert(sourceProvider.getShadersDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testDevelopDebug/shaders"))
                }
            }
            developDebug.getMainArtifact match {
              case mainArtifact =>
                assert(mainArtifact.getAbiFilters == null)
                assert(mainArtifact.getApplicationId == "com.epy0n0ff.example")
                assert(mainArtifact.getBuildConfigFields.isEmpty)
                mainArtifact.getGeneratedResourceFolders.asScala match {
                  case generatedResourceFolders =>
                    assert(generatedResourceFolders.size == 2)
                    assert(generatedResourceFolders.head.getAbsolutePath.contains(s"$APP_BUILD_GENERATED_DIR/res/rs/develop/debug"))
                    assert(generatedResourceFolders.last.getAbsolutePath.contains(s"$APP_BUILD_GENERATED_DIR/res/resValues/develop/debug"))
                }
                val folders = mainArtifact.getGeneratedSourceFolders.asScala
                assert(folders.size == 4)
                val r = s".*$APP_BUILD_GENERATED_DIR/source/r/develop/debug$$".r
                val aidl = s".*$APP_BUILD_GENERATED_DIR/source/aidl/develop/debug$$".r
                val buildConfig = s".*$APP_BUILD_GENERATED_DIR/source/buildConfig/develop/debug$$".r
                val rs = s".*$APP_BUILD_GENERATED_DIR/source/rs/develop/debug$$".r
                for (genSource <- folders) genSource.getAbsolutePath match {
                  case r(_*) =>
                  case aidl(_*) =>
                  case buildConfig(_*) =>
                  case rs(_*) =>
                }
                mainArtifact.getInstantRun match {
                  case instantRun =>
                    assert(instantRun.getIncrementalAssembleTaskName == "incrementalDevelopDebugSupportDex")
                    assert(instantRun.getInfoFile.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/restart-dex/develop/debug/build-info.xml"))
                    assert(instantRun.isSupportedByArtifact)
                }

                assert(mainArtifact.getNativeLibraries.isEmpty)

                mainArtifact.getOutputs.asScala match {
                  case outputs =>
                    assert(outputs.size == 1)
                    outputs.head match {
                      case androidArtifactOutput =>
                        assert(androidArtifactOutput.getAssembleTaskName == "assembledevelopDebug")
                        assert(androidArtifactOutput.getGeneratedManifest.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/manifests/full/develop/debug/AndroidManifest.xml"))
                    }
                }

                assert(mainArtifact.getResValues.isEmpty)
                assert(mainArtifact.getSigningConfigName == "debug")
                assert(mainArtifact.getSourceGenTaskName == "generateDevelopDebugSources")
                assert(mainArtifact.isSigned)
                assert(mainArtifact.getAssembleTaskName == "assembleDevelopDebug")
                assert(mainArtifact.getClassesFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/classes/develop/debug"))
                assert(mainArtifact.getCompileTaskName == "compileDevelopDebugSources")
                mainArtifact.getDependencies match {
                  case dependencies =>
                    dependencies.getJavaLibraries.asScala match {
                      case javaLibraries =>
                        assert(javaLibraries.size == 1)
                        val javaLibrary = javaLibraries.head
                        assert(javaLibrary.getDependencies.isEmpty)
                        javaLibrary match {
                          case supportAnnotations =>
                            assert(supportAnnotations.getJarFile.getAbsolutePath.contains("sdk/extras/android/m2repository/com/android/support/support-annotations/"))
                            assert(!supportAnnotations.isProvided)
                            assert(supportAnnotations.getRequestedCoordinates == null)
                            assert(supportAnnotations.getResolvedCoordinates.getArtifactId == "support-annotations")
                            assert(supportAnnotations.getResolvedCoordinates.getClassifier == null)
                            assert(supportAnnotations.getResolvedCoordinates.getGroupId == "com.android.support")
                            assert(supportAnnotations.getResolvedCoordinates.getPackaging == "jar")
                            assert(supportAnnotations.getResolvedCoordinates.getVersion == "24.0.0")
                        }
                    }
                    dependencies.getLibraries.asScala match {
                      case libraries =>
                        assert(libraries.size == 1)
                        val appcompatV7 = libraries.head
                        assert(appcompatV7.getAidlFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0/aidl"))
                        assert(appcompatV7.getAssetsFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0/assets"))
                        assert(appcompatV7.getBundle.getAbsolutePath.contains("sdk/extras/android/m2repository/com/android/support/appcompat-v7/24.0.0/appcompat-v7-24.0.0.aar"))
                        assert(appcompatV7.getExternalAnnotations.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0/annotations.zip"))
                        assert(appcompatV7.getFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0"))
                        assert(appcompatV7.getJarFile.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0/jars/classes.jar"))
                        assert(appcompatV7.getJniFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0/jni"))
                        appcompatV7.getLibraryDependencies.asScala match {
                          case libraryDependencies =>
                            assert(libraryDependencies.size == 3)
                            libraryDependencies.head match {
                              case supportV4 =>
                                assert(supportV4.getAidlFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/aidl"))
                                assert(supportV4.getAssetsFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/assets"))
                                assert(supportV4.getBundle.getAbsolutePath.contains("sdk/extras/android/m2repository/com/android/support/support-v4/24.0.0/support-v4-24.0.0.aar"))
                                assert(supportV4.getExternalAnnotations.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/annotations.zip"))
                                assert(supportV4.getFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0"))
                                assert(supportV4.getJarFile.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jars/classes.jar"))
                                assert(supportV4.getJniFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jni"))
                                assert(supportV4.getLibraryDependencies.isEmpty)
                                assert(supportV4.getLintJar.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jars/lint.jar"))

                                val localJars = supportV4.getLocalJars.asScala
                                assert(localJars.size == 1)
                                assert(localJars.head.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jars/libs/internal_impl-24.0.0.jar"))

                                assert(supportV4.getManifest.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/AndroidManifest.xml"))
                                assert(supportV4.getProguardRules.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/proguard.txt"))
                                assert(supportV4.getProject == null)
                                assert(supportV4.getProjectVariant == null)
                                assert(supportV4.getPublicResources.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/public.txt"))
                                assert(supportV4.getRenderscriptFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/rs"))
                                assert(supportV4.getResFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/res"))
                                assert(!supportV4.isOptional)
                                assert(supportV4.getRequestedCoordinates == null)
                                assert(supportV4.getResolvedCoordinates.getArtifactId == "support-v4")
                                assert(supportV4.getResolvedCoordinates.getClassifier == null)
                                assert(supportV4.getResolvedCoordinates.getGroupId == "com.android.support")
                                assert(supportV4.getResolvedCoordinates.getPackaging == "aar")
                                assert(supportV4.getResolvedCoordinates.getVersion == "24.0.0")
                            }
                            libraryDependencies(1) match {
                              case vectorDrawable =>
                                assert(vectorDrawable.getAidlFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/aidl"))
                                assert(vectorDrawable.getAssetsFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/assets"))
                                assert(vectorDrawable.getBundle.getAbsolutePath.contains("sdk/extras/android/m2repository/com/android/support/support-vector-drawable/24.0.0/support-vector-drawable-24.0.0.aar"))
                                assert(vectorDrawable.getExternalAnnotations.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/annotations.zip"))
                                assert(vectorDrawable.getFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0"))
                                assert(vectorDrawable.getJarFile.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/jars/classes.jar"))
                                assert(vectorDrawable.getJniFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/jni"))
                                vectorDrawable.getLibraryDependencies.asScala match {
                                  case supportV4libraryDependencies =>
                                    assert(supportV4libraryDependencies.size == 1)
                                    val supportV4 = supportV4libraryDependencies.head
                                    assert(supportV4.getAidlFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/aidl"))
                                    assert(supportV4.getAssetsFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/assets"))
                                    assert(supportV4.getBundle.getAbsolutePath.contains("sdk/extras/android/m2repository/com/android/support/support-v4/24.0.0/support-v4-24.0.0.aar"))
                                    assert(supportV4.getExternalAnnotations.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/annotations.zip"))
                                    assert(supportV4.getFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0"))
                                    assert(supportV4.getJarFile.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jars/classes.jar"))
                                    assert(supportV4.getJniFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jni"))
                                    assert(supportV4.getLibraryDependencies.isEmpty)
                                    assert(supportV4.getLintJar.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jars/lint.jar"))

                                    val localJars = supportV4.getLocalJars.asScala
                                    assert(localJars.size == 1)
                                    assert(localJars.head.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jars/libs/internal_impl-24.0.0.jar"))

                                    assert(supportV4.getManifest.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/AndroidManifest.xml"))
                                    assert(supportV4.getProguardRules.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/proguard.txt"))
                                    assert(supportV4.getProject == null)
                                    assert(supportV4.getProjectVariant == null)
                                    assert(supportV4.getPublicResources.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/public.txt"))
                                    assert(supportV4.getRenderscriptFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/rs"))
                                    assert(supportV4.getResFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/res"))
                                    assert(!supportV4.isOptional)
                                    assert(supportV4.getRequestedCoordinates == null)
                                    assert(supportV4.getResolvedCoordinates.getArtifactId == "support-v4")
                                    assert(supportV4.getResolvedCoordinates.getClassifier == null)
                                    assert(supportV4.getResolvedCoordinates.getGroupId == "com.android.support")
                                    assert(supportV4.getResolvedCoordinates.getPackaging == "aar")
                                }
                                assert(vectorDrawable.getLintJar.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/jars/lint.jar"))
                                assert(vectorDrawable.getLocalJars.isEmpty)
                                assert(vectorDrawable.getManifest.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/AndroidManifest.xml"))
                                assert(vectorDrawable.getProguardRules.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/proguard.txt"))
                                assert(vectorDrawable.getProject == null)
                                assert(vectorDrawable.getProjectVariant == null)
                                assert(vectorDrawable.getPublicResources.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/public.txt"))
                                assert(vectorDrawable.getRenderscriptFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/rs"))
                                assert(vectorDrawable.getResFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/res"))
                                assert(!vectorDrawable.isOptional)
                                assert(vectorDrawable.getRequestedCoordinates == null)
                                assert(vectorDrawable.getResolvedCoordinates.getArtifactId == "support-vector-drawable")
                                assert(vectorDrawable.getResolvedCoordinates.getClassifier == null)
                                assert(vectorDrawable.getResolvedCoordinates.getGroupId == "com.android.support")
                                assert(vectorDrawable.getResolvedCoordinates.getPackaging == "aar")
                                assert(vectorDrawable.getResolvedCoordinates.getVersion == "24.0.0")
                            }
                            libraryDependencies.last match {
                              case animatedVectorDrawable =>
                                assert(animatedVectorDrawable.getAidlFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0/aidl"))
                                assert(animatedVectorDrawable.getAssetsFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0/assets"))
                                assert(animatedVectorDrawable.getBundle.getAbsolutePath.contains("sdk/extras/android/m2repository/com/android/support/animated-vector-drawable/24.0.0/animated-vector-drawable-24.0.0.aar"))
                                assert(animatedVectorDrawable.getExternalAnnotations.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0/annotations.zip"))
                                assert(animatedVectorDrawable.getFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0"))
                                assert(animatedVectorDrawable.getJarFile.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0/jars/classes.jar"))
                                assert(animatedVectorDrawable.getJniFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0/jni"))
                                animatedVectorDrawable.getLibraryDependencies.asScala match {
                                  case animatedlibraryDependencies =>
                                    assert(animatedlibraryDependencies.size == 1)
                                    val supportVectorDrawable = animatedlibraryDependencies.head
                                    assert(supportVectorDrawable.getAidlFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/aidl"))
                                    assert(supportVectorDrawable.getAssetsFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/assets"))
                                    assert(supportVectorDrawable.getBundle.getAbsolutePath.contains("sdk/extras/android/m2repository/com/android/support/support-vector-drawable/24.0.0/support-vector-drawable-24.0.0.aar"))
                                    assert(supportVectorDrawable.getExternalAnnotations.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/annotations.zip"))
                                    assert(supportVectorDrawable.getFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0"))
                                    assert(supportVectorDrawable.getJarFile.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/jars/classes.jar"))
                                    assert(supportVectorDrawable.getJniFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/jni"))
                                    supportVectorDrawable.getLibraryDependencies.asScala match {
                                      case supportV4libraryDependencies =>
                                        assert(supportV4libraryDependencies.size == 1)
                                        val supportV4 = supportV4libraryDependencies.head
                                        assert(supportV4.getAidlFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/aidl"))
                                        assert(supportV4.getAssetsFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/assets"))
                                        assert(supportV4.getBundle.getAbsolutePath.contains("sdk/extras/android/m2repository/com/android/support/support-v4/24.0.0/support-v4-24.0.0.aar"))
                                        assert(supportV4.getExternalAnnotations.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/annotations.zip"))
                                        assert(supportV4.getFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0"))
                                        assert(supportV4.getJarFile.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jars/classes.jar"))
                                        assert(supportV4.getJniFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jni"))
                                        assert(supportV4.getLibraryDependencies.isEmpty)
                                        assert(supportV4.getLintJar.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jars/lint.jar"))

                                        val localJars = supportV4.getLocalJars.asScala
                                        assert(localJars.size == 1)
                                        assert(localJars.head.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jars/libs/internal_impl-24.0.0.jar"))

                                        assert(supportV4.getManifest.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/AndroidManifest.xml"))
                                        assert(supportV4.getProguardRules.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/proguard.txt"))
                                        assert(supportV4.getProject == null)
                                        assert(supportV4.getProjectVariant == null)
                                        assert(supportV4.getPublicResources.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/public.txt"))
                                        assert(supportV4.getRenderscriptFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/rs"))
                                        assert(supportV4.getResFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/res"))
                                        assert(!supportV4.isOptional)
                                        assert(supportV4.getRequestedCoordinates == null)
                                        assert(supportV4.getResolvedCoordinates.getArtifactId == "support-v4")
                                        assert(supportV4.getResolvedCoordinates.getClassifier == null)
                                        assert(supportV4.getResolvedCoordinates.getGroupId == "com.android.support")
                                        assert(supportV4.getResolvedCoordinates.getPackaging == "aar")
                                    }
                                    assert(supportVectorDrawable.getLintJar.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/jars/lint.jar"))
                                    assert(supportVectorDrawable.getLocalJars.isEmpty)
                                    assert(supportVectorDrawable.getManifest.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/AndroidManifest.xml"))
                                    assert(supportVectorDrawable.getProguardRules.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/proguard.txt"))
                                    assert(supportVectorDrawable.getProject == null)
                                    assert(supportVectorDrawable.getProjectVariant == null)
                                    assert(supportVectorDrawable.getPublicResources.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/public.txt"))
                                    assert(supportVectorDrawable.getRenderscriptFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/rs"))
                                    assert(supportVectorDrawable.getResFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/res"))
                                    assert(!supportVectorDrawable.isOptional)
                                    assert(supportVectorDrawable.getRequestedCoordinates == null)
                                    assert(supportVectorDrawable.getResolvedCoordinates.getArtifactId == "support-vector-drawable")
                                    assert(supportVectorDrawable.getResolvedCoordinates.getClassifier == null)
                                    assert(supportVectorDrawable.getResolvedCoordinates.getGroupId == "com.android.support")
                                    assert(supportVectorDrawable.getResolvedCoordinates.getPackaging == "aar")
                                }
                                assert(animatedVectorDrawable.getLintJar.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0/jars/lint.jar"))
                                assert(animatedVectorDrawable.getLocalJars.isEmpty)
                                assert(animatedVectorDrawable.getManifest.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0/AndroidManifest.xml"))
                                assert(animatedVectorDrawable.getProguardRules.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0/proguard.txt"))
                                assert(animatedVectorDrawable.getProject == null)
                                assert(animatedVectorDrawable.getProjectVariant == null)
                                assert(animatedVectorDrawable.getPublicResources.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0/public.txt"))
                                assert(animatedVectorDrawable.getRenderscriptFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0/rs"))
                                assert(animatedVectorDrawable.getResFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0/res"))
                                assert(!animatedVectorDrawable.isOptional)
                                assert(animatedVectorDrawable.getRequestedCoordinates == null)
                                assert(animatedVectorDrawable.getResolvedCoordinates.getArtifactId == "animated-vector-drawable")
                                assert(animatedVectorDrawable.getResolvedCoordinates.getClassifier == null)
                                assert(animatedVectorDrawable.getResolvedCoordinates.getGroupId == "com.android.support")
                                assert(animatedVectorDrawable.getResolvedCoordinates.getPackaging == "aar")
                                assert(animatedVectorDrawable.getResolvedCoordinates.getVersion == "24.0.0")
                            }
                        }
                        assert(appcompatV7.getLintJar.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0/jars/lint.jar"))
                        assert(appcompatV7.getLocalJars.isEmpty)
                        assert(appcompatV7.getManifest.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0/AndroidManifest.xml"))
                        assert(appcompatV7.getProguardRules.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0/proguard.txt"))
                        assert(appcompatV7.getProject == null)
                        assert(appcompatV7.getProjectVariant == null)
                        assert(appcompatV7.getPublicResources.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0/public.txt"))
                        assert(appcompatV7.getRenderscriptFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0/rs"))
                        assert(appcompatV7.getResFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0/res"))
                        assert(!appcompatV7.isOptional)
                        assert(appcompatV7.getRequestedCoordinates == null)
                        assert(appcompatV7.getResolvedCoordinates.getArtifactId == "appcompat-v7")
                        assert(appcompatV7.getResolvedCoordinates.getClassifier == null)
                        assert(appcompatV7.getResolvedCoordinates.getGroupId == "com.android.support")
                        assert(appcompatV7.getResolvedCoordinates.getPackaging == "aar")
                        assert(appcompatV7.getResolvedCoordinates.getVersion == "24.0.0")
                    }
                    assert(dependencies.getProjects.isEmpty)
                }
                mainArtifact.getDependencies.getJavaLibraries.asScala match {
                  case javaLibraries =>
                    assert(javaLibraries.size == 1)
                    val javaLibrary = javaLibraries.head
                    assert(javaLibrary.getDependencies.isEmpty)
                    javaLibrary match {
                      case supportAnnotations =>
                        assert(supportAnnotations.getJarFile.getAbsolutePath.contains("sdk/extras/android/m2repository/com/android/support/support-annotations/"))
                        assert(!supportAnnotations.isProvided)
                        assert(supportAnnotations.getRequestedCoordinates == null)
                        assert(supportAnnotations.getResolvedCoordinates.getArtifactId == "support-annotations")
                        assert(supportAnnotations.getResolvedCoordinates.getClassifier == null)
                        assert(supportAnnotations.getResolvedCoordinates.getGroupId == "com.android.support")
                        assert(supportAnnotations.getResolvedCoordinates.getPackaging == "jar")
                        assert(supportAnnotations.getResolvedCoordinates.getVersion == "24.0.0")
                    }
                }
                mainArtifact.getDependencies.getLibraries.asScala match {
                  case libraries =>
                    assert(libraries.size == 1)
                    val appcompatV7 = libraries.head
                    assert(appcompatV7.getAidlFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0/aidl"))
                    assert(appcompatV7.getAssetsFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0/assets"))
                    assert(appcompatV7.getBundle.getAbsolutePath.contains("sdk/extras/android/m2repository/com/android/support/appcompat-v7/24.0.0/appcompat-v7-24.0.0.aar"))
                    assert(appcompatV7.getExternalAnnotations.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0/annotations.zip"))
                    assert(appcompatV7.getFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0"))
                    assert(appcompatV7.getJarFile.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0/jars/classes.jar"))
                    assert(appcompatV7.getJniFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0/jni"))
                    appcompatV7.getLibraryDependencies.asScala match {
                      case libraryDependencies =>
                        assert(libraryDependencies.size == 3)
                        libraryDependencies.head match {
                          case supportV4 =>
                            assert(supportV4.getAidlFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/aidl"))
                            assert(supportV4.getAssetsFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/assets"))
                            assert(supportV4.getBundle.getAbsolutePath.contains("sdk/extras/android/m2repository/com/android/support/support-v4/24.0.0/support-v4-24.0.0.aar"))
                            assert(supportV4.getExternalAnnotations.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/annotations.zip"))
                            assert(supportV4.getFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0"))
                            assert(supportV4.getJarFile.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jars/classes.jar"))
                            assert(supportV4.getJniFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jni"))
                            assert(supportV4.getLibraryDependencies.isEmpty)
                            assert(supportV4.getLintJar.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jars/lint.jar"))

                            val localJars = supportV4.getLocalJars.asScala
                            assert(localJars.size == 1)
                            assert(localJars.head.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jars/libs/internal_impl-24.0.0.jar"))

                            assert(supportV4.getManifest.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/AndroidManifest.xml"))
                            assert(supportV4.getProguardRules.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/proguard.txt"))
                            assert(supportV4.getProject == null)
                            assert(supportV4.getProjectVariant == null)
                            assert(supportV4.getPublicResources.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/public.txt"))
                            assert(supportV4.getRenderscriptFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/rs"))
                            assert(supportV4.getResFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/res"))
                            assert(!supportV4.isOptional)
                            assert(supportV4.getRequestedCoordinates == null)
                            assert(supportV4.getResolvedCoordinates.getArtifactId == "support-v4")
                            assert(supportV4.getResolvedCoordinates.getClassifier == null)
                            assert(supportV4.getResolvedCoordinates.getGroupId == "com.android.support")
                            assert(supportV4.getResolvedCoordinates.getPackaging == "aar")
                            assert(supportV4.getResolvedCoordinates.getVersion == "24.0.0")
                        }
                        libraryDependencies(1) match {
                          case vectorDrawable =>
                            assert(vectorDrawable.getAidlFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/aidl"))
                            assert(vectorDrawable.getAssetsFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/assets"))
                            assert(vectorDrawable.getBundle.getAbsolutePath.contains("sdk/extras/android/m2repository/com/android/support/support-vector-drawable/24.0.0/support-vector-drawable-24.0.0.aar"))
                            assert(vectorDrawable.getExternalAnnotations.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/annotations.zip"))
                            assert(vectorDrawable.getFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0"))
                            assert(vectorDrawable.getJarFile.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/jars/classes.jar"))
                            assert(vectorDrawable.getJniFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/jni"))
                            vectorDrawable.getLibraryDependencies.asScala match {
                              case supportV4libraryDependencies =>
                                assert(supportV4libraryDependencies.size == 1)
                                val supportV4 = supportV4libraryDependencies.head
                                assert(supportV4.getAidlFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/aidl"))
                                assert(supportV4.getAssetsFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/assets"))
                                assert(supportV4.getBundle.getAbsolutePath.contains("sdk/extras/android/m2repository/com/android/support/support-v4/24.0.0/support-v4-24.0.0.aar"))
                                assert(supportV4.getExternalAnnotations.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/annotations.zip"))
                                assert(supportV4.getFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0"))
                                assert(supportV4.getJarFile.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jars/classes.jar"))
                                assert(supportV4.getJniFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jni"))
                                assert(supportV4.getLibraryDependencies.isEmpty)
                                assert(supportV4.getLintJar.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jars/lint.jar"))

                                val localJars = supportV4.getLocalJars.asScala
                                assert(localJars.size == 1)
                                assert(localJars.head.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jars/libs/internal_impl-24.0.0.jar"))

                                assert(supportV4.getManifest.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/AndroidManifest.xml"))
                                assert(supportV4.getProguardRules.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/proguard.txt"))
                                assert(supportV4.getProject == null)
                                assert(supportV4.getProjectVariant == null)
                                assert(supportV4.getPublicResources.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/public.txt"))
                                assert(supportV4.getRenderscriptFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/rs"))
                                assert(supportV4.getResFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/res"))
                                assert(!supportV4.isOptional)
                                assert(supportV4.getRequestedCoordinates == null)
                                assert(supportV4.getResolvedCoordinates.getArtifactId == "support-v4")
                                assert(supportV4.getResolvedCoordinates.getClassifier == null)
                                assert(supportV4.getResolvedCoordinates.getGroupId == "com.android.support")
                                assert(supportV4.getResolvedCoordinates.getPackaging == "aar")
                            }
                            assert(vectorDrawable.getLintJar.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/jars/lint.jar"))
                            assert(vectorDrawable.getLocalJars.isEmpty)
                            assert(vectorDrawable.getManifest.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/AndroidManifest.xml"))
                            assert(vectorDrawable.getProguardRules.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/proguard.txt"))
                            assert(vectorDrawable.getProject == null)
                            assert(vectorDrawable.getProjectVariant == null)
                            assert(vectorDrawable.getPublicResources.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/public.txt"))
                            assert(vectorDrawable.getRenderscriptFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/rs"))
                            assert(vectorDrawable.getResFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/res"))
                            assert(!vectorDrawable.isOptional)
                            assert(vectorDrawable.getRequestedCoordinates == null)
                            assert(vectorDrawable.getResolvedCoordinates.getArtifactId == "support-vector-drawable")
                            assert(vectorDrawable.getResolvedCoordinates.getClassifier == null)
                            assert(vectorDrawable.getResolvedCoordinates.getGroupId == "com.android.support")
                            assert(vectorDrawable.getResolvedCoordinates.getPackaging == "aar")
                            assert(vectorDrawable.getResolvedCoordinates.getVersion == "24.0.0")
                        }
                        libraryDependencies.last match {
                          case animatedVectorDrawable =>
                            assert(animatedVectorDrawable.getAidlFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0/aidl"))
                            assert(animatedVectorDrawable.getAssetsFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0/assets"))
                            assert(animatedVectorDrawable.getBundle.getAbsolutePath.contains("sdk/extras/android/m2repository/com/android/support/animated-vector-drawable/24.0.0/animated-vector-drawable-24.0.0.aar"))
                            assert(animatedVectorDrawable.getExternalAnnotations.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0/annotations.zip"))
                            assert(animatedVectorDrawable.getFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0"))
                            assert(animatedVectorDrawable.getJarFile.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0/jars/classes.jar"))
                            assert(animatedVectorDrawable.getJniFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0/jni"))
                            animatedVectorDrawable.getLibraryDependencies.asScala match {
                              case animatedlibraryDependencies =>
                                assert(animatedlibraryDependencies.size == 1)
                                val supportVectorDrawable = animatedlibraryDependencies.head
                                assert(supportVectorDrawable.getAidlFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/aidl"))
                                assert(supportVectorDrawable.getAssetsFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/assets"))
                                assert(supportVectorDrawable.getBundle.getAbsolutePath.contains("sdk/extras/android/m2repository/com/android/support/support-vector-drawable/24.0.0/support-vector-drawable-24.0.0.aar"))
                                assert(supportVectorDrawable.getExternalAnnotations.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/annotations.zip"))
                                assert(supportVectorDrawable.getFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0"))
                                assert(supportVectorDrawable.getJarFile.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/jars/classes.jar"))
                                assert(supportVectorDrawable.getJniFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/jni"))
                                supportVectorDrawable.getLibraryDependencies.asScala match {
                                  case supportV4libraryDependencies =>
                                    assert(supportV4libraryDependencies.size == 1)
                                    val supportV4 = supportV4libraryDependencies.head
                                    assert(supportV4.getAidlFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/aidl"))
                                    assert(supportV4.getAssetsFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/assets"))
                                    assert(supportV4.getBundle.getAbsolutePath.contains("sdk/extras/android/m2repository/com/android/support/support-v4/24.0.0/support-v4-24.0.0.aar"))
                                    assert(supportV4.getExternalAnnotations.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/annotations.zip"))
                                    assert(supportV4.getFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0"))
                                    assert(supportV4.getJarFile.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jars/classes.jar"))
                                    assert(supportV4.getJniFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jni"))
                                    assert(supportV4.getLibraryDependencies.isEmpty)
                                    assert(supportV4.getLintJar.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jars/lint.jar"))

                                    val localJars = supportV4.getLocalJars.asScala
                                    assert(localJars.size == 1)
                                    assert(localJars.head.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jars/libs/internal_impl-24.0.0.jar"))

                                    assert(supportV4.getManifest.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/AndroidManifest.xml"))
                                    assert(supportV4.getProguardRules.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/proguard.txt"))
                                    assert(supportV4.getProject == null)
                                    assert(supportV4.getProjectVariant == null)
                                    assert(supportV4.getPublicResources.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/public.txt"))
                                    assert(supportV4.getRenderscriptFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/rs"))
                                    assert(supportV4.getResFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/res"))
                                    assert(!supportV4.isOptional)
                                    assert(supportV4.getRequestedCoordinates == null)
                                    assert(supportV4.getResolvedCoordinates.getArtifactId == "support-v4")
                                    assert(supportV4.getResolvedCoordinates.getClassifier == null)
                                    assert(supportV4.getResolvedCoordinates.getGroupId == "com.android.support")
                                    assert(supportV4.getResolvedCoordinates.getPackaging == "aar")
                                }
                                assert(supportVectorDrawable.getLintJar.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/jars/lint.jar"))
                                assert(supportVectorDrawable.getLocalJars.isEmpty)
                                assert(supportVectorDrawable.getManifest.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/AndroidManifest.xml"))
                                assert(supportVectorDrawable.getProguardRules.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/proguard.txt"))
                                assert(supportVectorDrawable.getProject == null)
                                assert(supportVectorDrawable.getProjectVariant == null)
                                assert(supportVectorDrawable.getPublicResources.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/public.txt"))
                                assert(supportVectorDrawable.getRenderscriptFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/rs"))
                                assert(supportVectorDrawable.getResFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/res"))
                                assert(!supportVectorDrawable.isOptional)
                                assert(supportVectorDrawable.getRequestedCoordinates == null)
                                assert(supportVectorDrawable.getResolvedCoordinates.getArtifactId == "support-vector-drawable")
                                assert(supportVectorDrawable.getResolvedCoordinates.getClassifier == null)
                                assert(supportVectorDrawable.getResolvedCoordinates.getGroupId == "com.android.support")
                                assert(supportVectorDrawable.getResolvedCoordinates.getPackaging == "aar")
                            }
                            assert(animatedVectorDrawable.getLintJar.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0/jars/lint.jar"))
                            assert(animatedVectorDrawable.getLocalJars.isEmpty)
                            assert(animatedVectorDrawable.getManifest.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0/AndroidManifest.xml"))
                            assert(animatedVectorDrawable.getProguardRules.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0/proguard.txt"))
                            assert(animatedVectorDrawable.getProject == null)
                            assert(animatedVectorDrawable.getProjectVariant == null)
                            assert(animatedVectorDrawable.getPublicResources.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0/public.txt"))
                            assert(animatedVectorDrawable.getRenderscriptFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0/rs"))
                            assert(animatedVectorDrawable.getResFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0/res"))
                            assert(!animatedVectorDrawable.isOptional)
                            assert(animatedVectorDrawable.getRequestedCoordinates == null)
                            assert(animatedVectorDrawable.getResolvedCoordinates.getArtifactId == "animated-vector-drawable")
                            assert(animatedVectorDrawable.getResolvedCoordinates.getClassifier == null)
                            assert(animatedVectorDrawable.getResolvedCoordinates.getGroupId == "com.android.support")
                            assert(animatedVectorDrawable.getResolvedCoordinates.getPackaging == "aar")
                            assert(animatedVectorDrawable.getResolvedCoordinates.getVersion == "24.0.0")
                        }
                    }
                    assert(appcompatV7.getLintJar.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0/jars/lint.jar"))
                    assert(appcompatV7.getLocalJars.isEmpty)
                    assert(appcompatV7.getManifest.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0/AndroidManifest.xml"))
                    assert(appcompatV7.getProguardRules.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0/proguard.txt"))
                    assert(appcompatV7.getProject == null)
                    assert(appcompatV7.getProjectVariant == null)
                    assert(appcompatV7.getPublicResources.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0/public.txt"))
                    assert(appcompatV7.getRenderscriptFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0/rs"))
                    assert(appcompatV7.getResFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0/res"))
                    assert(!appcompatV7.isOptional)
                    assert(appcompatV7.getRequestedCoordinates == null)
                    assert(appcompatV7.getResolvedCoordinates.getArtifactId == "appcompat-v7")
                    assert(appcompatV7.getResolvedCoordinates.getClassifier == null)
                    assert(appcompatV7.getResolvedCoordinates.getGroupId == "com.android.support")
                    assert(appcompatV7.getResolvedCoordinates.getPackaging == "aar")
                    assert(appcompatV7.getResolvedCoordinates.getVersion == "24.0.0")
                }
                assert(mainArtifact.getDependencies.getProjects.isEmpty)
                for (taskName <- mainArtifact.getIdeSetupTaskNames.asScala) {
                  assert(taskName == "generateDevelopDebugSources")
                }
                assert(mainArtifact.getJavaResourcesFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/sourceFolderJavaResources/develop/debug"))
                assert(mainArtifact.getMultiFlavorSourceProvider == null)
                assert(mainArtifact.getName == "_main_")
                mainArtifact.getVariantSourceProvider match {
                  case variantSourceProvider =>
                    assert(variantSourceProvider.getAidlDirectories.size() == 1)
                    assert(variantSourceProvider.getAidlDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/developDebug/aidl"))
                    assert(variantSourceProvider.getAssetsDirectories.size() == 1)
                    assert(variantSourceProvider.getAssetsDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/developDebug/assets"))
                    assert(variantSourceProvider.getCDirectories.size() == 1)
                    assert(variantSourceProvider.getCDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/developDebug/jni"))
                    assert(variantSourceProvider.getCppDirectories.size() == 1)
                    assert(variantSourceProvider.getCppDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/developDebug/jni"))
                    assert(variantSourceProvider.getJavaDirectories.size() == 1)
                    assert(variantSourceProvider.getJavaDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/developDebug/java"))
                    assert(variantSourceProvider.getJniLibsDirectories.size() == 1)
                    assert(variantSourceProvider.getJniLibsDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/developDebug/jniLibs"))
                    assert(variantSourceProvider.getManifestFile.getAbsolutePath.contains(s"$APP_SRC_DIR/developDebug/AndroidManifest.xml"))
                    assert(variantSourceProvider.getName == "developDebug")
                    assert(variantSourceProvider.getRenderscriptDirectories.size() == 1)
                    assert(variantSourceProvider.getRenderscriptDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/developDebug/rs"))
                    assert(variantSourceProvider.getResDirectories.size() == 1)
                    assert(variantSourceProvider.getResDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/developDebug/res"))
                    assert(variantSourceProvider.getResourcesDirectories.size() == 1)
                    assert(variantSourceProvider.getResourcesDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/developDebug/resources"))
                    assert(variantSourceProvider.getShadersDirectories.size() == 1)
                    assert(variantSourceProvider.getShadersDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/developDebug/shaders"))
                }
            }
            developDebug.getMergedFlavor match {
              case mergedFlavor =>
                assert(mergedFlavor.getApplicationId == "com.epy0n0ff.example")
                assert(mergedFlavor.getMaxSdkVersion == null)
                mergedFlavor.getMinSdkVersion match {
                  case minSdkVersion =>
                    assert(minSdkVersion.getApiLevel == 14)
                    assert(minSdkVersion.getApiString == "14")
                    assert(minSdkVersion.getCodename == null)
                }
                assert(mergedFlavor.getName == "")
                assert(mergedFlavor.getRenderscriptNdkModeEnabled == null)
                assert(mergedFlavor.getRenderscriptSupportModeEnabled == null)
                assert(mergedFlavor.getRenderscriptTargetApi == null)
                assert(mergedFlavor.getResourceConfigurations.isEmpty)
                assert(mergedFlavor.getSigningConfig == null)
                mergedFlavor.getTargetSdkVersion match {
                  case targetSdkVersion =>
                    assert(mergedFlavor.getTargetSdkVersion.getApiLevel == 24)
                    assert(mergedFlavor.getTargetSdkVersion.getApiString == "24")
                    assert(mergedFlavor.getTargetSdkVersion.getCodename == null)
                }
                assert(mergedFlavor.getTestApplicationId == null)
                assert(mergedFlavor.getTestFunctionalTest == null)
                assert(mergedFlavor.getTestHandleProfiling == null)
                assert(mergedFlavor.getTestInstrumentationRunner == null)
                assert(mergedFlavor.getTestInstrumentationRunnerArguments.isEmpty)
                mergedFlavor.getVectorDrawables match {
                  case vectorDrawables =>
                    val generateDensities = vectorDrawables.getGeneratedDensities.asScala
                    assert(generateDensities.size == 6)
                    assert(generateDensities.contains("ldpi"))
                    assert(generateDensities.contains("mdpi"))
                    assert(generateDensities.contains("hdpi"))
                    assert(generateDensities.contains("xhdpi"))
                    assert(generateDensities.contains("xxhdpi"))
                    assert(generateDensities.contains("xxxhdpi"))
                    assert(!vectorDrawables.getUseSupportLibrary)
                }
                assert(mergedFlavor.getVersionCode == 1)
                assert(mergedFlavor.getVersionName == "1.0")
                assert(mergedFlavor.getApplicationIdSuffix == null)
                assert(mergedFlavor.getBuildConfigFields.isEmpty)
                assert(mergedFlavor.getConsumerProguardFiles.isEmpty)
                assert(mergedFlavor.getDimension == null)
                assert(mergedFlavor.getJarJarRuleFiles.isEmpty)
                assert(mergedFlavor.getManifestPlaceholders.isEmpty)
                assert(mergedFlavor.getMultiDexEnabled == null)
                assert(mergedFlavor.getMultiDexKeepFile == null)
                assert(mergedFlavor.getMultiDexKeepProguard == null)
                assert(mergedFlavor.getProguardFiles.isEmpty)
                assert(mergedFlavor.getResValues.isEmpty)
                assert(mergedFlavor.getTestProguardFiles.isEmpty)
            }
            assert(developDebug.getName == "developDebug")
            developDebug.getProductFlavors.asScala match {
              case productFlavors =>
                assert(productFlavors.size == 1)
                assert(productFlavors.head == "develop")
            }
        }
        variants.last match {
          case developRelease =>
            assert(developRelease.getBuildType == "release")
            assert(developRelease.getDisplayName == "develop-release")
            assert(developRelease.getExtraAndroidArtifacts.isEmpty)
            developRelease.getExtraJavaArtifacts.asScala match {
              case extraJavaArtifacts =>
                assert(extraJavaArtifacts.size == 1)
                val artifact = extraJavaArtifacts.head
                assert(artifact.getMockablePlatformJar.getAbsolutePath.contains(s"$BUILD_GENERATED_DIR/mockable-android-24.jar"))
                assert(artifact.getAssembleTaskName == "assembleDevelopReleaseUnitTest")
                assert(artifact.getClassesFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/classes/test/develop/release"))
                val dependency = artifact.getDependencies
                val javaLibraries = dependency.getJavaLibraries.asScala
                assert(javaLibraries.size == 2)
                for (lib <- javaLibraries) {
                  val jar = lib.getJarFile
                  assert(lib.getDependencies.isEmpty)
                  assert(jar.getAbsolutePath.contains(".gradle/caches/"))
                  jar.getName match {
                    case "hamcrest-core-1.3.jar" =>
                    case "junit-4.12.jar" =>
                  }
                  assert(!lib.isProvided)
                  assert(lib.getRequestedCoordinates == null)
                  lib.getResolvedCoordinates.getArtifactId match {
                    case "junit" =>
                    case "hamcrest-core" =>
                  }
                  assert(lib.getResolvedCoordinates.getClassifier == null)
                  lib.getResolvedCoordinates.getGroupId match {
                    case "junit" =>
                    case "org.hamcrest" =>
                  }
                  lib.getResolvedCoordinates.getPackaging match {
                    case "jar" =>
                  }
                  lib.getResolvedCoordinates.getVersion match {
                    case "4.12" =>
                    case "1.3" =>
                  }
                }
                assert(extraJavaArtifacts.head.getDependencies.getLibraries.isEmpty)
                assert(extraJavaArtifacts.head.getDependencies.getProjects.isEmpty)
                assert(extraJavaArtifacts.head.getGeneratedSourceFolders.isEmpty)
                for (taskName <- extraJavaArtifacts.head.getIdeSetupTaskNames.asScala) taskName match {
                  case "prepareDevelopReleaseUnitTestDependencies" =>
                  case "mockableAndroidJar" =>
                }
                assert(extraJavaArtifacts.head.getJavaResourcesFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/sourceFolderJavaResources/test/develop/release"))
                assert(extraJavaArtifacts.head.getMultiFlavorSourceProvider == null)
                assert(extraJavaArtifacts.head.getName == "_unit_test_")
                extraJavaArtifacts.head.getVariantSourceProvider match {
                  case variantSourceProvider =>
                    assert(variantSourceProvider.getAidlDirectories.size() == 1)
                    assert(variantSourceProvider.getAidlDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testDevelopRelease/aidl"))
                    assert(variantSourceProvider.getAssetsDirectories.size() == 1)
                    assert(variantSourceProvider.getAssetsDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testDevelopRelease/assets"))
                    assert(variantSourceProvider.getCDirectories.size() == 1)
                    assert(variantSourceProvider.getCDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testDevelopRelease/jni"))
                    assert(variantSourceProvider.getCppDirectories.size() == 1)
                    assert(variantSourceProvider.getCppDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testDevelopRelease/jni"))
                    assert(variantSourceProvider.getJavaDirectories.size() == 1)
                    assert(variantSourceProvider.getJavaDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testDevelopRelease/java"))
                    assert(variantSourceProvider.getJniLibsDirectories.size() == 1)
                    assert(variantSourceProvider.getJniLibsDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testDevelopRelease/jniLibs"))
                    assert(variantSourceProvider.getManifestFile.getAbsolutePath.contains(s"$APP_SRC_DIR/testDevelopRelease/AndroidManifest.xml"))
                    assert(variantSourceProvider.getName == "testDevelopRelease")
                    assert(variantSourceProvider.getRenderscriptDirectories.size() == 1)
                    assert(variantSourceProvider.getRenderscriptDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testDevelopRelease/rs"))
                    assert(variantSourceProvider.getResDirectories.size() == 1)
                    assert(variantSourceProvider.getResDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testDevelopRelease/res"))
                    assert(variantSourceProvider.getResourcesDirectories.size() == 1)
                    assert(variantSourceProvider.getResourcesDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testDevelopRelease/resources"))
                    assert(variantSourceProvider.getShadersDirectories.size() == 1)
                    assert(variantSourceProvider.getShadersDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/testDevelopRelease/shaders"))
                }
            }
            developRelease.getMainArtifact match {
              case mainArtifact =>
                assert(mainArtifact.getAbiFilters == null)
                assert(mainArtifact.getApplicationId == "com.epy0n0ff.example")
                assert(mainArtifact.getBuildConfigFields.isEmpty)
                mainArtifact.getGeneratedResourceFolders.asScala match {
                  case generatedResourceFolders =>
                    assert(generatedResourceFolders.size == 2)
                    assert(generatedResourceFolders.head.getAbsolutePath.contains(s"$APP_BUILD_GENERATED_DIR/res/rs/develop/release"))
                    assert(generatedResourceFolders.last.getAbsolutePath.contains(s"$APP_BUILD_GENERATED_DIR/res/resValues/develop/release"))
                }
                val folders = mainArtifact.getGeneratedSourceFolders.asScala
                assert(folders.size == 4)
                val r = s".*$APP_BUILD_GENERATED_DIR/source/r/develop/release$$".r
                val aidl = s".*$APP_BUILD_GENERATED_DIR/source/aidl/develop/release$$".r
                val buildConfig = s".*$APP_BUILD_GENERATED_DIR/source/buildConfig/develop/release$$".r
                val rs = s".*$APP_BUILD_GENERATED_DIR/source/rs/develop/release$$".r
                for (genSource <- folders) genSource.getAbsolutePath match {
                  case r(_*) =>
                  case aidl(_*) =>
                  case buildConfig(_*) =>
                  case rs(_*) =>
                }
                mainArtifact.getInstantRun match {
                  case instantRun =>
                    assert(instantRun.getIncrementalAssembleTaskName == "incrementalDevelopReleaseSupportDex")
                    assert(instantRun.getInfoFile.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/restart-dex/develop/release/build-info.xml"))
                    assert(!instantRun.isSupportedByArtifact)
                }

                assert(mainArtifact.getNativeLibraries.isEmpty)

                mainArtifact.getOutputs.asScala match {
                  case outputs =>
                    assert(outputs.size == 1)
                    outputs.head match {
                      case androidArtifactOutput =>
                        assert(androidArtifactOutput.getAssembleTaskName == "assembledevelopRelease") // BUG?
                        assert(androidArtifactOutput.getGeneratedManifest.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/manifests/full/develop/release/AndroidManifest.xml"))
                    }
                }

                assert(mainArtifact.getResValues.isEmpty)
                assert(mainArtifact.getSigningConfigName == null)
                assert(mainArtifact.getSourceGenTaskName == "generateDevelopReleaseSources")
                assert(!mainArtifact.isSigned)
                assert(mainArtifact.getAssembleTaskName == "assembleDevelopRelease")
                assert(mainArtifact.getClassesFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/classes/develop/release"))
                assert(mainArtifact.getCompileTaskName == "compileDevelopReleaseSources")
                mainArtifact.getDependencies match {
                  case dependencies =>
                    dependencies.getJavaLibraries.asScala match {
                      case javaLibraries =>
                        assert(javaLibraries.size == 1)
                        val javaLibrary = javaLibraries.head
                        assert(javaLibrary.getDependencies.isEmpty)
                        javaLibrary match {
                          case supportAnnotations =>
                            assert(supportAnnotations.getJarFile.getAbsolutePath.contains("sdk/extras/android/m2repository/com/android/support/support-annotations/"))
                            assert(!supportAnnotations.isProvided)
                            assert(supportAnnotations.getRequestedCoordinates == null)
                            assert(supportAnnotations.getResolvedCoordinates.getArtifactId == "support-annotations")
                            assert(supportAnnotations.getResolvedCoordinates.getClassifier == null)
                            assert(supportAnnotations.getResolvedCoordinates.getGroupId == "com.android.support")
                            assert(supportAnnotations.getResolvedCoordinates.getPackaging == "jar")
                            assert(supportAnnotations.getResolvedCoordinates.getVersion == "24.0.0")
                        }
                    }
                    dependencies.getLibraries.asScala match {
                      case libraries =>
                        assert(libraries.size == 1)
                        val appcompatV7 = libraries.head
                        assert(appcompatV7.getAidlFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0/aidl"))
                        assert(appcompatV7.getAssetsFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0/assets"))
                        assert(appcompatV7.getBundle.getAbsolutePath.contains("sdk/extras/android/m2repository/com/android/support/appcompat-v7/24.0.0/appcompat-v7-24.0.0.aar"))
                        assert(appcompatV7.getExternalAnnotations.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0/annotations.zip"))
                        assert(appcompatV7.getFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0"))
                        assert(appcompatV7.getJarFile.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0/jars/classes.jar"))
                        assert(appcompatV7.getJniFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0/jni"))
                        appcompatV7.getLibraryDependencies.asScala.toList match {
                          case libraryDependencies =>
                            assert(libraryDependencies.size == 3)
                            libraryDependencies.head match {
                              case supportV4 =>
                                assert(supportV4.getAidlFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/aidl"))
                                assert(supportV4.getAssetsFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/assets"))
                                assert(supportV4.getBundle.getAbsolutePath.contains("sdk/extras/android/m2repository/com/android/support/support-v4/24.0.0/support-v4-24.0.0.aar"))
                                assert(supportV4.getExternalAnnotations.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/annotations.zip"))
                                assert(supportV4.getFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0"))
                                assert(supportV4.getJarFile.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jars/classes.jar"))
                                assert(supportV4.getJniFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jni"))
                                assert(supportV4.getLibraryDependencies.isEmpty)
                                assert(supportV4.getLintJar.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jars/lint.jar"))

                                val localJars = supportV4.getLocalJars.asScala
                                assert(localJars.size == 1)
                                assert(localJars.head.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jars/libs/internal_impl-24.0.0.jar"))

                                assert(supportV4.getManifest.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/AndroidManifest.xml"))
                                assert(supportV4.getProguardRules.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/proguard.txt"))
                                assert(supportV4.getProject == null)
                                assert(supportV4.getProjectVariant == null)
                                assert(supportV4.getPublicResources.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/public.txt"))
                                assert(supportV4.getRenderscriptFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/rs"))
                                assert(supportV4.getResFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/res"))
                                assert(!supportV4.isOptional)
                                assert(supportV4.getRequestedCoordinates == null)
                                assert(supportV4.getResolvedCoordinates.getArtifactId == "support-v4")
                                assert(supportV4.getResolvedCoordinates.getClassifier == null)
                                assert(supportV4.getResolvedCoordinates.getGroupId == "com.android.support")
                                assert(supportV4.getResolvedCoordinates.getPackaging == "aar")
                                assert(supportV4.getResolvedCoordinates.getVersion == "24.0.0")
                            }
                            libraryDependencies(1) match {
                              case vectorDrawable =>
                                assert(vectorDrawable.getAidlFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/aidl"))
                                assert(vectorDrawable.getAssetsFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/assets"))
                                assert(vectorDrawable.getBundle.getAbsolutePath.contains("sdk/extras/android/m2repository/com/android/support/support-vector-drawable/24.0.0/support-vector-drawable-24.0.0.aar"))
                                assert(vectorDrawable.getExternalAnnotations.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/annotations.zip"))
                                assert(vectorDrawable.getFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0"))
                                assert(vectorDrawable.getJarFile.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/jars/classes.jar"))
                                assert(vectorDrawable.getJniFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/jni"))
                                vectorDrawable.getLibraryDependencies.asScala match {
                                  case supportV4libraryDependencies =>
                                    assert(supportV4libraryDependencies.size == 1)
                                    val supportV4 = supportV4libraryDependencies.head
                                    assert(supportV4.getAidlFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/aidl"))
                                    assert(supportV4.getAssetsFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/assets"))
                                    assert(supportV4.getBundle.getAbsolutePath.contains("sdk/extras/android/m2repository/com/android/support/support-v4/24.0.0/support-v4-24.0.0.aar"))
                                    assert(supportV4.getExternalAnnotations.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/annotations.zip"))
                                    assert(supportV4.getFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0"))
                                    assert(supportV4.getJarFile.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jars/classes.jar"))
                                    assert(supportV4.getJniFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jni"))
                                    assert(supportV4.getLibraryDependencies.isEmpty)
                                    assert(supportV4.getLintJar.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jars/lint.jar"))

                                    val localJars = supportV4.getLocalJars.asScala
                                    assert(localJars.size == 1)
                                    assert(localJars.head.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jars/libs/internal_impl-24.0.0.jar"))

                                    assert(supportV4.getManifest.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/AndroidManifest.xml"))
                                    assert(supportV4.getProguardRules.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/proguard.txt"))
                                    assert(supportV4.getProject == null)
                                    assert(supportV4.getProjectVariant == null)
                                    assert(supportV4.getPublicResources.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/public.txt"))
                                    assert(supportV4.getRenderscriptFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/rs"))
                                    assert(supportV4.getResFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/res"))
                                    assert(!supportV4.isOptional)
                                    assert(supportV4.getRequestedCoordinates == null)
                                    assert(supportV4.getResolvedCoordinates.getArtifactId == "support-v4")
                                    assert(supportV4.getResolvedCoordinates.getClassifier == null)
                                    assert(supportV4.getResolvedCoordinates.getGroupId == "com.android.support")
                                    assert(supportV4.getResolvedCoordinates.getPackaging == "aar")
                                }
                                assert(vectorDrawable.getLintJar.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/jars/lint.jar"))
                                assert(vectorDrawable.getLocalJars.isEmpty)
                                assert(vectorDrawable.getManifest.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/AndroidManifest.xml"))
                                assert(vectorDrawable.getProguardRules.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/proguard.txt"))
                                assert(vectorDrawable.getProject == null)
                                assert(vectorDrawable.getProjectVariant == null)
                                assert(vectorDrawable.getPublicResources.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/public.txt"))
                                assert(vectorDrawable.getRenderscriptFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/rs"))
                                assert(vectorDrawable.getResFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/res"))
                                assert(!vectorDrawable.isOptional)
                                assert(vectorDrawable.getRequestedCoordinates == null)
                                assert(vectorDrawable.getResolvedCoordinates.getArtifactId == "support-vector-drawable")
                                assert(vectorDrawable.getResolvedCoordinates.getClassifier == null)
                                assert(vectorDrawable.getResolvedCoordinates.getGroupId == "com.android.support")
                                assert(vectorDrawable.getResolvedCoordinates.getPackaging == "aar")
                                assert(vectorDrawable.getResolvedCoordinates.getVersion == "24.0.0")
                            }
                            libraryDependencies.last match {
                              case animatedVectorDrawable =>
                                assert(animatedVectorDrawable.getAidlFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0/aidl"))
                                assert(animatedVectorDrawable.getAssetsFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0/assets"))
                                assert(animatedVectorDrawable.getBundle.getAbsolutePath.contains("sdk/extras/android/m2repository/com/android/support/animated-vector-drawable/24.0.0/animated-vector-drawable-24.0.0.aar"))
                                assert(animatedVectorDrawable.getExternalAnnotations.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0/annotations.zip"))
                                assert(animatedVectorDrawable.getFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0"))
                                assert(animatedVectorDrawable.getJarFile.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0/jars/classes.jar"))
                                assert(animatedVectorDrawable.getJniFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0/jni"))
                                animatedVectorDrawable.getLibraryDependencies.asScala match {
                                  case animatedlibraryDependencies =>
                                    assert(animatedlibraryDependencies.size == 1)
                                    val supportVectorDrawable = animatedlibraryDependencies.head
                                    assert(supportVectorDrawable.getAidlFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/aidl"))
                                    assert(supportVectorDrawable.getAssetsFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/assets"))
                                    assert(supportVectorDrawable.getBundle.getAbsolutePath.contains("sdk/extras/android/m2repository/com/android/support/support-vector-drawable/24.0.0/support-vector-drawable-24.0.0.aar"))
                                    assert(supportVectorDrawable.getExternalAnnotations.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/annotations.zip"))
                                    assert(supportVectorDrawable.getFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0"))
                                    assert(supportVectorDrawable.getJarFile.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/jars/classes.jar"))
                                    assert(supportVectorDrawable.getJniFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/jni"))
                                    supportVectorDrawable.getLibraryDependencies.asScala match {
                                      case supportV4libraryDependencies =>
                                        assert(supportV4libraryDependencies.size == 1)
                                        val supportV4 = supportV4libraryDependencies.head
                                        assert(supportV4.getAidlFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/aidl"))
                                        assert(supportV4.getAssetsFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/assets"))
                                        assert(supportV4.getBundle.getAbsolutePath.contains("sdk/extras/android/m2repository/com/android/support/support-v4/24.0.0/support-v4-24.0.0.aar"))
                                        assert(supportV4.getExternalAnnotations.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/annotations.zip"))
                                        assert(supportV4.getFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0"))
                                        assert(supportV4.getJarFile.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jars/classes.jar"))
                                        assert(supportV4.getJniFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jni"))
                                        assert(supportV4.getLibraryDependencies.isEmpty)
                                        assert(supportV4.getLintJar.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jars/lint.jar"))

                                        val localJars = supportV4.getLocalJars.asScala
                                        assert(localJars.size == 1)
                                        assert(localJars.head.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jars/libs/internal_impl-24.0.0.jar"))

                                        assert(supportV4.getManifest.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/AndroidManifest.xml"))
                                        assert(supportV4.getProguardRules.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/proguard.txt"))
                                        assert(supportV4.getProject == null)
                                        assert(supportV4.getProjectVariant == null)
                                        assert(supportV4.getPublicResources.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/public.txt"))
                                        assert(supportV4.getRenderscriptFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/rs"))
                                        assert(supportV4.getResFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/res"))
                                        assert(!supportV4.isOptional)
                                        assert(supportV4.getRequestedCoordinates == null)
                                        assert(supportV4.getResolvedCoordinates.getArtifactId == "support-v4")
                                        assert(supportV4.getResolvedCoordinates.getClassifier == null)
                                        assert(supportV4.getResolvedCoordinates.getGroupId == "com.android.support")
                                        assert(supportV4.getResolvedCoordinates.getPackaging == "aar")
                                    }
                                    assert(supportVectorDrawable.getLintJar.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/jars/lint.jar"))
                                    assert(supportVectorDrawable.getLocalJars.isEmpty)
                                    assert(supportVectorDrawable.getManifest.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/AndroidManifest.xml"))
                                    assert(supportVectorDrawable.getProguardRules.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/proguard.txt"))
                                    assert(supportVectorDrawable.getProject == null)
                                    assert(supportVectorDrawable.getProjectVariant == null)
                                    assert(supportVectorDrawable.getPublicResources.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/public.txt"))
                                    assert(supportVectorDrawable.getRenderscriptFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/rs"))
                                    assert(supportVectorDrawable.getResFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/res"))
                                    assert(!supportVectorDrawable.isOptional)
                                    assert(supportVectorDrawable.getRequestedCoordinates == null)
                                    assert(supportVectorDrawable.getResolvedCoordinates.getArtifactId == "support-vector-drawable")
                                    assert(supportVectorDrawable.getResolvedCoordinates.getClassifier == null)
                                    assert(supportVectorDrawable.getResolvedCoordinates.getGroupId == "com.android.support")
                                    assert(supportVectorDrawable.getResolvedCoordinates.getPackaging == "aar")
                                }
                                assert(animatedVectorDrawable.getLintJar.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0/jars/lint.jar"))
                                assert(animatedVectorDrawable.getLocalJars.isEmpty)
                                assert(animatedVectorDrawable.getManifest.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0/AndroidManifest.xml"))
                                assert(animatedVectorDrawable.getProguardRules.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0/proguard.txt"))
                                assert(animatedVectorDrawable.getProject == null)
                                assert(animatedVectorDrawable.getProjectVariant == null)
                                assert(animatedVectorDrawable.getPublicResources.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0/public.txt"))
                                assert(animatedVectorDrawable.getRenderscriptFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0/rs"))
                                assert(animatedVectorDrawable.getResFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0/res"))
                                assert(!animatedVectorDrawable.isOptional)
                                assert(animatedVectorDrawable.getRequestedCoordinates == null)
                                assert(animatedVectorDrawable.getResolvedCoordinates.getArtifactId == "animated-vector-drawable")
                                assert(animatedVectorDrawable.getResolvedCoordinates.getClassifier == null)
                                assert(animatedVectorDrawable.getResolvedCoordinates.getGroupId == "com.android.support")
                                assert(animatedVectorDrawable.getResolvedCoordinates.getPackaging == "aar")
                                assert(animatedVectorDrawable.getResolvedCoordinates.getVersion == "24.0.0")
                            }
                        }

                        assert(appcompatV7.getLintJar.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0/jars/lint.jar"))
                        assert(appcompatV7.getLocalJars.isEmpty)
                        assert(appcompatV7.getManifest.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0/AndroidManifest.xml"))
                        assert(appcompatV7.getProguardRules.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0/proguard.txt"))
                        assert(appcompatV7.getProject == null)
                        assert(appcompatV7.getProjectVariant == null)
                        assert(appcompatV7.getPublicResources.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0/public.txt"))
                        assert(appcompatV7.getRenderscriptFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0/rs"))
                        assert(appcompatV7.getResFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0/res"))
                        assert(!appcompatV7.isOptional)
                        assert(appcompatV7.getRequestedCoordinates == null)
                        assert(appcompatV7.getResolvedCoordinates.getArtifactId == "appcompat-v7")
                        assert(appcompatV7.getResolvedCoordinates.getClassifier == null)
                        assert(appcompatV7.getResolvedCoordinates.getGroupId == "com.android.support")
                        assert(appcompatV7.getResolvedCoordinates.getPackaging == "aar")
                        assert(appcompatV7.getResolvedCoordinates.getVersion == "24.0.0")
                    }
                    assert(dependencies.getProjects.isEmpty)
                }
                mainArtifact.getDependencies.getJavaLibraries.asScala match {
                  case javaLibraries =>
                    assert(javaLibraries.size == 1)
                    val javaLibrary = javaLibraries.head
                    assert(javaLibrary.getDependencies.isEmpty)
                    javaLibrary match {
                      case supportAnnotations =>
                        assert(supportAnnotations.getJarFile.getAbsolutePath.contains("sdk/extras/android/m2repository/com/android/support/support-annotations/"))
                        assert(!supportAnnotations.isProvided)
                        assert(supportAnnotations.getRequestedCoordinates == null)
                        assert(supportAnnotations.getResolvedCoordinates.getArtifactId == "support-annotations")
                        assert(supportAnnotations.getResolvedCoordinates.getClassifier == null)
                        assert(supportAnnotations.getResolvedCoordinates.getGroupId == "com.android.support")
                        assert(supportAnnotations.getResolvedCoordinates.getPackaging == "jar")
                        assert(supportAnnotations.getResolvedCoordinates.getVersion == "24.0.0")
                    }
                }
                mainArtifact.getDependencies.getLibraries.asScala match {
                  case libraries =>
                    assert(libraries.size == 1)
                    val appcompatV7 = libraries.head
                    assert(appcompatV7.getAidlFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0/aidl"))
                    assert(appcompatV7.getAssetsFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0/assets"))
                    assert(appcompatV7.getBundle.getAbsolutePath.contains("sdk/extras/android/m2repository/com/android/support/appcompat-v7/24.0.0/appcompat-v7-24.0.0.aar"))
                    assert(appcompatV7.getExternalAnnotations.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0/annotations.zip"))
                    assert(appcompatV7.getFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0"))
                    assert(appcompatV7.getJarFile.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0/jars/classes.jar"))
                    assert(appcompatV7.getJniFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0/jni"))
                    appcompatV7.getLibraryDependencies.asScala match {
                      case libraryDependencies =>
                        assert(libraryDependencies.size == 3)
                        libraryDependencies.head match {
                          case supportV4 =>
                            assert(supportV4.getAidlFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/aidl"))
                            assert(supportV4.getAssetsFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/assets"))
                            assert(supportV4.getBundle.getAbsolutePath.contains("sdk/extras/android/m2repository/com/android/support/support-v4/24.0.0/support-v4-24.0.0.aar"))
                            assert(supportV4.getExternalAnnotations.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/annotations.zip"))
                            assert(supportV4.getFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0"))
                            assert(supportV4.getJarFile.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jars/classes.jar"))
                            assert(supportV4.getJniFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jni"))
                            assert(supportV4.getLibraryDependencies.isEmpty)
                            assert(supportV4.getLintJar.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jars/lint.jar"))

                            val localJars = supportV4.getLocalJars.asScala
                            assert(localJars.size == 1)
                            assert(localJars.head.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jars/libs/internal_impl-24.0.0.jar"))

                            assert(supportV4.getManifest.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/AndroidManifest.xml"))
                            assert(supportV4.getProguardRules.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/proguard.txt"))
                            assert(supportV4.getProject == null)
                            assert(supportV4.getProjectVariant == null)
                            assert(supportV4.getPublicResources.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/public.txt"))
                            assert(supportV4.getRenderscriptFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/rs"))
                            assert(supportV4.getResFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/res"))
                            assert(!supportV4.isOptional)
                            assert(supportV4.getRequestedCoordinates == null)
                            assert(supportV4.getResolvedCoordinates.getArtifactId == "support-v4")
                            assert(supportV4.getResolvedCoordinates.getClassifier == null)
                            assert(supportV4.getResolvedCoordinates.getGroupId == "com.android.support")
                            assert(supportV4.getResolvedCoordinates.getPackaging == "aar")
                            assert(supportV4.getResolvedCoordinates.getVersion == "24.0.0")
                        }
                        libraryDependencies(1) match {
                          case vectorDrawable =>
                            assert(vectorDrawable.getAidlFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/aidl"))
                            assert(vectorDrawable.getAssetsFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/assets"))
                            assert(vectorDrawable.getBundle.getAbsolutePath.contains("sdk/extras/android/m2repository/com/android/support/support-vector-drawable/24.0.0/support-vector-drawable-24.0.0.aar"))
                            assert(vectorDrawable.getExternalAnnotations.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/annotations.zip"))
                            assert(vectorDrawable.getFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0"))
                            assert(vectorDrawable.getJarFile.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/jars/classes.jar"))
                            assert(vectorDrawable.getJniFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/jni"))
                            vectorDrawable.getLibraryDependencies.asScala match {
                              case supportV4libraryDependencies =>
                                assert(supportV4libraryDependencies.size == 1)
                                val supportV4 = supportV4libraryDependencies.head
                                assert(supportV4.getAidlFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/aidl"))
                                assert(supportV4.getAssetsFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/assets"))
                                assert(supportV4.getBundle.getAbsolutePath.contains("sdk/extras/android/m2repository/com/android/support/support-v4/24.0.0/support-v4-24.0.0.aar"))
                                assert(supportV4.getExternalAnnotations.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/annotations.zip"))
                                assert(supportV4.getFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0"))
                                assert(supportV4.getJarFile.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jars/classes.jar"))
                                assert(supportV4.getJniFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jni"))
                                assert(supportV4.getLibraryDependencies.isEmpty)
                                assert(supportV4.getLintJar.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jars/lint.jar"))

                                val localJars = supportV4.getLocalJars.asScala
                                assert(localJars.size == 1)
                                assert(localJars.head.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jars/libs/internal_impl-24.0.0.jar"))

                                assert(supportV4.getManifest.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/AndroidManifest.xml"))
                                assert(supportV4.getProguardRules.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/proguard.txt"))
                                assert(supportV4.getProject == null)
                                assert(supportV4.getProjectVariant == null)
                                assert(supportV4.getPublicResources.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/public.txt"))
                                assert(supportV4.getRenderscriptFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/rs"))
                                assert(supportV4.getResFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/res"))
                                assert(!supportV4.isOptional)
                                assert(supportV4.getRequestedCoordinates == null)
                                assert(supportV4.getResolvedCoordinates.getArtifactId == "support-v4")
                                assert(supportV4.getResolvedCoordinates.getClassifier == null)
                                assert(supportV4.getResolvedCoordinates.getGroupId == "com.android.support")
                                assert(supportV4.getResolvedCoordinates.getPackaging == "aar")
                            }
                            assert(vectorDrawable.getLintJar.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/jars/lint.jar"))
                            assert(vectorDrawable.getLocalJars.isEmpty)
                            assert(vectorDrawable.getManifest.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/AndroidManifest.xml"))
                            assert(vectorDrawable.getProguardRules.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/proguard.txt"))
                            assert(vectorDrawable.getProject == null)
                            assert(vectorDrawable.getProjectVariant == null)
                            assert(vectorDrawable.getPublicResources.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/public.txt"))
                            assert(vectorDrawable.getRenderscriptFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/rs"))
                            assert(vectorDrawable.getResFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/res"))
                            assert(!vectorDrawable.isOptional)
                            assert(vectorDrawable.getRequestedCoordinates == null)
                            assert(vectorDrawable.getResolvedCoordinates.getArtifactId == "support-vector-drawable")
                            assert(vectorDrawable.getResolvedCoordinates.getClassifier == null)
                            assert(vectorDrawable.getResolvedCoordinates.getGroupId == "com.android.support")
                            assert(vectorDrawable.getResolvedCoordinates.getPackaging == "aar")
                            assert(vectorDrawable.getResolvedCoordinates.getVersion == "24.0.0")
                        }
                        libraryDependencies.last match {
                          case animatedVectorDrawable =>
                            assert(animatedVectorDrawable.getAidlFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0/aidl"))
                            assert(animatedVectorDrawable.getAssetsFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0/assets"))
                            assert(animatedVectorDrawable.getBundle.getAbsolutePath.contains("sdk/extras/android/m2repository/com/android/support/animated-vector-drawable/24.0.0/animated-vector-drawable-24.0.0.aar"))
                            assert(animatedVectorDrawable.getExternalAnnotations.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0/annotations.zip"))
                            assert(animatedVectorDrawable.getFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0"))
                            assert(animatedVectorDrawable.getJarFile.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0/jars/classes.jar"))
                            assert(animatedVectorDrawable.getJniFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0/jni"))
                            animatedVectorDrawable.getLibraryDependencies.asScala match {
                              case animatedlibraryDependencies =>
                                assert(animatedlibraryDependencies.size == 1)
                                val supportVectorDrawable = animatedlibraryDependencies.head
                                assert(supportVectorDrawable.getAidlFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/aidl"))
                                assert(supportVectorDrawable.getAssetsFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/assets"))
                                assert(supportVectorDrawable.getBundle.getAbsolutePath.contains("sdk/extras/android/m2repository/com/android/support/support-vector-drawable/24.0.0/support-vector-drawable-24.0.0.aar"))
                                assert(supportVectorDrawable.getExternalAnnotations.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/annotations.zip"))
                                assert(supportVectorDrawable.getFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0"))
                                assert(supportVectorDrawable.getJarFile.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/jars/classes.jar"))
                                assert(supportVectorDrawable.getJniFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/jni"))
                                supportVectorDrawable.getLibraryDependencies.asScala match {
                                  case supportV4libraryDependencies =>
                                    assert(supportV4libraryDependencies.size == 1)
                                    val supportV4 = supportV4libraryDependencies.head
                                    assert(supportV4.getAidlFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/aidl"))
                                    assert(supportV4.getAssetsFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/assets"))
                                    assert(supportV4.getBundle.getAbsolutePath.contains("sdk/extras/android/m2repository/com/android/support/support-v4/24.0.0/support-v4-24.0.0.aar"))
                                    assert(supportV4.getExternalAnnotations.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/annotations.zip"))
                                    assert(supportV4.getFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0"))
                                    assert(supportV4.getJarFile.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jars/classes.jar"))
                                    assert(supportV4.getJniFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jni"))
                                    assert(supportV4.getLibraryDependencies.isEmpty)
                                    assert(supportV4.getLintJar.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jars/lint.jar"))

                                    val localJars = supportV4.getLocalJars.asScala
                                    assert(localJars.size == 1)
                                    assert(localJars.head.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/jars/libs/internal_impl-24.0.0.jar"))

                                    assert(supportV4.getManifest.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/AndroidManifest.xml"))
                                    assert(supportV4.getProguardRules.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/proguard.txt"))
                                    assert(supportV4.getProject == null)
                                    assert(supportV4.getProjectVariant == null)
                                    assert(supportV4.getPublicResources.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/public.txt"))
                                    assert(supportV4.getRenderscriptFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/rs"))
                                    assert(supportV4.getResFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-v4/24.0.0/res"))
                                    assert(!supportV4.isOptional)
                                    assert(supportV4.getRequestedCoordinates == null)
                                    assert(supportV4.getResolvedCoordinates.getArtifactId == "support-v4")
                                    assert(supportV4.getResolvedCoordinates.getClassifier == null)
                                    assert(supportV4.getResolvedCoordinates.getGroupId == "com.android.support")
                                    assert(supportV4.getResolvedCoordinates.getPackaging == "aar")
                                }
                                assert(supportVectorDrawable.getLintJar.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/jars/lint.jar"))
                                assert(supportVectorDrawable.getLocalJars.isEmpty)
                                assert(supportVectorDrawable.getManifest.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/AndroidManifest.xml"))
                                assert(supportVectorDrawable.getProguardRules.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/proguard.txt"))
                                assert(supportVectorDrawable.getProject == null)
                                assert(supportVectorDrawable.getProjectVariant == null)
                                assert(supportVectorDrawable.getPublicResources.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/public.txt"))
                                assert(supportVectorDrawable.getRenderscriptFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/rs"))
                                assert(supportVectorDrawable.getResFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/support-vector-drawable/24.0.0/res"))
                                assert(!supportVectorDrawable.isOptional)
                                assert(supportVectorDrawable.getRequestedCoordinates == null)
                                assert(supportVectorDrawable.getResolvedCoordinates.getArtifactId == "support-vector-drawable")
                                assert(supportVectorDrawable.getResolvedCoordinates.getClassifier == null)
                                assert(supportVectorDrawable.getResolvedCoordinates.getGroupId == "com.android.support")
                                assert(supportVectorDrawable.getResolvedCoordinates.getPackaging == "aar")
                            }
                            assert(animatedVectorDrawable.getLintJar.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0/jars/lint.jar"))
                            assert(animatedVectorDrawable.getLocalJars.isEmpty)
                            assert(animatedVectorDrawable.getManifest.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0/AndroidManifest.xml"))
                            assert(animatedVectorDrawable.getProguardRules.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0/proguard.txt"))
                            assert(animatedVectorDrawable.getProject == null)
                            assert(animatedVectorDrawable.getProjectVariant == null)
                            assert(animatedVectorDrawable.getPublicResources.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0/public.txt"))
                            assert(animatedVectorDrawable.getRenderscriptFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0/rs"))
                            assert(animatedVectorDrawable.getResFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/animated-vector-drawable/24.0.0/res"))
                            assert(!animatedVectorDrawable.isOptional)
                            assert(animatedVectorDrawable.getRequestedCoordinates == null)
                            assert(animatedVectorDrawable.getResolvedCoordinates.getArtifactId == "animated-vector-drawable")
                            assert(animatedVectorDrawable.getResolvedCoordinates.getClassifier == null)
                            assert(animatedVectorDrawable.getResolvedCoordinates.getGroupId == "com.android.support")
                            assert(animatedVectorDrawable.getResolvedCoordinates.getPackaging == "aar")
                            assert(animatedVectorDrawable.getResolvedCoordinates.getVersion == "24.0.0")
                        }
                    }
                    assert(appcompatV7.getLintJar.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0/jars/lint.jar"))
                    assert(appcompatV7.getLocalJars.isEmpty)
                    assert(appcompatV7.getManifest.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0/AndroidManifest.xml"))
                    assert(appcompatV7.getProguardRules.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0/proguard.txt"))
                    assert(appcompatV7.getProject == null)
                    assert(appcompatV7.getProjectVariant == null)
                    assert(appcompatV7.getPublicResources.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0/public.txt"))
                    assert(appcompatV7.getRenderscriptFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0/rs"))
                    assert(appcompatV7.getResFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/exploded-aar/com.android.support/appcompat-v7/24.0.0/res"))
                    assert(!appcompatV7.isOptional)
                    assert(appcompatV7.getRequestedCoordinates == null)
                    assert(appcompatV7.getResolvedCoordinates.getArtifactId == "appcompat-v7")
                    assert(appcompatV7.getResolvedCoordinates.getClassifier == null)
                    assert(appcompatV7.getResolvedCoordinates.getGroupId == "com.android.support")
                    assert(appcompatV7.getResolvedCoordinates.getPackaging == "aar")
                    assert(appcompatV7.getResolvedCoordinates.getVersion == "24.0.0")
                }
                assert(mainArtifact.getDependencies.getProjects.isEmpty)
                for (taskName <- mainArtifact.getIdeSetupTaskNames.asScala) {
                  assert(taskName == "generateDevelopReleaseSources")
                }
                assert(mainArtifact.getJavaResourcesFolder.getAbsolutePath.contains(s"$APP_BUILD_INTERMEDIATES_DIR/sourceFolderJavaResources/develop/release"))
                assert(mainArtifact.getMultiFlavorSourceProvider == null)
                assert(mainArtifact.getName == "_main_")
                mainArtifact.getVariantSourceProvider match {
                  case variantSourceProvider =>
                    assert(variantSourceProvider.getAidlDirectories.size() == 1)
                    assert(variantSourceProvider.getAidlDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/developRelease/aidl"))
                    assert(variantSourceProvider.getAssetsDirectories.size() == 1)
                    assert(variantSourceProvider.getAssetsDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/developRelease/assets"))
                    assert(variantSourceProvider.getCDirectories.size() == 1)
                    assert(variantSourceProvider.getCDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/developRelease/jni"))
                    assert(variantSourceProvider.getCppDirectories.size() == 1)
                    assert(variantSourceProvider.getCppDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/developRelease/jni"))
                    assert(variantSourceProvider.getJavaDirectories.size() == 1)
                    assert(variantSourceProvider.getJavaDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/developRelease/java"))
                    assert(variantSourceProvider.getJniLibsDirectories.size() == 1)
                    assert(variantSourceProvider.getJniLibsDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/developRelease/jniLibs"))
                    assert(variantSourceProvider.getManifestFile.getAbsolutePath.contains(s"$APP_SRC_DIR/developRelease/AndroidManifest.xml"))
                    assert(variantSourceProvider.getName == "developRelease")
                    assert(variantSourceProvider.getRenderscriptDirectories.size() == 1)
                    assert(variantSourceProvider.getRenderscriptDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/developRelease/rs"))
                    assert(variantSourceProvider.getResDirectories.size() == 1)
                    assert(variantSourceProvider.getResDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/developRelease/res"))
                    assert(variantSourceProvider.getResourcesDirectories.size() == 1)
                    assert(variantSourceProvider.getResourcesDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/developRelease/resources"))
                    assert(variantSourceProvider.getShadersDirectories.size() == 1)
                    assert(variantSourceProvider.getShadersDirectories.asScala.head.getAbsolutePath.contains(s"$APP_SRC_DIR/developRelease/shaders"))
                }
            }

            developRelease.getMergedFlavor match {
              case mergedFlavor =>
                assert(mergedFlavor.getApplicationId == "com.epy0n0ff.example")
                assert(mergedFlavor.getMaxSdkVersion == null)
                mergedFlavor.getMinSdkVersion match {
                  case minSdkVersion =>
                    assert(minSdkVersion.getApiLevel == 14)
                    assert(minSdkVersion.getApiString == "14")
                    assert(minSdkVersion.getCodename == null)
                }
                assert(mergedFlavor.getName == "")
                assert(mergedFlavor.getRenderscriptNdkModeEnabled == null)
                assert(mergedFlavor.getRenderscriptSupportModeEnabled == null)
                assert(mergedFlavor.getRenderscriptTargetApi == null)
                assert(mergedFlavor.getResourceConfigurations.isEmpty)
                assert(mergedFlavor.getSigningConfig == null)
                mergedFlavor.getTargetSdkVersion match {
                  case targetSdkVersion =>
                    assert(mergedFlavor.getTargetSdkVersion.getApiLevel == 24)
                    assert(mergedFlavor.getTargetSdkVersion.getApiString == "24")
                    assert(mergedFlavor.getTargetSdkVersion.getCodename == null)
                }
                assert(mergedFlavor.getTestApplicationId == null)
                assert(mergedFlavor.getTestFunctionalTest == null)
                assert(mergedFlavor.getTestHandleProfiling == null)
                assert(mergedFlavor.getTestInstrumentationRunner == null)
                assert(mergedFlavor.getTestInstrumentationRunnerArguments.isEmpty)
                mergedFlavor.getVectorDrawables match {
                  case vectorDrawables =>
                    val generateDensities = vectorDrawables.getGeneratedDensities.asScala
                    assert(generateDensities.size == 6)
                    assert(generateDensities.contains("ldpi"))
                    assert(generateDensities.contains("mdpi"))
                    assert(generateDensities.contains("hdpi"))
                    assert(generateDensities.contains("xhdpi"))
                    assert(generateDensities.contains("xxhdpi"))
                    assert(generateDensities.contains("xxxhdpi"))
                    assert(!vectorDrawables.getUseSupportLibrary)
                }
                assert(mergedFlavor.getVersionCode == 1)
                assert(mergedFlavor.getVersionName == "1.0")
                assert(mergedFlavor.getApplicationIdSuffix == null)
                assert(mergedFlavor.getBuildConfigFields.isEmpty)
                assert(mergedFlavor.getConsumerProguardFiles.isEmpty)
                assert(mergedFlavor.getDimension == null)
                assert(mergedFlavor.getJarJarRuleFiles.isEmpty)
                assert(mergedFlavor.getManifestPlaceholders.isEmpty)
                assert(mergedFlavor.getMultiDexEnabled == null)
                assert(mergedFlavor.getMultiDexKeepFile == null)
                assert(mergedFlavor.getMultiDexKeepProguard == null)
                assert(mergedFlavor.getProguardFiles.isEmpty)
                assert(mergedFlavor.getResValues.isEmpty)
                assert(mergedFlavor.getTestProguardFiles.isEmpty)
            }
            assert(developRelease.getName == "developRelease")
            developRelease.getProductFlavors.asScala match {
              case productFlavors =>
                assert(productFlavors.size == 1)
                assert(productFlavors.head == "develop")
            }
        }
    }
    assert(!android.isLibrary)
    project.close()
  }
}
