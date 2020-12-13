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
const bookRouter= require('./routes/books');
app.use('/books',bookRouter)

// TODO: User router

app.listen(3000,()=> console.log('Server started: app.js ...'));

