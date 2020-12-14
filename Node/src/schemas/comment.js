const mongoose=require('mongoose');

const { Schema } = mongoose;

// mongoose.Promise
const commentSchema = new Schema({
  content: { type: String, required: true },
  rating: { type:Number, min:0 , max:5},
  _user: { type: Schema.ObjectId, ref: 'User' }
});

/*const autoPopulateCreator = function(next) {
  this.populate({
    path: '_creator',
    select: 'username createdAt -_id'
  });
  next();
};*/

//commentSchema.pre('find', autoPopulateCreator);

module.exports = mongoose.model('Comment', commentSchema);
