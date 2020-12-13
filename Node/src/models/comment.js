import mongoose from 'mongoose';

const { Schema } = mongoose;

// mongoose.Promise = global.Promise; Inserted the bluebird module here to fix warnings described in app.js
mongoose.Promise = require('bluebird');

const commentSchema = new Schema({
  text: { type: String, required: true },
  _creator: { type: Schema.ObjectId, ref: 'User' },
  _book: { type: Schema.ObjectId, ref: 'Book'}
});

const autoPopulateCreator = function(next) {
  this.populate({
    path: '_creator',
    select: 'username createdAt -_id'
  });
  next();
};

commentSchema.pre('find', autoPopulateCreator);

const Comment = mongoose.model('Comment', commentSchema);

module.exports = {Comment:Comment}

console.log('Executing Server: comment.js ...');
