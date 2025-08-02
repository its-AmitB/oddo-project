# CivicTrack Cloud Deployment Guide

This guide will help you deploy the CivicTrack backend to Vercel, Render.com, or other cloud platforms.

## Prerequisites

1. **MongoDB Atlas Account** (Free tier available)
2. **GitHub Repository** with your code
3. **Cloud Platform Account** (Vercel/Render.com)

## Step 1: Set Up MongoDB Atlas

1. Go to [MongoDB Atlas](https://www.mongodb.com/atlas)
2. Create a free account
3. Create a new cluster (M0 Free tier)
4. Set up database access:
   - Create a database user
   - Set username and password
5. Set up network access:
   - Allow access from anywhere (0.0.0.0/0)
6. Get your connection string:
   ```
   mongodb+srv://username:password@cluster.mongodb.net/civictrack
   ```

## Step 2: Prepare Your Code

### Required Files (Already Created)

✅ `vercel.json` - Vercel configuration
✅ `Dockerfile` - Render.com configuration  
✅ `package.json` - Updated with cloud dependencies
✅ `utils/cloudStorage.js` - Cloud storage utilities
✅ Updated controllers for cloud deployment

### Environment Variables

Create these environment variables in your cloud platform:

```env
NODE_ENV=production
MONGO_URI=mongodb+srv://username:password@cluster.mongodb.net/civictrack
JWT_SECRET=your_very_long_and_secure_jwt_secret_key_here
PORT=5000
```

## Step 3: Deploy to Vercel

### Option A: Using Vercel CLI

1. Install Vercel CLI:
   ```bash
   npm i -g vercel
   ```

2. Login to Vercel:
   ```bash
   vercel login
   ```

3. Deploy:
   ```bash
   vercel
   ```

4. Set environment variables in Vercel dashboard:
   - Go to your project settings
   - Add environment variables
   - Set `MONGO_URI`, `JWT_SECRET`, `NODE_ENV`

### Option B: Using Vercel Dashboard

1. Go to [vercel.com](https://vercel.com)
2. Import your GitHub repository
3. Configure:
   - **Framework Preset**: Node.js
   - **Build Command**: `npm install`
   - **Output Directory**: (leave empty)
   - **Install Command**: `npm install`
4. Add environment variables
5. Deploy

## Step 4: Deploy to Render.com

1. Go to [render.com](https://render.com)
2. Connect your GitHub account
3. Create a new Web Service
4. Configure:
   - **Name**: civictrack-backend
   - **Environment**: Node
   - **Build Command**: `npm install`
   - **Start Command**: `npm start`
   - **Plan**: Free
5. Add environment variables:
   - `MONGO_URI`
   - `JWT_SECRET`
   - `NODE_ENV=production`
6. Deploy

## Step 5: Test Your Deployment

### Health Check
```bash
curl https://your-app.vercel.app/health
```

### Test API Endpoints
```bash
# Get categories
curl https://your-app.vercel.app/api/v1/categories

# Get issues
curl https://your-app.vercel.app/api/v1/issues
```

## Step 6: Update Android App

Update the API base URL in your Android app:

```kotlin
// In NetworkModule.kt
private const val BASE_URL = "https://your-app.vercel.app/api/v1/"
// or
private const val BASE_URL = "https://your-app.onrender.com/api/v1/"
```

## Common Issues & Solutions

### Issue 1: MongoDB Connection Failed
**Error**: `MongoServerSelectionError`
**Solution**: 
- Check MongoDB Atlas network access (allow 0.0.0.0/0)
- Verify connection string format
- Ensure database user has correct permissions

### Issue 2: File Upload Not Working
**Error**: `ENOENT: no such file or directory 'uploads'`
**Solution**: 
- The app now uses cloud storage
- Files are processed in memory
- No local file system required

### Issue 3: CORS Errors
**Error**: `Access to fetch at '...' from origin '...' has been blocked`
**Solution**:
- CORS is already configured in the app
- Check if your domain is allowed in CORS settings

### Issue 4: Environment Variables Not Set
**Error**: `process.env.MONGO_URI is undefined`
**Solution**:
- Double-check environment variable names
- Ensure variables are set in your cloud platform dashboard
- Redeploy after setting variables

## Production Checklist

- [ ] MongoDB Atlas cluster created and configured
- [ ] Environment variables set in cloud platform
- [ ] API endpoints tested and working
- [ ] Android app updated with new API URL
- [ ] File upload functionality tested
- [ ] Authentication working properly
- [ ] Database seeded with initial data

## Monitoring & Maintenance

### Vercel
- Use Vercel Analytics
- Monitor function execution times
- Check deployment logs

### Render.com
- Monitor service logs
- Check resource usage
- Set up alerts for downtime

### MongoDB Atlas
- Monitor database performance
- Set up alerts for connection issues
- Regular backups (automatic with Atlas)

## Cost Optimization

### Vercel
- Free tier: 100GB bandwidth/month
- Serverless functions: 100GB-hours/month
- Upgrade only when needed

### Render.com
- Free tier: 750 hours/month
- Auto-sleep after 15 minutes of inactivity
- Upgrade for always-on service

### MongoDB Atlas
- Free tier: 512MB storage
- 500 connections
- Shared clusters

## Security Considerations

1. **JWT Secret**: Use a long, random string
2. **MongoDB**: Use strong passwords
3. **CORS**: Configure allowed origins properly
4. **Rate Limiting**: Consider adding rate limiting
5. **HTTPS**: Always use HTTPS in production

## Next Steps

After successful deployment:

1. **Set up monitoring** and alerts
2. **Configure custom domain** (optional)
3. **Set up CI/CD** for automatic deployments
4. **Add rate limiting** for API protection
5. **Implement logging** for better debugging
6. **Set up backups** for MongoDB data

## Support

If you encounter issues:

1. Check the deployment logs
2. Verify environment variables
3. Test API endpoints manually
4. Check MongoDB Atlas status
5. Review the troubleshooting section above 