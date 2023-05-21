# RUNIT Android App

The RUNIT app allows users to track and save their runs/journeys, along with notes and ratings. The app keeps a record of all their runs, enabling them to review their previous journeys and view achievements such as total runs, highest distance covered, and longest run. The information is presented in a user-friendly manner, facilitating data analysis and understanding.
Resolution: 1080 x 1920
Density: 420dpi
Android API version: 29 (Android 10.0)

<img src="https://github.com/lakshmi-shravya/RunningApp/assets/89875894/bcb49f9a-e8da-4023-af9e-5326b031d34a" alt="Screenshot" width="700" height="300">


## Key Features:
* Track and save runs with notes and ratings
* View and analyze run data, including achievements
* Present data on widgets and other applications
* User-friendly app design for easy management
* Utilizes MVVM architecture for efficient app component implementation


## App Components:
- Activities: The app consists of four activities, including the Main Activity, Tracker, DisplayInfoAfterRun, and PreviousRuns.
- Services: A foreground service, "MyLocationTrackerService," is utilized to track the user's journey in the background and provide active updates.
- Content Provider: A content provider is implemented to enable access to app data by other applications, facilitating widget integration and data sharing.
- App Architecture: The app follows the MVVM architecture pattern, utilizing Room database for efficient data management.

## App architechture

<img src="https://github.com/lakshmi-shravya/RunningApp/assets/89875894/5e9e5c5f-3487-4bc8-8ce6-0f945938efe0" alt="Screenshot" width="400" height="600">

### Tracker

<img src="https://github.com/lakshmi-shravya/RunningApp/assets/89875894/644542a1-3ff0-4a2e-baaf-cef36f7bab02" alt="Screenshot" width="400" height="200">


