const express = require('express')
const router = express.Router()
const Book = require('../schemas/book')
const Comment = require('../schemas/comment')
const mongoose = require('mongoose');
const BookService = require("../services/bookService");
const bookService = new BookService();

module.exports = router

// Obtiene sólo id y título
router.get('/', async (req,res)=> {
  try{
    const books = await bookService.getAllBooks()
    const response = toResponse(books)
    formatResponse(response, ["_id", "title"])
    res.json(response);
  }catch (error) {
    res.status(500).json({message: err.message})
  }
})

// Obtiene libro
router.get('/:id', async (req,res)=> {
  const id = req.params.id;

  if(!mongoose.Types.ObjectId.isValid(id)){
    return res.sendStatus(400);
  }

  const book = await bookService.getBookById(id)
  if (!book) {
    res.sendStatus(404);
  } else {
    const response = toResponse(book)
    res.json(response);
  }
})

// Crea un libro
router.post('/', async (req,res)=> {
  const book = new Book({
    title: req.body.title,
    resume: req.body.resume,
    author: req.body.author,
    publicationYear: req.body.publicationYear,
    editorial: req.body.editorial
  })
  try{
    const newBook = await bookService.addBook(book)
    res.status(201).json(newBook)
  }catch (e){
    res.status(400).json({message: e.message})
  }

})

// Borra un libro
router.delete('/:idBook', async (req,res)=> {
  const bookId = req.params.idBook;

  if(!mongoose.Types.ObjectId.isValid(bookId)){
    return res.sendStatus(400);
  }
  const book = await bookService.deleteBookById(bookId);
  if (!book) {
    res.sendStatus(404);
  } else {
    res.json(toResponse(book));
  }
})

// Actualiza un libro
router.put('/:idBook', async (req,res)=> {
  const bookId = req.params.idBook;
  if(!mongoose.Types.ObjectId.isValid(bookId)){
    return res.sendStatus(400);
  }

  const book = new Book({
    title: req.body.title,
    resume: req.body.resume,
    author: req.body.author,
    publicationYear: req.body.publicationYear,
    editorial: req.body.editorial
  })

  try{
    const newBook = await bookService.updateBookById(bookId, book)
    res.status(201).json(newBook)
  }catch (e){
    res.status(400).json({message: e.message})
  }

})

// Create comment
router.post('/:idBook/comments', async (req,res)=> {
  const comment = new Comment({
    rating: req.body.rating,
    content: req.body.content
  });

  comment.nick = req.body.nick

  const bookId = req.params.idBook;
  const book = await bookService.addComment(bookId,comment);
  if (!book) {
    res.sendStatus(404);
  } else {
    res.json(toResponse(book));
  }
})

// Delete comment
router.delete('/:idBook/comments/:idComment', async (req,res)=> {
  const bookId = req.params.idBook;
  const commentId = req.params.idComment;

  const book = await bookService.deleteComment(bookId,commentId);
  if (!book) {
    res.sendStatus(404);
  } else {
    res.json(toResponse(book));
  }
})

// Formateo de la respuesta de la peticion
function toResponse(document) {
  if (document instanceof Array) {
    return document.map(elem => toResponse(elem));
  } else {
    return document.toObject({versionKey: false});
  }
}

// Formateo la respuesta con las propiedades que me hacen falta
function formatResponse(document, properties){
  if (document instanceof Array) {
    return document.map(elem => formatResponse(elem, properties));
  } else {
    for (const key in document) {
      if(!properties.includes(key)){
        delete document[key];
      }
    }
    return document;
  }
}
