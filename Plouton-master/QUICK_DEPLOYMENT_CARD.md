# 🚀 Quick Deployment Reference Card

## Essential Steps (Do These in Order)

### 1. MongoDB Atlas Setup (5 minutes)
- [ ] Sign up at mongodb.com/atlas
- [ ] Create M0 free cluster
- [ ] Create database user with password
- [ ] Allow access from anywhere (0.0.0.0/0)
- [ ] Copy connection string

### 2. GitHub Upload (3 minutes)
- [ ] Create new GitHub repository
- [ ] Upload your project files
- [ ] Make repository public

### 3. Render.com Deployment (5 minutes)
- [ ] Sign up at render.com with GitHub
- [ ] Create new Web Service
- [ ] Connect your repository
- [ ] Set environment variables (see table below)
- [ ] Deploy

### 4. Test & Update (2 minutes)
- [ ] Test health endpoint
- [ ] Update Android app BASE_URL
- [ ] Test Android app

---

## Environment Variables for Render.com

| Variable | Value |
|----------|-------|
| `PORT` | `10000` |
| `NODE_ENV` | `production` |
| `MONGO_URI` | `mongodb+srv://username:password@cluster.mongodb.net/civictrack` |
| `JWT_SECRET` | `your-long-secret-key-here` |
| `UPLOAD_PATH` | `uploads/` |
| `MAX_FILE_SIZE` | `5242880` |

---

## Quick Test Commands

```bash
# Test your deployed API
curl https://your-app.onrender.com/health

# Expected response:
# {"status":"OK","message":"CivicTrack API is running"}
```

---

## Android App Update

Find this file: `app/src/main/java/com/plouton/CiviLized/di/NetworkModule.kt`

Change this line:
```kotlin
private const val BASE_URL = "https://your-app-name.onrender.com/api/v1/"
```

---

## ⚠️ Common Issues

1. **Build fails**: Check `package.json` has all dependencies
2. **Database error**: Verify MongoDB connection string
3. **404 errors**: Check API routes are correct
4. **CORS errors**: Server already has CORS enabled

---

## 🎯 Success Indicators

- ✅ Health endpoint returns OK
- ✅ User registration works
- ✅ Android app connects to API
- ✅ All features work as expected

**Your API will be live at: `https://your-app-name.onrender.com`** 