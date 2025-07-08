This is a Kotlin Multiplatform project targeting Android, iOS, Desktop.

**Tech Stacks**
Kotlin
Jetpack Compose for UI
Koin for Dependacy Injection
SqlDelight for local DB
Coroutine with Stateflow
MVVM for Architech Pattern

**ScreenShots**

**Android**
![android-1](https://github.com/user-attachments/assets/df30b1ad-3356-412e-acf5-a8c45621b747)
![android-2](https://github.com/user-attachments/assets/58c507a5-e020-4e50-a350-37c3de781dfd)
![android-3](https://github.com/user-attachments/assets/912d6c29-3a75-4b1b-bde5-c3779453674d)
![andorid-4](https://github.com/user-attachments/assets/d968b89f-2fd1-404f-b69c-279e2a423a0e)

**IOS**

![IOS-1](https://github.com/user-attachments/assets/892347ff-b7f4-4699-bd4b-ef55471b1bd8)![IOS-4](https://github.com/user-attachments/assets/6d881660-2cb5-4bec-a668-7d1335f30447)
![IOS-5](https://github.com/user-attachments/assets/95a04206-1b47-45e1-9f76-937d36f40cfc)
![IOS-3](https://github.com/user-attachments/assets/12a66ae6-0945-4c57-9433-d221ca71b619)
![IOS-2](https://github.com/user-attachments/assets/ccf79175-e1c3-487b-bf86-5fb5e2021e13)

**Desktop**
<img width="803" alt="Screenshot 2025-07-08 at 8 57 42 PM" src="https://github.com/user-attachments/assets/c12173ca-9b58-47a5-85d2-6d520b2fdb2c" />



* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html),
[Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform/#compose-multiplatform),
[Kotlin/Wasm](https://kotl.in/wasm/)…

We would appreciate your feedback on Compose/Web and Kotlin/Wasm in the public Slack channel [#compose-web](https://slack-chats.kotlinlang.org/c/compose-web).
If you face any issues, please report them on [GitHub](https://github.com/JetBrains/compose-multiplatform/issues).
