const mongoose=require('mongoose');

const { Schema } = mongoose;

// mongoose.Promise = global.Promise; Inserted the bluebird module here to fix warnings described in app.js
mongoose.Promise = require('bluebird');

const bookSchema = new Schema({
  title: { type: String, required: true },
  resume: String,
  editorial: String,
  publicationYear: Number,
  _creator: { type: Schema.ObjectId, ref: 'User' },
  _comments: [{ type: Schema.ObjectId, ref: 'Comment' }],
});

const Book = mongoose.model('Book', bookSchema);

module.exports = {Book:Book}

console.log('Executing Server: post.js ...');
