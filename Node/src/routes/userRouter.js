const express = require('express')
const router = express.Router()
const User = require('../schemas/user')
const mongoose = require('mongoose');
const UserService = require("../services/userService");
const userService = new UserService();

let template = Object.freeze({
  FULL: 'FULL',
  WITHOUT_ID: 'WITHOUT_ID',
});

module.exports = router

router.get('/', async (req,res)=> {
  try{
    const users = await userService.getAllUsers();
    res.json(toResponse(users,template.FULL));
  }catch (error) {
    res.status(500).json({message: error.message})
  }
})

router.get('/:id', async (req,res)=> {
  const userId = req.params.id;

  if(!mongoose.Types.ObjectId.isValid(userId)){
    return res.sendStatus(400);
  }
  const user = await userService.getUserById(userId);
  if (!user) {
    res.sendStatus(404);
  } else {
    res.json(toResponse(user,template.FULL));
  }
})

router.post('/', async (req,res)=> {
  const user = new User({
    nick: req.body.nick,
    email: req.body.email
  })
  let checkUser = await User.findOne({'nick':user.nick});
  if(!checkUser) {
    try {
      checkUser = await User.findOne({'email': user.email});
      if (!checkUser) {
        const newUser = await userService.addUser(user);
        res.status(201).json(toResponse(newUser, template.FULL));
      } else
        return res.status(400).send({message: 'Email is already taken.'})
    } catch (e) {
      res.status(400).json({message: e.message})
    }
  }else{
    return res.status(400).send({message: 'Username is already taken.'})
  }
})

// Delete user if NO have any comments
router.delete('/:id', async (req,res)=> {
  const userId = req.params.id;

  if(!mongoose.Types.ObjectId.isValid(userId)){
    return res.sendStatus(400);
  }

  const user = await userService.getUserById(userId);
  if (!user) {
    res.sendStatus(404);
  } else {
    const deleteUser = await userService.deleteUserById(userId);
    if (!deleteUser) {
      return res.status(403).send({message: 'User has comments'})
    }else{
      res.json(toResponse(deleteUser,template.FULL));
    }
  }

})

router.put('/:id/email', async (req,res)=> {
  const userEmail = req.body.email;
  const userId = req.params.id;
  const checkUser = await User.findOne({'email':userEmail});
  if(!checkUser){
    const userUpdated = await userService.updateUserEmailById(userId,userEmail);
    if (!userUpdated) {
      res.sendStatus(404);
    } else {
      res.json(toResponse(userUpdated,template.FULL));
    }
  } else
    return res.status(400).send({message: 'Email is already taken.'})
  })


router.get('/:id/comments', async (req,res)=> {
  try{
    const userId = req.params.id;
    const comments = await userService.getAllComments(userId);
    res.json(toResponse(comments,template.FULL));
  }catch (error) {
    res.status(500).json({message: error.message})
  }
})


// Formateo de la respuesta de la peticion
function toResponse(document,type) {
  if (document instanceof Array) {
    return document.map(elem => toResponse(elem,type));
  } else {
    let response = document.toObject({ versionKey: false });
    switch (type){
      case template.FULL:{
        return response;
      }
      case template.WITHOUT_ID:{
        delete response._id;
        return response;
      }
    }

  }
}
