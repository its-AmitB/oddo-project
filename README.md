# 🚀 CivicTrack — Backend API

![Node.js](https://img.shields.io/badge/Node.js-339933?style=flat&logo=nodedotjs&logoColor=white)
![Express.js](https://img.shields.io/badge/Express.js-000000?style=flat&logo=express&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-47A248?style=flat&logo=mongodb&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=flat&logo=jsonwebtokens)
![Deployed on Render](https://img.shields.io/badge/Deploy-Render-46E3B7?style=flat&logo=render)

**CivicTrack** is the backend API powering the CivicTrack Android app.  
It enables citizens to **report, track, and resolve local civic issues** like road damage, garbage overflow, and water leaks — all in real-time.

---

## ✨ Features
- 🔐 **User Authentication** with JWT
- 📍 **Location-Based Filtering** (3–5 km radius)
- 🖼️ **Image Upload Support**
- 📦 **MongoDB Atlas** cloud database
- 🌍 **RESTful API** ready for mobile integration
- ☁️ **Deployed** and running on Render.com

---

## 🛠 Tech Stack
- **Backend:** Node.js, Express.js
- **Database:** MongoDB Atlas (Mongoose ODM)
- **Authentication:** JWT (JSON Web Token)
- **File Uploads:** Multer
- **Deployment:** Render.com

---

## 📡 API Endpoints

| Method | Endpoint                  | Description               | Auth Required |
|--------|---------------------------|---------------------------|--------------|
| `POST` | `/api/auth/register`      | Register a new user       | ❌ No        |
| `POST` | `/api/auth/login`         | Login and get JWT token   | ❌ No        |
| `POST` | `/api/issues`             | Report a new issue        | ✅ Yes       |
| `GET`  | `/api/issues`             | Get issues in radius      | ✅ Yes       |
| `PATCH`| `/api/issues/:id/status`  | Update issue status       | ✅ Yes       |
| `GET`  | `/health`                 | API health check          | ❌ No        |

---

## ⚙️ Environment Variables
Create a `.env` file in the root directory with the following:

```env
PORT=10000
NODE_ENV=production
MONGO_URI=mongodb+srv://<username>:<password>@cluster0.sj6khmg.mongodb.net/civictrack?retryWrites=true&w=majority&appName=Cluster0
JWT_SECRET=<your-secret-key>
UPLOAD_PATH=uploads/
MAX_FILE_SIZE=5242880
