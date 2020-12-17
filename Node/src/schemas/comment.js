const mongoose=require('mongoose');

const { Schema } = mongoose;

// mongoose.Promise
const commentSchema = new Schema({
  content: { type: String, required: true },
  rating: { type:Number, min:0 , max:5},
  _user: { type: Schema.ObjectId, ref: 'User' }
});

module.exports = mongoose.model('Comment', commentSchema);
