import express from 'express';
import { 
  getFlaggedIssues, 
  banUser, 
  unbanUser, 
  getAnalytics, 
  getAllUsers 
} from '../controllers/adminController.js';
import { protect, admin } from '../middleware/authMiddleware.js';

const router = express.Router();

// All admin routes require authentication and admin role
router.use(protect, admin);

router.get('/issues/flagged', getFlaggedIssues);
router.post('/ban-user', banUser);
router.post('/unban-user', unbanUser);
router.get('/analytics', getAnalytics);
router.get('/users', getAllUsers);

export default router; 