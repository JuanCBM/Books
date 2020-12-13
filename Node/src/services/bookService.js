let contains=function(main,sub){
  return main.toLowerCase().indexOf(sub.toLowerCase())>=0;
}
/**
 *
 * Tenemos que meter aqui toda la logica como tienes tu en el de Java
 *
 *
 * **/
class BookService{

  constructor(bookRepository){
    this.bookRepository=bookRepository;
  }

  async add(book){
    if(book && book.title){
      if(!book.id)
        book.id=book.title.toLowerCase().replace(' ','-');
      await this.bookRepository.addBook(book);
    }
  }

  async getAll(){
    return await this.bookRepository.getAllBooks();
  }

  async getById(bookId){
    return await this.bookRepository.getBookById(bookId);
  }

  async search(term){
    return await this.getAll().filter(a=> contains(a.title,term) || contains(a.cover,term));
  }

  async removeBook(id){
    await this.bookRepository.deleteBookById(id);

  }
  async update(id,book){
    let checkbook=await this.bookRepository.getBookById(id);
    if(checkbook!=null){
      if(book && book.title){
        if(!book.id)
          book.id=book.title.toLowerCase().replace(' ','-');
        await this.bookRepository.updateBookById(book);
      }
    }

    else{
      console.log('book add failed');
    }
  }

}
module.exports=BookService;
