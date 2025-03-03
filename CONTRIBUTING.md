# Contribution Guidelines

To help us maintain a high-quality, consistent codebase, please follow these steps before opening a pull request.

## 1. Run All Tests

Before submitting your changes, make sure all tests pass. Run the following command:

```bash
./gradlew testDebugUnitTest validateDebugScreenshotTest connectedDebugAndroidTest --stacktrace
```

This command runs unit tests, screenshot tests, and connected Android tests. If any tests fail, please fix the issues before pushing your changes.

## 2. Ensure Code Style Compliance with ktlint

Our project uses [ktlint](https://github.com/pinterest/ktlint) to enforce code style guidelines. Please run the following commands:

- **Check code style:**

  ```bash
  ./gradlew ktlintCheck
  ```

- **Automatically format your code:**

  ```bash
  ./gradlew ktlintFormat
  ```

If the `ktlintCheck` command reports any violations, run `ktlintFormat` to auto-correct them. Review any changes made by ktlint and commit them before pushing.

## 3. Final Steps

Once you’ve confirmed that all tests pass and your code meets the style guidelines:
- Commit your changes.
- Push your branch.
- Open a pull request.

By following these steps, you help ensure that every contribution maintains our code quality and consistency.
