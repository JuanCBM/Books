const MongoClient = require('mongodb').MongoClient;
const ObjectId = require('mongodb').ObjectID;

const uri = 'mongodb://localhost:27017/mydb';

let myDB, bookCollection, commentCollection;

const BOOK_COLLECTION = 'books';
const COMMENT_COLLECTION = 'comments';

const dbname='BooksDb';
const collectionName='books';

class BookRepository {
  constructor(){
    MongoClient.connect(
        uri,
        {
          useUnifiedTopology: true,
          useNewUrlParser: true,
        },
        async (err, db) => {
          if (err) throw err;
          console.log('Database created!');

          myDB = db.db('mydb');

          bookCollection = await myDB.createCollection(BOOK_COLLECTION);
          commentCollection = await myDB.createCollection(COMMENT_COLLECTION);
        },
    );
  }

  async getAllBooks() {
    return await bookCollection.find({}).toArray();
  }

  async addBook(book) {
    try {
      await bookCollection.insertOne(book);
      return book;
    } catch (error) {
      throw error;
    }
  }

  async getBookById(id) {
    const query = {_id: new ObjectId(id)};
    try {
      const book = await bookCollection.findOne(query);
      if (book != null) {
        return book;
      }
      throw {status: 404, message: 'Book not found'};
    } catch (error) {
      throw error;
    }
  }

  async deleteBookById(id) {
    try {
      const book = await this.getBookById(id);
      await bookCollection.deleteOne(book);
      return book;
    } catch (error) {
      throw error;
    }
  }

  async updateBookById(id, book) {
    try {
      const query = {_id: new ObjectId(id)};
      const newValues = {$set: book};
      await bookCollection.updateOne(query, newValues);
      return book;
    } catch (error) {
      throw error;
    }
  }

  async addComment(bookId, comment) {
    try {
      const book = await this.getBookById(bookId);
      comment._book = bookId;
      await commentCollection.insertOne(comment);
      const newValues = {$push: {_comments: comment}};
      await bookCollection.updateOne(book, newValues);
      return comment;
    } catch (error) {
      throw error;
    }
  }

  async deleteComment(bookId, commentId) {
    const newValues = {$pull: {_comments: {_id: new ObjectId(commentId)}}};
    const query = {_id: new ObjectId(commentId)};
    try {
      const book = await this.getBookById(bookId);
      const comment = await commentCollection.findOne(query);
      await commentCollection.deleteOne(comment);
      await bookCollection.updateOne(book, newValues);
      return null;
    } catch (error) {
      throw error;
    }
  }

}

module.exports = BookRepository;
