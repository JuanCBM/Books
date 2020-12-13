const mongoose =require('mongoose');
const bookApiController= require('./routes/bookApiController');
const bodyParser = require('body-parser');
const express = require('express');

const uri = 'mongodb://localhost:27017';

/*mongoose.connect(uri, options, () => {
  console.log('Connected to MongoDB...');
  console.log('');
});*/


mongoose
.connect(uri)
.then(_ => {
  app.listen(process.env.PORT || 3000);
})
.catch(err => {
  console.log(err);
});

const app = express();

// Middelware
app.use(bodyParser.json());
app.use('/', bookApiController);

//export default app;
module.exports = {app:app}

console.log('Executing Server: app.js ...');
