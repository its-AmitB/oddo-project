import mongoose from 'mongoose';

const statusLogSchema = new mongoose.Schema({
  status: {
    type: String,
    enum: ['Reported', 'In Progress', 'Resolved'],
    required: true
  },
  timestamp: {
    type: Date,
    default: Date.now
  },
  note: {
    type: String,
    default: ''
  }
});

const flagSchema = new mongoose.Schema({
  userId: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User'
  },
  reason: {
    type: String,
    required: true
  },
  timestamp: {
    type: Date,
    default: Date.now
  }
});

const issueSchema = new mongoose.Schema({
  title: {
    type: String,
    required: true,
    trim: true
  },
  description: {
    type: String,
    required: true
  },
  category: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'Category',
    required: true
  },
  location: {
    type: {
      type: String,
      enum: ['Point'],
      default: 'Point'
    },
    coordinates: {
      type: [Number],
      required: true
    }
  },
  address: {
    type: String,
    default: ''
  },
  images: [{
    type: String,
    default: []
  }],
  status: {
    type: String,
    enum: ['Reported', 'In Progress', 'Resolved'],
    default: 'Reported'
  },
  statusLogs: [statusLogSchema],
  reportedBy: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User'
  },
  anonymous: {
    type: Boolean,
    default: false
  },
  flags: [flagSchema],
  isFlagged: {
    type: Boolean,
    default: false
  },
  upvotes: {
    type: Number,
    default: 0
  },
  downvotes: {
    type: Number,
    default: 0
  },
  resolvedAt: {
    type: Date
  }
}, {
  timestamps: true
});

// Index for geospatial queries
issueSchema.index({ location: '2dsphere' });

// Index for status and category filtering
issueSchema.index({ status: 1, category: 1 });

// Index for date queries
issueSchema.index({ createdAt: -1 });

const Issue = mongoose.model('Issue', issueSchema);
export default Issue; 