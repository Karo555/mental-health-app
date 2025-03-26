# Welcome to Auri ! 🌊
![enter image description here](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=Kotlin&logoColor=white)![enter image description here](https://img.shields.io/badge/firebase-ffca28?style=for-the-badge&logo=firebase&logoColor=black)

Auri is a Kotlin-based mobile application designed to provide psychological support and stress management tools. It leverages Firebase for data storage and incorporates various methods to help users track their emotional well-being, access guided exercises, and receive personalized recommendations.
---
## Stack 🧑‍💻
-   **Language**: Kotlin
-   **Database**: Firebase Firestore
-   **Authentication**: Firebase Authentication
-   **UI/UX**: Jetpack Compose (optional, if applicable)
--- 

## Features📌
-   **Mood Tracking**: Users can log their daily emotions and monitor trends over time.
-   **Guided Exercises**: A collection of relaxation techniques, breathing exercises, and mindfulness activities.
-   **Personalized Recommendations**: Based on user input, Auri suggests suitable exercises and coping strategies.
-   **Secure Cloud Storage**: Firebase is used to securely store user data while ensuring privacy.
-   **User Authentication**: Secure login and account management.
--- 
##  Screenshots 📷
<img src="./Screenshots/Welcome%20screen.png" alt="Welcome Screen" style="width:25%;"> <img src="./Screenshots/Home%20screen.png" alt="Welcome Screen" style="width:25%;"> <img src="./Screenshots/Insights%20screen.png" alt="Welcome Screen" style="width:25%;">
<img src="./Screenshots/History%20screen.png" alt="Welcome Screen" style="width:25%;">

---

## 📥 Installation
1. Clone the repository:  
   ```sh
   git clone https://github.com/your-repo/auri.git
   ```
2. Open the project in **Android Studio**.  
3. Sync dependencies using Gradle.  
4. Set up Firebase:
   - Create a Firebase project.
   - Enable Firestore and Authentication.
   - Download the `google-services.json` file and place it in the `app/` directory.  
5. Run the application on an emulator or physical device.

---

## 🔌 API Integration
Auri may include APIs for enhanced functionality:
- **OpenAI API** (optional) – For AI-driven suggestions.
- **Google Fit API** (optional) – Sync with health data.

Setup API keys in `local.properties`:
```properties
OPENAI_API_KEY=your_api_key_here
GOOGLE_FIT_API_KEY=your_api_key_here
```

---

## 🚧 Roadmap
- [x] Basic Mood Tracking  
- [x] User Authentication  
- [x] AI-driven insights  
- [ ] Community Support Features  
- [ ] Integration with Wearables  

---

## 👥 Contributing
1. Fork the repository  
2. Create a new branch:  
   ```sh
   git checkout -b feature-name
   ```
3. Commit changes:  
   ```sh
   git commit -m "Add new feature"
   ```
4. Push to the branch:  
   ```sh
   git push origin feature-name
   ```
5. Open a Pull Request  

---

## 📄 License
This project is licensed under the **Apache License 2.0**.

---
