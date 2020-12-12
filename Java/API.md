# API REST - PRÁCTICA 1

Basic documentation of the operations supported by the REST API of **Práctica 1 - Tecnologías de Servicios de Internet**

## PRECONDITION BEFORE STATRTING TO TEST

_Before we start, we have created a default data loading method that will do a first load of books/comments to make
testing of the GET calls more comfortable_

## DEFAULT DATA

_Precharge default data to the app_

* #### POST METHOD

* #### URL:  http://localhost:8080/rest/books/default-data

* #### RESPONSE
    ```
        OK
    ```

## GET ALL BOOKS INFO

_Obtain a list with the identifier and title of each of the books_

* #### GET METHOD

* #### URL:  http://localhost:8080/rest/books

* #### RESPONSE
  ```
  [
      {
          "id": 0,
          "title": "El señor de los Anillos"
      },
      {
          "id": 1,
          "title": "Harry Potter"
      }
  ]
  ```

## GET BOOK INFO

_Get all the information of a given book (comments included)_

* #### GET METHOD

* #### URL:  http://localhost:8080/rest/books/0

* #### RESPONSE
  ```
  {
      "id": 0,
      "title": "El señor de los Anillos",
      "resume": "Un señor con un anillo para gobernarlos a todos",
      "author": "Frodo",
      "editorial": "Edithorial",
      "publicationYear": 2019,
      "comments": [
          {
              "id": 0,
              "name": "Pepe",
              "comment": "Me gusta mucho la peli",
              "rating": 5
          }
      ]
  }
  ```

## CREATE BOOK

_Create a book_

* #### POST METHOD

* #### URL:  http://localhost:8080/rest/books

* #### BODY
  ```
  {
      "title": "El señor de los Anillos 2, las dos torres",
      "resume": "Un señor con dos anillos para gobernarlos a todos",
      "author": "Sam",
      "editorial": "EdiTHORial",
      "publicationYear": 2020
  }
  ```

* #### RESPONSE

  ```
  {
      "id": 2,
      "title": "El señor de los Anillos 2, las dos torres",
      "resume": "Un señor con dos anillos para gobernarlos a todos",
      "author": "Sam",
      "editorial": "EdiTHORial",
      "publicationYear": 2020,
      "comments": []
  }
  ```

## CREATE COMMENT

_Create a comment for a specific book_

* #### POST METHOD

* #### URL:  http://localhost:8080/rest/books/0/comments

* #### BODY

  ```
  {
      "name": "Jose Luis",
      "comment": "Me gusta bastente poco la peli",
      "rating": 3
  }
  ```

* #### RESPONSE

  ```
  {
      "id": 3,
      "name": "Jose Luis",
      "comment": "Me gusta bastente poco la peli",
      "rating": 3
  }
  ```

## DELETE BOOK

_Delete a book_

* #### DELETE METHOD

* #### URL:  http://localhost:8080/rest/books/0/delete

* #### RESPONSE

  ```
  {
      "id": 0,
      "title": "El señor de los Anillos",
      "resume": "Un señor con un anillo para gobernarlos a todos",
      "author": "Frodo",
      "editorial": "Edithorial",
      "publicationYear": 2019,
      "comments": [
          {
              "id": 0,
              "name": "Pepe",
              "comment": "Me gusta mucho la peli",
              "rating": 5
          }
      ]
  }
  ```

## DELETE COMMENT

_Delete a comment from a book_

* #### DELETE METHOD

* #### URL:  http://localhost:8080/rest/books/0/comments/0/delete

* #### RESPONSE

  ```
  {
      "id": 0,
      "title": "El señor de los Anillos",
      "resume": "Un señor con un anillo para gobernarlos a todos",
      "author": "Frodo",
      "editorial": "Edithorial",
      "publicationYear": 2019,
      "comments": [
          {
              "id": 2,
              "name": "Jose Luis",
              "comment": "Me gusta bastente poco la peli",
              "rating": 3
          }
      ]
  }
  ```
