const mongoose=require('mongoose')
const { Schema } = mongoose;

const bookSchema = new Schema({
  title: { type: String, required: true },
  resume: String,
  editorial: String,
  publicationYear: Number,
  author: String,
  _comments: [{ type: Schema.ObjectId, ref: 'Comment' }],
});

module.exports = mongoose.model('Book', bookSchema);
