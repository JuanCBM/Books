const mongoose =require('mongoose');
const express = require('express');
const app = express();

const uri = 'mongodb://localhost:27017';

mongoose.connect(uri,
    { useUnifiedTopology: true,
             useNewUrlParser: true,
             useFindAndModify: false })
    .then(console.log("Connected to database"))
    .catch((error) => console.error(error));

app.use(express.json());

// BooksRouter
const bookRouter= require('./routes/bookRouter');
const userRouter= require('./routes/userRouter');
app.use('/api/v1/books',bookRouter);
app.use('/api/v1/users',userRouter);

app.listen(3000,()=> console.log('Server started: app.js ...'));

