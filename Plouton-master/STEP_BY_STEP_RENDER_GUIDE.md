# Complete Step-by-Step Render.com Deployment Guide

## 🎯 What You'll Achieve
- Deploy your CivicTrack backend API to Render.com
- Connect it to MongoDB Atlas database
- Make your API accessible worldwide
- Connect your Android app to the deployed API

---

## 📋 Prerequisites Checklist

Before starting, make sure you have:
- [ ] GitHub account
- [ ] Render.com account (free)
- [ ] MongoDB Atlas account (free)
- [ ] Your code pushed to GitHub

---

## 🗄️ Step 1: Set Up MongoDB Atlas Database

### 1.1 Create MongoDB Atlas Account
1. Go to [MongoDB Atlas](https://www.mongodb.com/atlas)
2. Click "Try Free" or "Sign Up"
3. Fill in your details and create account

### 1.2 Create Database Cluster
1. Click "Build a Database"
2. Choose "FREE" tier (M0)
3. Select your preferred cloud provider (AWS/Google Cloud/Azure)
4. Choose a region close to you
5. Click "Create"

### 1.3 Set Up Database Access
1. In the left sidebar, click "Database Access"
2. Click "Add New Database User"
3. Choose "Password" authentication
4. Create a username (e.g., `civictrack_user`)
5. Create a strong password (save this!)
6. Select "Read and write to any database"
7. Click "Add User"

### 1.4 Set Up Network Access
1. In the left sidebar, click "Network Access"
2. Click "Add IP Address"
3. Click "Allow Access from Anywhere" (for development)
4. Click "Confirm"

### 1.5 Get Your Connection String
1. Click "Database" in the left sidebar
2. Click "Connect"
3. Choose "Connect your application"
4. Copy the connection string
5. Replace `<password>` with your actual password
6. Replace `<dbname>` with `civictrack`

**Your connection string will look like:**
```
mongodb+srv://civictrack_user:yourpassword@cluster0.xxxxx.mongodb.net/civictrack
```

---

## 🚀 Step 2: Prepare Your Code for Deployment

### 2.1 Push Code to GitHub
1. Create a new repository on GitHub
2. Upload your project files to the repository
3. Make sure your repository is public (for free Render.com deployment)

### 2.2 Verify Your Files
Ensure these files are in your repository:
- ✅ `package.json`
- ✅ `server.js`
- ✅ `config/db.js`
- ✅ All route files
- ✅ All middleware files

---

## 🌐 Step 3: Deploy to Render.com

### 3.1 Create Render.com Account
1. Go to [Render.com](https://render.com)
2. Click "Get Started for Free"
3. Sign up with your GitHub account

### 3.2 Create New Web Service
1. In your Render dashboard, click "New +"
2. Select "Web Service"
3. Connect your GitHub account if not already connected
4. Select your repository
5. Click "Connect"

### 3.3 Configure Your Service

Fill in these details:

**Basic Settings:**
- **Name**: `civictrack-backend` (or any name you prefer)
- **Environment**: `Node`
- **Region**: Choose closest to your users
- **Branch**: `main` (or your default branch)
- **Root Directory**: Leave empty (if your code is in root)

**Build & Deploy Settings:**
- **Build Command**: `npm install`
- **Start Command**: `npm start`
- **Plan**: `Free`

### 3.4 Add Environment Variables

Click on "Environment" tab and add these variables:

| Variable Name | Value |
|---------------|-------|
| `PORT` | `10000` |
| `NODE_ENV` | `production` |
| `MONGO_URI` | Your MongoDB connection string from Step 1.5 |
| `JWT_SECRET` | A long random string (e.g., `my-super-secret-jwt-key-2024-civictrack`) |
| `UPLOAD_PATH` | `uploads/` |
| `MAX_FILE_SIZE` | `5242880` |

### 3.5 Deploy
1. Click "Create Web Service"
2. Wait for deployment (usually 2-5 minutes)
3. You'll see build logs in real-time

---

## ✅ Step 4: Test Your Deployment

### 4.1 Check Deployment Status
1. In your Render dashboard, click on your service
2. Look for green "Live" status
3. Copy your service URL (e.g., `https://civictrack-backend.onrender.com`)

### 4.2 Test API Endpoints
Open your browser or use curl to test:

```bash
# Health check
curl https://your-app-name.onrender.com/health

# Should return: {"status":"OK","message":"CivicTrack API is running"}

# Test categories endpoint
curl https://your-app-name.onrender.com/api/v1/categories

# Test auth endpoint
curl -X POST https://your-app-name.onrender.com/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"password123","name":"Test User"}'
```

---

## 📱 Step 5: Update Your Android App

### 5.1 Find Your NetworkModule
Locate this file in your Android project:
```
app/src/main/java/com/plouton/CiviLized/di/NetworkModule.kt
```

### 5.2 Update Base URL
Change the BASE_URL to your Render.com URL:

```kotlin
// Change this line:
private const val BASE_URL = "https://your-app-name.onrender.com/api/v1/"
```

### 5.3 Build and Test
1. Build your Android app
2. Install on your device
3. Test all features (login, report issues, etc.)

---

## 🔧 Step 6: Troubleshooting Common Issues

### Issue: Build Fails
**Solution:**
- Check that all dependencies are in `package.json`
- Ensure `package.json` has correct scripts
- Verify Node.js version compatibility

### Issue: Database Connection Fails
**Solution:**
- Double-check your MongoDB connection string
- Verify username/password are correct
- Ensure network access allows all IPs

### Issue: API Returns 404
**Solution:**
- Check that your routes are properly configured
- Verify the API endpoints match your Android app
- Test with Postman or curl

### Issue: CORS Errors
**Solution:**
- Your server already has CORS enabled
- If issues persist, check the CORS configuration in `server.js`

---

## 🎉 Step 7: Verify Everything Works

### 7.1 Test Checklist
- [ ] Health endpoint responds
- [ ] User registration works
- [ ] User login works
- [ ] Issue creation works
- [ ] Categories are loaded
- [ ] Android app connects successfully

### 7.2 Monitor Your Service
1. In Render dashboard, check:
   - Service status (should be "Live")
   - Logs for any errors
   - Usage statistics

---

## 📞 Getting Help

If you encounter issues:

1. **Check Render Logs**: In your service dashboard, click "Logs"
2. **Check MongoDB Atlas**: Verify database connection
3. **Test Locally**: Run your server locally to debug
4. **Render Support**: Use Render's documentation and support

---

## 🚀 Your API is Now Live!

Your CivicTrack backend is now accessible at:
`https://your-app-name.onrender.com`

Your Android app can now connect to this deployed API and work from anywhere in the world!

**Remember:**
- Free tier has cold starts (first request may be slow)
- Service sleeps after 15 minutes of inactivity
- Consider upgrading for production use 