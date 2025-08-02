# CivicTrack Backend API

A Node.js/Express backend API for the CivicTrack Android application, built with MongoDB and JWT authentication.

## Features

- 🔐 JWT Authentication (optional for issue reporting)
- 📍 Geospatial queries for nearby issues
- 🖼️ Image upload support (Cloud Storage ready)
- 🚩 Issue flagging and moderation
- 📊 Admin analytics and user management
- 🏷️ Category management
- 📱 Optimized for Retrofit consumption
- ☁️ Cloud deployment ready (Vercel, Render.com)

## API Endpoints

### Authentication
- `POST /api/v1/auth/register` - Register new user
- `POST /api/v1/auth/login` - Login user
- `GET /api/v1/auth/profile` - Get user profile

### Issues
- `POST /api/v1/issues` - Create new issue
- `GET /api/v1/issues` - Get issues with filters
- `GET /api/v1/issues/nearby` - Get nearby issues
- `GET /api/v1/issues/:id` - Get specific issue
- `PATCH /api/v1/issues/:id/status` - Update issue status
- `POST /api/v1/issues/:id/flag` - Flag issue

### Categories
- `GET /api/v1/categories` - Get all categories

### Admin
- `GET /api/v1/admin/issues/flagged` - Get flagged issues
- `POST /api/v1/admin/ban-user` - Ban user
- `POST /api/v1/admin/unban-user` - Unban user
- `GET /api/v1/admin/analytics` - Get analytics
- `GET /api/v1/admin/users` - Get all users

## Installation

### Local Development

1. Clone the repository
2. Install dependencies:
   ```bash
   npm install
   ```

3. Create `.env` file from `env.example`:
   ```bash
   cp env.example .env
   ```

4. Update environment variables in `.env`

5. Start MongoDB locally or use MongoDB Atlas

6. Seed the database:
   ```bash
   npm run seed
   ```

7. Start the server:
   ```bash
   npm run dev
   ```

## Cloud Deployment

### Vercel Deployment

1. Install Vercel CLI:
   ```bash
   npm i -g vercel
   ```

2. Deploy to Vercel:
   ```bash
   vercel
   ```

3. Set environment variables in Vercel dashboard:
   - `MONGO_URI` - Your MongoDB Atlas connection string
   - `JWT_SECRET` - Your JWT secret key
   - `NODE_ENV` - Set to "production"

### Render.com Deployment

1. Connect your GitHub repository to Render
2. Create a new Web Service
3. Configure:
   - **Build Command**: `npm install`
   - **Start Command**: `npm start`
   - **Environment**: Node

4. Set environment variables:
   - `MONGO_URI` - Your MongoDB Atlas connection string
   - `JWT_SECRET` - Your JWT secret key
   - `NODE_ENV` - Set to "production"

### Environment Variables

```env
PORT=5000
NODE_ENV=production
MONGO_URI=mongodb+srv://username:password@cluster.mongodb.net/civictrack
JWT_SECRET=your_jwt_secret_key_here
```

## Database Schema

### User
- username, email, password
- role (user/admin)
- isBanned flag

### Issue
- title, description, category
- location (GeoJSON Point)
- status, statusLogs
- images, flags
- reportedBy (optional)

### Category
- name, description
- icon, color
- isActive flag

## API Response Format

All responses follow this format:
```json
{
  "success": true,
  "data": {...},
  "message": "Success message"
}
```

## Error Handling

Errors return:
```json
{
  "success": false,
  "message": "Error description",
  "stack": "..." // Only in development
}
```

## Authentication

- Public endpoints: No authentication required
- Optional auth: Works with or without JWT
- Protected endpoints: Valid JWT required
- Admin endpoints: Valid JWT + admin role required

## File Upload

- Supports multiple image uploads (max 3)
- Max file size: 5MB
- Supported formats: JPG, PNG, GIF
- Cloud storage ready (AWS S3, Cloudinary, etc.)

## Geospatial Queries

- Uses MongoDB's 2dsphere index
- Supports radius-based queries
- Coordinates format: [longitude, latitude]

## Development

```bash
# Start development server
npm run dev

# Seed database
npm run seed

# Start production server
npm start
```

## Default Admin Account

After seeding:
- Email: admin@civictrack.com
- Password: admin123

## API Documentation

For detailed API documentation, see the Postman collection or Swagger docs (if implemented).

## License

MIT License 