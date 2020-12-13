const BookService  = require("../services/bookService");
const Book =require('../models/book');
const BookRepository=require('../repositories/bookRepository');


let bookRepository=new BookRepository();

const bookService = new BookService(bookRepository);

async function  getBookList(request,response){
  try{ let books=await bookService.getAll();
    await response.send(books);}catch(e){
    response.status(400);
    await response.send(e.message);
    console.log(e.message);
  }

}

async function addBook(request,response){
  const book = request.body;
  try{
    await bookService.add(book);
    response.status(201); //created
    await response.send(book);
  }catch(e){
    response.status(400);
    await response.send(e.message);
    console.log(e.message);
  }
}

async function getBookById(request,response){

  let bookId= request.params.bookId;
  console.log(bookId);
  let book=await bookService.getById(bookId);
  if(book)
    await response.send(book);
  else{
    response.status(404); //not found
    await response.send({error: `Book not found`, bookId:bookId});
  }
}

async function removeBook(request,response){
  let id=request.params.bookId;
  await bookService.removeBook(id);
  response.status(204); //no content
  await response.send({});
}

async function updateBook(request,response){
  let id=request.params.bookId;
  const book = request.body;
  let checkbook=await bookRepository.getBookById(id);
  if(checkbook)
    try{
      await bookRepository.updateBookById(id,book);
      response.status(201); //created
      await response.send(book);
    }catch(e){
      response.status(400);
      await response.send(e.message);
      console.log(e.message);
    }
  else{
    response.status(404); //not found
    await response.send({error: `Book not found`, id:id});
  }
}

async function addComment(request,response) {
  let bookId= request.params.bookId;
  const comment = request.body;
  let checkbook=await bookRepository.getBookById(bookId);
  if(checkbook)
    try {
      await bookRepository.addComment(bookId, comment);
      response.status(201); //created
      await response.send(comment);
    }catch(e){
      response.status(400);
      await response.send(e.message);
      console.log(e.message);
    }
  else{
    response.status(404); //not found
    await response.send({error: `Book not found`, id:id});
  }
}


async function deleteComment(request,response){
  let bookId= request.params.bookId;
  let commentId= request.params.commentId;
  let checkbook=await bookRepository.getBookById(bookId);
  if(checkbook) {
    await bookRepository.deleteComment(bookId, commentId);
    response.status(204); //no content
    await response.send({});
  }else{
    response.status(404); //not found
    await response.send({error: `Book not found`, id:id});
  }
}

const express = require('express');
const router = express.Router();

router.get('/books', getBookList);
router.post('/books', addBook);
router.put('/books/:bookId', updateBook);
router.get('/books/:bookId', getBookById);
router.delete('/books/:bookId', removeBook);
router.delete('/books/:bookId/comments/:commentId', deleteComment);
router.post('/books/:bookId/comments', addComment);




module.exports = router;
