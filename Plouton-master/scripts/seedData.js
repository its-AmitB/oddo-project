import mongoose from 'mongoose';
import dotenv from 'dotenv';
import Category from '../models/Category.js';
import User from '../models/User.js';

dotenv.config();

const connectDB = async () => {
  try {
    const conn = await mongoose.connect(process.env.MONGO_URI || 'mongodb://localhost:27017/civictrack');
    console.log(`MongoDB Connected: ${conn.connection.host}`);
  } catch (error) {
    console.error(`Error: ${error.message}`);
    process.exit(1);
  }
};

const seedCategories = async () => {
  const categories = [
    {
      name: 'Road Issues',
      description: 'Potholes, broken roads, street lights, traffic signals',
      icon: 'road',
      color: '#FF6B6B'
    },
    {
      name: 'Water & Sanitation',
      description: 'Water supply, drainage, sewage, garbage collection',
      icon: 'water',
      color: '#4ECDC4'
    },
    {
      name: 'Public Transport',
      description: 'Bus stops, metro stations, transport infrastructure',
      icon: 'bus',
      color: '#45B7D1'
    },
    {
      name: 'Parks & Recreation',
      description: 'Parks, playgrounds, sports facilities, public spaces',
      icon: 'park',
      color: '#96CEB4'
    },
    {
      name: 'Safety & Security',
      description: 'Street lighting, police presence, emergency services',
      icon: 'security',
      color: '#FFEAA7'
    },
    {
      name: 'Healthcare',
      description: 'Hospitals, clinics, medical facilities',
      icon: 'health',
      color: '#DDA0DD'
    },
    {
      name: 'Education',
      description: 'Schools, colleges, educational infrastructure',
      icon: 'education',
      color: '#98D8C8'
    },
    {
      name: 'Environment',
      description: 'Air quality, pollution, green spaces, wildlife',
      icon: 'environment',
      color: '#A8E6CF'
    }
  ];

  try {
    await Category.deleteMany({});
    await Category.insertMany(categories);
    console.log('Categories seeded successfully');
  } catch (error) {
    console.error('Error seeding categories:', error);
  }
};

const seedAdminUser = async () => {
  try {
    const adminExists = await User.findOne({ email: 'admin@civictrack.com' });
    
    if (!adminExists) {
      await User.create({
        username: 'admin',
        email: 'admin@civictrack.com',
        password: 'admin123',
        role: 'admin'
      });
      console.log('Admin user created successfully');
    } else {
      console.log('Admin user already exists');
    }
  } catch (error) {
    console.error('Error seeding admin user:', error);
  }
};

const seedData = async () => {
  await connectDB();
  await seedCategories();
  await seedAdminUser();
  
  console.log('Database seeded successfully');
  process.exit(0);
};

seedData(); 