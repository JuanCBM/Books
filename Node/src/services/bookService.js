const ObjectId = require('mongodb').ObjectID;
const Book = require('../schemas/book')
const Comment = require('../schemas/comment')
const UserService = require("../services/userService");
const userService = new UserService();
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
      const book = await Book.find(query).populate("_comments").populate("_user");
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

  async addComment(bookId, comment) {
    try {
      const user = await userService.getUserByNick(comment.nick);
      comment._user = user;

      const commentToUpdate = await Comment.create(comment);

      const newValues = {$push: {_comments: commentToUpdate}};
      const query = {_id: new ObjectId(bookId)};
      await Book.findOneAndUpdate(query, newValues);
      return this.getBookById(bookId)
    } catch (error) {
      throw error;
    }
  }

  async deleteComment(bookId, commentId) {
    const newValues = {$pull: {_comments: {_id: new ObjectId(commentId)}}};
    const query = {_id: new ObjectId(bookId)};
    const queryComment = {_id: new ObjectId(commentId)};
    try {
      await Comment.deleteOne(queryComment);
      await Book.findOneAndUpdate(query, newValues);
      return this.getBookById(bookId)
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
