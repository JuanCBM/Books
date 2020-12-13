const mongoose=require('mongoose');
const { Schema } = mongoose;


let validateEmail = function(email) {
  const re = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
  return re.test(email)
};

const userSchema = new Schema({
  nick: {
    type: String,
    required: true,
    unique: true,
    minLength: [5, 'Nick must be 5 characters or more.'],
  },
  email: {
    type: String,
    unique: true,
    required: [true,'Email address is required'],
    validate: [validateEmail, 'Please fill a valid email address'],
    match: [/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/, 'Please fill a valid email address like email@email.com']
  },
});

module.exports = mongoose.model('User', userSchema);

console.log('Executing Server: user.js ...');
