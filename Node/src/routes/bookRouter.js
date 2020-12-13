const express = require('express')
const router = express.Router()
const Book = require('../schemas/book')
const Comment = require('../schemas/comment')
const mongoose = require('mongoose');
const BookService = require("../services/bookService");
const bookService = new BookService();

module.exports = router

// TODO: debe traer sólo id y titulo
router.get('/', async (req,res)=> {
  try{
    const books = await Book.find();
    res.json(toResponse(books));
  }catch (error) {
    res.status(500).json({message: err.message})
  }
})

// TODO: debe traer comentarios y dentro del comentario el nick y mail del usuario.
router.get('/:id', async (req,res)=> {
  const id = req.params.id;

  if(!mongoose.Types.ObjectId.isValid(id)){
    return res.sendStatus(400);
  }

  const book = await Book.findById(id);
  if (!book) {
    res.sendStatus(404);
  } else {
    res.json(book);
  }
})

// TODO: añadir el resto de info del libro al crear y el nick del usuario
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

// Delete book
router.delete('/:bookId', async (req,res)=> {
  const bookId = req.params.id;

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


router.post('/:id/comments', async (req,res)=> {
  const comment = new Comment({
    rating: req.body.rating,
    content: req.body.content
    //nick: req.body.nick
  });
  const bookId = req.params.id;
  const book = await bookService.addComment(bookId,comment);
  if (!book) {
    res.sendStatus(404);
  } else {
    res.json(toResponse(book));
  }
})

// TODO: Delete comment book
router.delete('/:idBook/comment/:idComment', async (req,res)=> {

})

// Formateo de la respuesta de la peticion
function toResponse(document) {
  if (document instanceof Array) {
    return document.map(elem => toResponse(elem));
  } else {
    let response = document.toObject({ versionKey: false });
    delete response._id;
    return response;
  }
}



// let bookRepository=new BookRepository();

// const bookService = new BookService(bookRepository);


// Se pude hacer con funciones o directamente dentro de la petición.
// async function  getBookList(request,response){
//   try{ let books=await bookService.getAll();
//     await response.send(books);}catch(e){
//     response.status(400);
//     await response.send(e.message);
//     console.log(e.message);
//   }
//
// }

//
// async function addBook(request,response){
//   const book = request.body;
//   try{
//     await bookService.add(book);
//     response.status(201); //created
//     await response.send(book);
//   }catch(e){
//     response.status(400);
//     await response.send(e.message);
//     console.log(e.message);
//   }
// }
//
// async function getBookById(request,response){
//
//   let bookId= request.params.bookId;
//   console.log(bookId);
//   let book=await bookService.getById(bookId);
//   if(book)
//     await response.send(book);
//   else{
//     response.status(404); //not found
//     await response.send({error: `Book not found`, bookId:bookId});
//   }
// }
//
// async function removeBook(request,response){
//   let id=request.params.bookId;
//   await bookService.removeBook(id);
//   response.status(204); //no content
//   await response.send({});
// }
//
// async function updateBook(request,response){
//   let id=request.params.bookId;
//   const book = request.body;
//   let checkbook=await bookRepository.getBookById(id);
//   if(checkbook)
//     try{
//       await bookRepository.updateBookById(id,book);
//       response.status(201); //created
//       await response.send(book);
//     }catch(e){
//       response.status(400);
//       await response.send(e.message);
//       console.log(e.message);
//     }
//   else{
//     response.status(404); //not found
//     await response.send({error: `Book not found`, id:id});
//   }
// }
//
// async function addComment(request,response) {
//   let bookId= request.params.bookId;
//   const comment = request.body;
//   let checkbook=await bookRepository.getBookById(bookId);
//   if(checkbook)
//     try {
//       await bookRepository.addComment(bookId, comment);
//       response.status(201); //created
//       await response.send(comment);
//     }catch(e){
//       response.status(400);
//       await response.send(e.message);
//       console.log(e.message);
//     }
//   else{
//     response.status(404); //not found
//     await response.send({error: `Book not found`, id:id});
//   }
// }
//
//
// async function deleteComment(request,response){
//   let bookId= request.params.bookId;
//   let commentId= request.params.commentId;
//   let checkbook=await bookRepository.getBookById(bookId);
//   if(checkbook) {
//     await bookRepository.deleteComment(bookId, commentId);
//     response.status(204); //no content
//     await response.send({});
//   }else{
//     response.status(404); //not found
//     await response.send({error: `Book not found`, id:id});
//   }
// }
//
// const express = require('express');
// const router = express.Router();
//
// router.get('/books', getBookList);
// router.post('/books', addBook);
// router.put('/books/:bookId', updateBook);
// router.get('/books/:bookId', getBookById);
// router.delete('/books/:bookId', removeBook);
// router.delete('/books/:bookId/comments/:commentId', deleteComment);
// router.post('/books/:bookId/comments', addComment);
//
//
//
//
// module.exports = router;
