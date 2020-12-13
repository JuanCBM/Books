const mongoose=require('mongoose');

const { Schema } = mongoose;

// mongoose.Promise = global.Promise; Inserted the bluebird module here to fix warnings described in app.js (now moved to alternative_db.js)
mongoose.Promise = require('bluebird');

const userSchema = new Schema({
  username: {
    type: String,
    required: 'UserName is required',
    unique: true,
    minLength: [5, 'Username must be 5 characters or more.'],
  },
  email: {
    type: String,
    trim: true,
    lowercase: true,
    unique: true,
    required: 'Email address is required',
    validate: [validateEmail, 'Please fill a valid email address'],
    match: [/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/, 'Please fill a valid email address']
  },
});


var validateEmail = function(email) {
  var re = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
  return re.test(email)
};

const User = mongoose.model('User', userSchema);

module.exports = {User:User}

console.log('Executing Server: user.js ...');
