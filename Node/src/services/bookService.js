const ObjectId = require('mongodb').ObjectID;
const Book = require('../schemas/book')
const Comment = require('../schemas/comment')

class BookService {
  constructor(){}

  async getAllBooks() {
    try {
      const books = await Book.find()
      return books
    } catch (error) {
      throw error
    }
  }

  async getBookById(id) {
    const query = {_id: new ObjectId(id)};
    try {
      const book = await Book.find(query).populate("_comments");
      if (book != null) {
        return book
      }
      throw {status: 404, message: 'Book not found'};
    } catch (error) {
      throw error
    }
  }

  async addBook(book) {
    try {
      await Book.create(book);
      return book;
    } catch (error) {
      throw error;
    }
  }

  async deleteBookById(id) {
    try {
      const query = {_id: new ObjectId(id),};
      const book = await Book.findOneAndRemove(query);
      deleteAssociatedComments(book);
      return book;
    } catch (error) {
      throw error;
    }
  }

  async updateBookById(id, book) {
    try {
      const persistedBook = await Book.findById(id);
      if (!persistedBook) {
        res.sendStatus(404);
      } else {
        persistedBook.title = book.title;
        persistedBook.author = persistedBook.author;
        persistedBook.resume = persistedBook.resume;
        persistedBook.editorial = persistedBook.editorial;
        persistedBook.publicationYear = persistedBook.publicationYear;
      }

      await persistedBook.save();

      return persistedBook;
    } catch (error) {
      throw error;
    }
  }



//TODO BUSCAR AL USUSARIO Y VERIFICAR QUE ESTA EN LA BBDD
  async addComment(bookId, comment) {
    try {
      const commentToUpdate = await Comment.create(comment);
      //const user = await userService.getUserByNick(comment.nick);

      //const queryComment = {$set: {_creator: {_id: new ObjectId(user._id)}}};
      //comment = await Comment.findOneAndUpdate(queryComment,comment);

      const newValues = {$push: {_comments: commentToUpdate}};

      const query = {_id: new ObjectId(bookId)};
      await Book.findOneAndUpdate(query, newValues);
      return comment;
    } catch (error) {
      throw error;
    }
  }

  async deleteComment(bookId, commentId) {
    const newValues = {$pull: {_comments: {_id: new ObjectId(commentId)}}};
    const query = {_id: new ObjectId(bookId)};
    const queryComment = {_id: new ObjectId(bookId)};
    try {
      await Comment.deleteOne(queryComment);
      return await Book.findOneAndUpdate(query, newValues);
    } catch (error) {
      throw error;
    }
  }

  async getBookByCommentId(commentId){
    const query = {_comments:{$elemMatch:{_id:new ObjectId(commentId)}}};
    return Book.findOne(query);
  }

}

// Borrar los comentarios asociados al Libro
function deleteAssociatedComments(document) {
  if (document instanceof Array) {
    return document.map(elem => deleteAssociatedComments(elem));
  } else {
    let response = document.toObject({ versionKey: false });
    response._comments.forEach(commentId=>{
      const query = {_id: new ObjectId(commentId),};
      Comment.findOneAndDelete(query);
    });
    return response;
  }
}

module.exports = BookService;
