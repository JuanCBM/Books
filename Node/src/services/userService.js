const ObjectId = require('mongodb').ObjectID;
const User = require('../schemas/user');
const Comment = require('../schemas/comment');
/*const BookService = require("../services/bookService");
const bookService = new BookService();*/

class UserService {
  constructor(){
  }

  async getAllUsers() {
    return User.find();
  }

  async addUser(user) {
    try {
      return await User.create(user);
    } catch (error) {
      throw error;
    }
  }

  async getUserById(userId) {
    const query = {_id: new ObjectId(userId)};
    try {
      const user = await User.findOne(query);
      if (user != null) {
        return user;
      }
      throw {status: 404, message: 'User not found'};
    } catch (error) {
      throw error;
    }
  }

  async getUserByNick(nick) {
    const query = {nick: nick};
    try {
      const user = await User.findOne(query);
      if (user != null) {
        return user;
      }
      throw {status: 404, message: 'User not found'};
    } catch (error) {
      throw error;
    }
  }

  async deleteUserById(userId) {
    try {
      const comments = await Comment.findOne({_user:new ObjectId(userId)});
      const query = {_id: new ObjectId(userId)};
      if(!comments){
        return await User.findOneAndRemove(query);
      }
      else{
        return null
      }
    } catch (error) {
      throw error;
    }
  }

  async updateUserEmailById(id, userEmail) {
    try {
      const userId = new ObjectId(id);
      const newValues = {$set: {email:userEmail}};
      await User.findByIdAndUpdate(userId, newValues);
      return User.findById(userId);
    } catch (error) {
      throw error;
    }
  }

  //TODO buscar el titulo del libro
  async getAllComments(userId) {
    try {
      return await Comment.find({_user:new ObjectId(userId)});
    } catch (error) {
      throw error;
    }
  }

}

module.exports = UserService;
