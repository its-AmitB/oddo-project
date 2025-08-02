import multer from 'multer';
import path from 'path';

// Cloud storage configuration for serverless environments
const cloudStorage = multer.memoryStorage();

const fileFilter = (req, file, cb) => {
  if (file.mimetype.startsWith('image/')) {
    cb(null, true);
  } else {
    cb(new Error('Not an image! Please upload an image.'), false);
  }
};

const cloudUpload = multer({
  storage: cloudStorage,
  fileFilter: fileFilter,
  limits: {
    fileSize: 5 * 1024 * 1024 // 5MB limit
  }
});

// For cloud deployment, we'll store file URLs instead of local files
const processUploadedFile = (file) => {
  // In a real implementation, you would upload to:
  // - AWS S3
  // - Google Cloud Storage
  // - Cloudinary
  // - Firebase Storage
  
  // For now, return a placeholder URL
  return {
    filename: file.originalname,
    mimetype: file.mimetype,
    size: file.size,
    url: `https://placeholder.com/uploads/${file.originalname}` // Replace with actual cloud storage URL
  };
};

export { cloudUpload, processUploadedFile }; 