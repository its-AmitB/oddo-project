import Issue from '../models/Issue.js';
import { cloudUpload, processUploadedFile } from '../utils/cloudStorage.js';

// Create new issue
export const createIssue = async (req, res) => {
  try {
    const { title, description, category, lat, lng, address, anonymous } = req.body;
    
    // Handle file uploads for cloud deployment
    let imageUrls = [];
    if (req.files && req.files.length > 0) {
      imageUrls = req.files.map(file => {
        const processedFile = processUploadedFile(file);
        return processedFile.url;
      });
    }

    const issue = new Issue({
      title,
      description,
      category,
      location: {
        type: 'Point',
        coordinates: [parseFloat(lng), parseFloat(lat)]
      },
      address: address || '',
      images: imageUrls,
      anonymous: anonymous === 'true',
      reportedBy: req.user ? req.user._id : null
    });

    const savedIssue = await issue.save();
    res.status(201).json({
      success: true,
      data: savedIssue,
      message: 'Issue created successfully'
    });
  } catch (error) {
    res.status(500).json({ 
      success: false,
      message: error.message 
    });
  }
};

// Get all issues with filters
export const getIssues = async (req, res) => {
  try {
    const { status, category, lat, lng, radius = 5000, page = 1, limit = 20 } = req.query;
    
    let query = {};
    
    if (status) query.status = status;
    if (category) query.category = category;
    
    // Add geospatial query if coordinates provided
    if (lat && lng) {
      query.location = {
        $near: {
          $geometry: {
            type: 'Point',
            coordinates: [parseFloat(lng), parseFloat(lat)]
          },
          $maxDistance: parseInt(radius)
        }
      };
    }
    
    const skip = (parseInt(page) - 1) * parseInt(limit);
    
    const issues = await Issue.find(query)
      .populate('category', 'name description')
      .populate('reportedBy', 'username email')
      .sort({ createdAt: -1 })
      .skip(skip)
      .limit(parseInt(limit));
    
    const total = await Issue.countDocuments(query);
    
    res.json({
      success: true,
      data: {
        issues,
        pagination: {
          page: parseInt(page),
          limit: parseInt(limit),
          total,
          pages: Math.ceil(total / parseInt(limit))
        }
      }
    });
  } catch (error) {
    res.status(500).json({ 
      success: false,
      message: error.message 
    });
  }
};

// Get nearby issues
export const getNearbyIssues = async (req, res) => {
  try {
    const { lat, lng, radius = 5000 } = req.query;
    
    if (!lat || !lng) {
      return res.status(400).json({
        success: false,
        message: 'Latitude and longitude are required'
      });
    }
    
    const issues = await Issue.find({
      location: {
        $near: {
          $geometry: {
            type: 'Point',
            coordinates: [parseFloat(lng), parseFloat(lat)]
          },
          $maxDistance: parseInt(radius)
        }
      }
    })
    .populate('category', 'name description')
    .populate('reportedBy', 'username email')
    .sort({ createdAt: -1 });
    
    res.json({
      success: true,
      data: issues
    });
  } catch (error) {
    res.status(500).json({ 
      success: false,
      message: error.message 
    });
  }
};

// Get single issue
export const getIssueById = async (req, res) => {
  try {
    const issue = await Issue.findById(req.params.id)
      .populate('category', 'name description')
      .populate('reportedBy', 'username email');
    
    if (!issue) {
      return res.status(404).json({
        success: false,
        message: 'Issue not found'
      });
    }
    
    res.json({
      success: true,
      data: issue
    });
  } catch (error) {
    res.status(500).json({ 
      success: false,
      message: error.message 
    });
  }
};

// Update issue status
export const updateIssueStatus = async (req, res) => {
  try {
    const { status, note } = req.body;
    
    const issue = await Issue.findById(req.params.id);
    if (!issue) {
      return res.status(404).json({
        success: false,
        message: 'Issue not found'
      });
    }
    
    // Add status log
    issue.statusLogs.push({
      status,
      timestamp: new Date().toISOString(),
      note: note || ''
    });
    
    issue.status = status;
    if (status === 'Resolved') {
      issue.resolvedAt = new Date().toISOString();
    }
    
    const updatedIssue = await issue.save();
    
    res.json({
      success: true,
      data: updatedIssue,
      message: 'Issue status updated successfully'
    });
  } catch (error) {
    res.status(500).json({ 
      success: false,
      message: error.message 
    });
  }
};

// Flag an issue
export const flagIssue = async (req, res) => {
  try {
    const { reason } = req.body;
    
    const issue = await Issue.findById(req.params.id);
    if (!issue) {
      return res.status(404).json({
        success: false,
        message: 'Issue not found'
      });
    }
    
    issue.flags.push({
      userId: req.user ? req.user._id : null,
      reason,
      timestamp: new Date().toISOString()
    });
    
    issue.isFlagged = true;
    await issue.save();
    
    res.json({
      success: true,
      message: 'Issue flagged successfully'
    });
  } catch (error) {
    res.status(500).json({ 
      success: false,
      message: error.message 
    });
  }
}; 