import User from '../models/User.js';
import Issue from '../models/Issue.js';
import Category from '../models/Category.js';

// @desc    Get flagged issues
// @route   GET /api/v1/admin/issues/flagged
// @access  Private/Admin
export const getFlaggedIssues = async (req, res) => {
  try {
    const issues = await Issue.find({ isFlagged: true })
      .populate('category')
      .populate('reportedBy', 'username email')
      .sort({ createdAt: -1 });
    
    res.json(issues);
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};

// @desc    Ban a user
// @route   POST /api/v1/admin/ban-user
// @access  Private/Admin
export const banUser = async (req, res) => {
  try {
    const { userId } = req.body;
    
    if (!userId) {
      return res.status(400).json({ message: 'User ID is required' });
    }
    
    const user = await User.findById(userId);
    
    if (!user) {
      return res.status(404).json({ message: 'User not found' });
    }
    
    if (user.role === 'admin') {
      return res.status(400).json({ message: 'Cannot ban admin users' });
    }
    
    user.isBanned = true;
    await user.save();
    
    res.json({ message: 'User banned successfully' });
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};

// @desc    Unban a user
// @route   POST /api/v1/admin/unban-user
// @access  Private/Admin
export const unbanUser = async (req, res) => {
  try {
    const { userId } = req.body;
    
    if (!userId) {
      return res.status(400).json({ message: 'User ID is required' });
    }
    
    const user = await User.findById(userId);
    
    if (!user) {
      return res.status(404).json({ message: 'User not found' });
    }
    
    user.isBanned = false;
    await user.save();
    
    res.json({ message: 'User unbanned successfully' });
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};

// @desc    Get analytics
// @route   GET /api/v1/admin/analytics
// @access  Private/Admin
export const getAnalytics = async (req, res) => {
  try {
    // Total issues
    const totalIssues = await Issue.countDocuments();
    
    // Issues by status
    const issuesByStatus = await Issue.aggregate([
      {
        $group: {
          _id: '$status',
          count: { $sum: 1 }
        }
      }
    ]);
    
    // Top categories
    const topCategories = await Issue.aggregate([
      {
        $lookup: {
          from: 'categories',
          localField: 'category',
          foreignField: '_id',
          as: 'categoryInfo'
        }
      },
      {
        $unwind: '$categoryInfo'
      },
      {
        $group: {
          _id: '$categoryInfo.name',
          count: { $sum: 1 }
        }
      },
      {
        $sort: { count: -1 }
      },
      {
        $limit: 10
      }
    ]);
    
    // Recent issues (last 7 days)
    const sevenDaysAgo = new Date();
    sevenDaysAgo.setDate(sevenDaysAgo.getDate() - 7);
    
    const recentIssues = await Issue.countDocuments({
      createdAt: { $gte: sevenDaysAgo }
    });
    
    // Active zones (areas with most issues)
    const activeZones = await Issue.aggregate([
      {
        $group: {
          _id: {
            lat: { $round: ['$location.coordinates.1', 2] },
            lng: { $round: ['$location.coordinates.0', 2] }
          },
          count: { $sum: 1 }
        }
      },
      {
        $sort: { count: -1 }
      },
      {
        $limit: 5
      }
    ]);
    
    res.json({
      totalIssues,
      issuesByStatus,
      topCategories,
      recentIssues,
      activeZones
    });
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};

// @desc    Get all users (admin only)
// @route   GET /api/v1/admin/users
// @access  Private/Admin
export const getAllUsers = async (req, res) => {
  try {
    const users = await User.find({}).select('-password').sort({ createdAt: -1 });
    res.json(users);
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
}; 