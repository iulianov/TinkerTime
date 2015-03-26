TinkerTime
==========

Automatically Updating Mod Manager for Kerbal Space Program

Master: [![Build Status](https://travis-ci.org/oharaandrew314/TinkerTime.svg?branch=master)](https://travis-ci.org/oharaandrew314/TinkerTime)

### Description
Tinker Time is a Mod Manager for Kerbal Space Program that will allow you to automatically update, enable, and disable all of your mods.  All you have to do is enter the URL to the mod webpage, and Tinker Time will do the rest of the work for you.  It will even manage your ModuleManager Installation for you. :)

### Requirements
- [Java 7](https://java.com/en/download/index.jsp) or better
  - OpenJDK is not supported, but it may work

#### Supported Mod Hosting Sites
- Curse
- Github (when compiled assets are released)
- KerbalStuff



### Downloads
Please download the latest version from [KerbalStuff](https://kerbalstuff.com/mod/243)

### Wiki
The [Tinker Time Wiki](https://github.com/oharaandrew314/TinkerTime/wiki) contains many useful resources with instructions and known issues.

### Special Thanks
- Contributors ([apemanzilla](https://github.com/apemanzilla), [grossws](https://github.com/grossws))

- Beta Testers ([foonix](https://github.com/foonix), [jcsntoll](https://github.com/jcsntoll), and [apemanzilla](https://github.com/apemanzilla))

### Change Log

v2.0 (planned)

This major update is planned to feature a visual design overhaul, and .version file integration

v1.3
This update focuses on fixing annoyances, in order to provide a better general UX

- New Features
  - Add an options field to set KSP Launch Options
  - Updating the options will no longer require an app restart
  - Now scans the GameData directory to see which mods are enabled
    - This fixes issues with the mods file being out of sync with the GameData directory
  - Now scrapes Github using the API, by default
    - If the API limit is reached, will fallback to the HTML scraper
- Fixes
  - Mod file caches between KSP installations are now separate
    - This fixes issues when updating or deleting a mod in one installation, which would then affect the other
    - Caches are now stored in a "TinkerTime" directory within your KSP installation
- Other Changes
  - Tinker Time now updates itself using KerbalStuff, rather than Github
  - ModuleManager has been further integrated as a mod, (as opposed to a special entity)
    - This will cause a minor issue the first time you update it
    - After updating, restart TinkerTime, and delete the old ModuleManager dll in your GameData directory (if there are two)
  - Mod file no longer needs to save image URLs and enabled state

v1.2
- New Features
  - Automatically check for TinkerTime update, and prompt user if new version is available
  - Mod Page Caching to now persist for 10 minutes (rather than just for a single workflow)
- Fixes
  - Update Github Crawler to match new DOM
  - Github Crawler to skip latest release if it does not contain a user-uploaded asset
  - Fix issue where illegal file characters were causing some mods to fail to download
  - "Update All" button to only update mods which have an update available

v1.1.1
- Updated for KerbalStuff content-provider change

v1.1
- New Features
  - KSP Launcher (supports Windows, OSX, and Linux)
    - On Windows, will ask user if they wish to use 64-bit, otherwise, automatic
  - Add Mod Import/Export Functionality (to share mod packs)
  - Add option to set windowed mode of KSP to borderless
  - Mod Host and Version is now displayed in Mod View
  - Support for Github Mods with multiple assets per release
- Fixes
  - Overhauled Zip Archive Analysis for more accurate mod installations
  - Set maximum size for Mod Image preview
  - Fix Typo in TinkerTime Options Window
  - Fix highlighting of toolbar buttons after clicking

v1.0
- New Features:
  - Module Manager is now considered a (non-removable) mod
  - Can now add local mod zip files (non-updateable)
  - Now fully using glyphicons (license in about)
  - Windows are now centered in the screen
  - Button to export JSON data for enabled mods
  - Will now delete old image caches and mods
  - Tinker Time Updater is simplified
  - When mod update is available, add message to mod description
- Fixes:
  - Fixed issue where variations of supported domains were not supported. e.g. beta.kerbalstuff.com
  - Fixed issue where mod list would not always load on startup
  - Fixed an issue where a module with a dependent mod would not be disabled even
    if the dependent mod was disabled
  - Improve Github mod URL analysis robustness
  - Fix Json caching for Jenkins crawler (less annoyance for [sarbian](https://github.com/sarbian))

v0.7
- KerbalStuff Mod Support
- Graphical Toolbar
- New GUI Theme (Nimbus)
- Overhauled Mod Archive Inspector (better mod compatability)
- Supported KSP Version will be disaplyed for each mod
- Automated JAR file generation with Gradle
- Travis-CI integration for automated testing

v0.6
- Github Mod Support
- GUI Mod List is to maintain proper order
- on start, update Module manager, and check for Mod updates
  - Can be Disabled through new Options Window
- Improvements to Mod Archive Inspector
- Improvements to Background Task Processor
- Each KSP Installation to have its own separate mods configuration
- Various other under-the-hood improvements

v0.5
- Initial Release
- Curse.com support

### License
<a rel="license" href="http://creativecommons.org/licenses/by-sa/4.0/"><img alt="Creative Commons License" style="border-width:0" src="https://i.creativecommons.org/l/by-sa/4.0/88x31.png" /></a><br /><span xmlns:dct="http://purl.org/dc/terms/" href="http://purl.org/dc/dcmitype/InteractiveResource" property="dct:title" rel="dct:type">Tinker Time</span> by <span xmlns:cc="http://creativecommons.org/ns#" property="cc:attributionName">Andrew O'Hara</span> is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by-sa/4.0/">Creative Commons Attribution-ShareAlike 4.0 International License</a>.<br />Based on a work at <a xmlns:dct="http://purl.org/dc/terms/" href="https://github.com/oharaandrew314/TinkerTime" rel="dct:source">https://github.com/oharaandrew314/TinkerTime</a>.
