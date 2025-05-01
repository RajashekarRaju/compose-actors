# 📸 Screenshot Testing Workflow

When you add or change any `@Preview` Composable UIs, you’ll need to update or validate your
images.

---

## 1. Update All Screenshots

This regenerates every recorded screenshot in your `src/screenshotTests` folder.

```bash
# macOS / Linux
./gradlew updateDebugScreenshotTest --stacktrace

# Windows PowerShell
.\gradlew.bat updateDebugScreenshotTest --stacktrace
```

## 2. Validate All Screenshots

To verify that your current UI still matches the images updated before:

```bash
# macOS / Linux
./gradlew validateDebugScreenshotTest

# Windows PowerShell
.\gradlew.bat validateDebugScreenshotTest
```

## 3. Update Only New or Specific Screenshots

Regenerating all images shouldn't be necessary. Instead target only the tests you’ve added or
changed:

```bash
# macOS / Linux
./gradlew updateDebugScreenshotTest \
  -Pandroid.testInstrumentationRunnerArguments.class=com.your.package.LoginScreenUIPreviewTest,\
com.your.package.AboutScreenUIPreviewTest

# Windows PowerShell
.\gradlew.bat updateDebugScreenshotTest `
  "-Pandroid.testInstrumentationRunnerArguments.class=com.your.package.LoginScreenUIPreviewTest,com.your.package.AboutScreenUIPreviewTest"
```

> Replace the FQCNs with your test classes.  
> This will only re-record those two previews.

You can also just specific one's:

```bash
# macOS / Linux
./gradlew validateDebugScreenshotTest \
  -Pandroid.testInstrumentationRunnerArguments.class=com.your.package.LoginScreenUIPreviewTest

# Windows PowerShell
.\gradlew.bat validateDebugScreenshotTest `
  "-Pandroid.testInstrumentationRunnerArguments.class=com.your.package.LoginScreenUIPreviewTest"
```