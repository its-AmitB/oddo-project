# Render.com Deployment Guide for CivicTrack Backend

## Prerequisites

1. **MongoDB Atlas Account** (Free tier available)
2. **Render.com Account** (Free tier available)
3. **GitHub Repository** with your code

## Step 1: Set up MongoDB Atlas

1. Go to [MongoDB Atlas](https://www.mongodb.com/atlas)
2. Create a free account
3. Create a new cluster (M0 Free tier)
4. Create a database user with password
5. Get your connection string
6. Add your IP address to the whitelist (or use 0.0.0.0/0 for all IPs)

## Step 2: Prepare Environment Variables

Create a `.env` file locally with these variables:

```env
PORT=10000
NODE_ENV=production
MONGO_URI=mongodb+srv://username:password@cluster.mongodb.net/civictrack
JWT_SECRET=your_very_long_and_secure_jwt_secret_key_here
UPLOAD_PATH=uploads/
MAX_FILE_SIZE=5242880
```

## Step 3: Deploy on Render.com

1. **Sign up/Login to Render.com**
2. **Create New Web Service**
   - Connect your GitHub repository
   - Select the repository
   - Choose the branch (main/master)

3. **Configure the service:**
   - **Name**: `civictrack-backend`
   - **Environment**: `Node`
   - **Build Command**: `npm install`
   - **Start Command**: `npm start`
   - **Plan**: Free

4. **Add Environment Variables:**
   - Go to Environment tab
   - Add these variables:
     - `PORT`: `10000`
     - `NODE_ENV`: `production`
     - `MONGO_URI`: Your MongoDB Atlas connection string
     - `JWT_SECRET`: Your secure JWT secret
     - `UPLOAD_PATH`: `uploads/`
     - `MAX_FILE_SIZE`: `5242880`

5. **Deploy**
   - Click "Create Web Service"
   - Wait for deployment to complete

## Step 4: Update Android App

After deployment, you'll get a URL like: `https://your-app-name.onrender.com`

Update your Android app's API base URL in the NetworkModule:

```kotlin
// In NetworkModule.kt, change the base URL to your Render URL
private const val BASE_URL = "https://your-app-name.onrender.com/api/v1/"
```

## Step 5: Test the API

Test your deployed API:
- Health check: `https://your-app-name.onrender.com/health`
- API endpoints: `https://your-app-name.onrender.com/api/v1/...`

## Important Notes

1. **Free Tier Limitations:**
   - Render free tier has cold starts (first request may be slow)
   - 750 hours/month free
   - Service sleeps after 15 minutes of inactivity

2. **File Uploads:**
   - Render's free tier doesn't persist files
   - Consider using Cloudinary or AWS S3 for file storage

3. **CORS:**
   - Your server already has CORS enabled
   - You may need to update CORS settings for your domain

## Troubleshooting

1. **Build fails**: Check if all dependencies are in package.json
2. **Database connection fails**: Verify MongoDB Atlas connection string
3. **Environment variables**: Make sure all required variables are set in Render dashboard
4. **Port issues**: Render uses port 10000 by default, but your app should use `process.env.PORT`

## Next Steps

1. Deploy the backend successfully
2. Update your Android app with the new API URL
3. Test all functionality
4. Consider upgrading to paid plans for better performance 