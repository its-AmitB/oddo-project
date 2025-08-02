# CivicTrack Setup Guide

This guide will help you set up and run the CivicTrack Android application with its Node.js backend server.

## Table of Contents
1. [Prerequisites](#prerequisites)
2. [Backend Setup](#backend-setup)
3. [Android Setup](#android-setup)
4. [Running the Application](#running-the-application)
5. [Testing the API](#testing-the-api)
6. [Troubleshooting](#troubleshooting)

## Prerequisites

### Backend Requirements
- Node.js (v16 or higher)
- MongoDB (local installation or MongoDB Atlas account)
- npm or yarn package manager

### Android Requirements
- Android Studio (latest version)
- Android SDK (API level 24 or higher)
- Java Development Kit (JDK 11 or higher)
- Android device or emulator

## Backend Setup

### 1. Install Dependencies

Navigate to the backend directory and install dependencies:

```bash
cd Plouton-master
npm install
```

### 2. Environment Configuration

Create a `.env` file in the root directory:

```bash
# Copy the example environment file
cp env.example .env
```

Edit the `.env` file with your configuration:

```env
# Server Configuration
PORT=5000
NODE_ENV=development

# MongoDB Configuration
MONGO_URI=mongodb://localhost:27017/civictrack

# JWT Configuration
JWT_SECRET=your_jwt_secret_key_here_make_it_long_and_secure

# File Upload Configuration
UPLOAD_PATH=uploads/
MAX_FILE_SIZE=5242880
```

### 3. Database Setup

#### Option A: Local MongoDB
1. Install MongoDB on your system
2. Start MongoDB service
3. Create database: `civictrack`

#### Option B: MongoDB Atlas
1. Create a MongoDB Atlas account
2. Create a new cluster
3. Get your connection string
4. Update `MONGO_URI` in `.env` file

### 4. Seed Initial Data

Run the seed script to populate initial categories and admin user:

```bash
npm run seed
```

This will create:
- Default issue categories (Potholes, Street Lights, etc.)
- Admin user (email: admin@civictrack.com, password: admin123)

### 5. Start the Backend Server

```bash
# Development mode with auto-restart
npm run dev

# Production mode
npm start
```

The server will start on `http://localhost:5000`

## Android Setup

### 1. Open Project in Android Studio

1. Launch Android Studio
2. Select "Open an existing project"
3. Navigate to `Plouton-master/app` directory
4. Click "OK"

### 2. Sync Gradle Files

1. Wait for Android Studio to sync Gradle files
2. If prompted, update Gradle version
3. Install any missing SDK components

### 3. Configure API Base URL

Edit the NetworkModule.kt file to set your backend URL:

```kotlin
// In NetworkModule.kt
private const val BASE_URL = "http://10.0.2.2:5000/api/v1/" // For Android emulator
// or
private const val BASE_URL = "http://your-local-ip:5000/api/v1/" // For physical device
```

### 4. Build and Run

1. Connect an Android device or start an emulator
2. Click the "Run" button (green play icon)
3. Select your target device
4. Wait for the app to build and install

## Running the Application

### Backend Verification

1. Check if the server is running:
   ```bash
   curl http://localhost:5000/health
   ```

2. Test API endpoints:
   ```bash
   # Get categories
   curl http://localhost:5000/api/v1/categories
   
   # Get issues
   curl http://localhost:5000/api/v1/issues
   ```

### Android App Features

The Android app includes:
- **Home Page**: View all reported issues
- **Map Page**: Interactive map with issue locations
- **Report Form**: Submit new issues with images
- **Authentication**: Login/Register functionality
- **Nearby Issues**: Find issues within your area

## Testing the API

### Using Postman Collection

1. Import the provided Postman collection:
   - File: `CivicTrack_Postman_Collection.json`
   - Open Postman
   - Click "Import" and select the file

2. Set up environment variables:
   - `base_url`: `http://localhost:5000/api/v1`
   - `auth_token`: (will be set after login)

3. Test the endpoints:
   - Register a new user
   - Login to get JWT token
   - Create issues with images
   - Get nearby issues
   - Flag issues

### Manual API Testing

```bash
# Register a user
curl -X POST http://localhost:5000/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","email":"test@example.com","password":"password123"}'

# Login
curl -X POST http://localhost:5000/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"password123"}'

# Create an issue
curl -X POST http://localhost:5000/api/v1/issues \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -F "title=Test Issue" \
  -F "description=This is a test issue" \
  -F "category=64f8a1b2c3d4e5f67890123" \
  -F "lat=40.7128" \
  -F "lng=-74.0060"

# Get nearby issues
curl "http://localhost:5000/api/v1/issues/nearby?lat=40.7128&lng=-74.0060&radius=5000"
```

## Troubleshooting

### Backend Issues

**Port already in use:**
```bash
# Find process using port 5000
lsof -i :5000
# Kill the process
kill -9 <PID>
```

**MongoDB connection failed:**
- Check if MongoDB is running
- Verify connection string in `.env`
- Ensure network access for Atlas

**JWT errors:**
- Check JWT_SECRET in `.env`
- Ensure token is included in Authorization header

### Android Issues

**Build errors:**
- Clean and rebuild project
- Sync Gradle files
- Update Android Studio

**Network errors:**
- Check API base URL in NetworkModule.kt
- Ensure backend is running
- Check device/emulator network settings

**Permission errors:**
- Grant location permissions in app settings
- Enable camera permissions for image uploads

### Common Solutions

1. **Backend not accessible from Android:**
   - Use `10.0.2.2` for Android emulator
   - Use your computer's IP for physical device
   - Check firewall settings

2. **Image upload fails:**
   - Ensure `uploads/` directory exists
   - Check file size limits
   - Verify multipart form data

3. **Geolocation not working:**
   - Enable location services
   - Grant location permissions
   - Check GPS settings

## Development Workflow

### Backend Development
1. Make changes to controllers/models
2. Restart server (or use nodemon for auto-restart)
3. Test with Postman collection
4. Update API documentation

### Android Development
1. Make changes to UI/ViewModel
2. Build and run on device/emulator
3. Test API integration
4. Update UI based on backend responses

## Production Deployment

### Backend Deployment
1. Set `NODE_ENV=production`
2. Use MongoDB Atlas for database
3. Deploy to cloud platform (Heroku, AWS, etc.)
4. Set up environment variables
5. Configure domain and SSL

### Android Release
1. Generate signed APK
2. Test on multiple devices
3. Update API base URL for production
4. Publish to Google Play Store

## Support

For issues and questions:
1. Check the troubleshooting section
2. Review API documentation in README.md
3. Test with Postman collection
4. Check server logs for errors

## Next Steps

After successful setup:
1. Customize issue categories
2. Add more admin features
3. Implement push notifications
4. Add offline support
5. Enhance UI/UX
6. Add analytics and reporting 