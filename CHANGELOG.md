# Changelog

All notable changes to the GoSu SDK Demo project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [14.1.702] - 2025-07-19

### Added
- Updated to GoSu SDK v1.2.13
- Firebase Crashlytics integration
- Comprehensive ProGuard rules for all dependencies
- Detailed README with setup instructions
- Contributing guidelines (CONTRIBUTING.md)
- MIT License file
- Comprehensive .gitignore for security
- Example configuration files
- Support for Android API Level 35
- Java 17 compatibility

### Changed
- Updated compileSdk to 35
- Updated targetSdk to 35
- Minimum SDK raised to 23 (Android 6.0)
- Updated Firebase SDK to latest version (32.8.0)
- Updated Facebook SDK to v17.0.0
- Updated Google Play Billing to v8.0.0
- Updated AppFlyer SDK to v6.14.0
- Improved build configuration with proper signing setup
- Enhanced output APK naming convention
- Better organized dependencies with clear categorization

### Fixed
- Build configuration issues
- Dependency conflicts
- ProGuard rules for all integrated SDKs
- Security issues with exposed credentials

### Security
- Removed hardcoded credentials from build files
- Added secure configuration management
- Protected sensitive files with .gitignore
- Added example configuration files instead of real ones

## [13.6.700] - Previous Version

### Features
- Basic GoSu SDK integration
- Firebase Analytics
- Facebook SDK integration
- Google Play Billing
- Basic project structure

---

## Release Notes

### v14.1.702 (Current)

This version represents a major refactoring of the project to make it suitable for public GitHub publishing. Key improvements include:

**Security Enhancements:**
- All sensitive credentials and keys removed
- Secure configuration management implemented
- Comprehensive .gitignore to prevent accidental commits

**Development Experience:**
- Complete setup documentation
- Contributing guidelines
- Troubleshooting guides
- Example configuration files

**Technical Updates:**
- Latest Android SDK support (API 35)
- Updated to newest dependency versions
- Improved build performance
- Better ProGuard configuration

**Project Structure:**
- Clean, organized codebase
- Proper documentation
- Standard open-source project files
- Clear separation of concerns

### Migration Guide

If upgrading from a previous version:

1. **Update Build Configuration:**
   - Update `compileSdk` and `targetSdk` to 35
   - Update minimum SDK to 23
   - Update dependency versions as shown in build.gradle

2. **Configure Security:**
   - Remove any hardcoded credentials
   - Set up local.properties for sensitive data
   - Use environment variables for CI/CD

3. **Update Dependencies:**
   - Replace old GoSu SDK with v1.2.13
   - Update Firebase, Facebook, and other SDKs
   - Review and update ProGuard rules

4. **Documentation:**
   - Follow new README setup instructions
   - Configure Firebase and Facebook properly
   - Set up signing configuration securely

### Known Issues

- None currently reported

### Upcoming Features

- Enhanced error handling examples
- Additional SDK integration samples
- Performance optimization guides
- Advanced ProGuard configuration examples

---

For technical support or questions about this changelog, please visit our [GitHub Issues](https://github.com/gosusdk/android-gamesdk/issues) page.
