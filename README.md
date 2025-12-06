# Donate+

Donate+ is an Android app that connects blood donors with recipients. Users can create blood requests with a location, receive responses, and find nearby hospitals and blood banks. The app uses Firebase for authentication/data and Google Maps/Places for location features.

## Features
- Email/password sign up and log in with email verification and password reset flow.
- Profile management, including blood group, contact details, and last donation date.
- News Feed of blood requests, highlighting urgent ones and linking to detailed view.
- Create new requests with required date, contact info, blood group, and location picked on a map.
- Respond to requests; seekers can review responders, mark them called/enlisted, add notes, and view an enlisted list.
- Personal dashboards for `My Posts` and `My Responses`.
- Notifications stream filtered by the user’s blood group.
- Hospital & Blood Bank directory with tap-to-call and map view for selected locations.
- Map support for showing a post’s location or picking an address via GPS/search.
- Settings screen with profile updates and logout.

## Tech Stack
- Android (Java), minSdk 19, targetSdk 28.
- Firebase: Auth, Realtime Database, Analytics (see `app/google-services.json`).
- Google Play Services: Maps, Places, Location (`map_key` in `app/src/main/res/values/map_key.xml`).
- UI: AndroidX AppCompat/ConstraintLayout/Material Components, RecyclerView.

## Project Layout
- `app/src/main/java/com/sbrotee63/donate/` – Activities, models, and RecyclerView adapters (news feed, posts/responses, notifications, hospital directory, profile/settings, maps).
- `app/src/main/res/layout/` – Screen layouts, list rows, and map/info window UI.
- `app/src/main/res/values/` – Strings, colors, styles, map key.
- `app/google-services.json` – Firebase config (duplicate copy also in `app/src/google-services.json`).
- `app/release/app-release.apk` – Prebuilt release APK.
- Gradle wrapper at the repository root (`./gradlew`) with module config in `app/build.gradle`.

## Setup
1. Prereqs: JDK 8+, Android Studio (or SDK) with Android 28 platform tools.
2. Firebase: create a project, enable Email/Password auth and Realtime Database, then place your `google-services.json` under `app/` (replace the sample copy if needed).
3. Maps: supply a valid Maps/Places API key in `app/src/main/res/values/map_key.xml`.
4. Build: `./gradlew clean assembleDebug` (or use Android Studio). Debug APK will be under `app/build/outputs/apk/debug/`.
5. Run on a device/emulator; allow location permissions to use mapping and nearby address pickers.
6. Release: `./gradlew assembleRelease` produces an unsigned APK; configure signing in `app/build.gradle` or via Android Studio before distribution.

## Usage Notes
- New accounts must verify the email before signing in.
- Location picker and GPS shortcuts are available when posting requests; you can also view a post or hospital location on the map.
- Responding to a request adds you to the seeker’s responder list and their notifications stream for that blood group.

## Testing
- Unit tests: `./gradlew test`.
- Instrumented tests: `./gradlew connectedAndroidTest` (requires an attached/emulated device with Google APIs).
