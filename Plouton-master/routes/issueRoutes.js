import express from 'express';
import { cloudUpload } from '../utils/cloudStorage.js';
import {
  createIssue,
  getIssues,
  getNearbyIssues,
  getIssueById,
  updateIssueStatus,
  flagIssue
} from '../controllers/issueController.js';
import { protect } from '../middleware/authMiddleware.js';

const router = express.Router();

// Public routes
router.get('/', getIssues);
router.get('/nearby', getNearbyIssues);
router.get('/:id', getIssueById);
router.post('/:id/flag', flagIssue);

// Protected routes
router.post('/', cloudUpload.array('images', 3), createIssue);
router.patch('/:id/status', protect, updateIssueStatus);

export default router; 