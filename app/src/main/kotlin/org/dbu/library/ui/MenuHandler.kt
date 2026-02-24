package org.dbu.library.ui

import org.dbu.library.model.Book
import org.dbu.library.model.Patron
import org.dbu.library.repository.LibraryRepository
import org.dbu.library.service.BorrowResult
import org.dbu.library.service.LibraryService

fun handleMenuAction(
    choice: String,
    service: LibraryService,
    repository: LibraryRepository
): Boolean {

    return when (choice) {

        "1" -> {
            addBook(service)
            true
        }

        "2" -> {
            registerPatron(repository)
            true
        }

        "3" -> {
            borrowBook(service)
            true
        }

        "4" -> {
            returnBook(service)
            true
        }

        "5" -> {
            search(service)
            true
        }

        "6" -> {
            listAllBooks(repository)
            true
        }

        "0" -> false

        else -> {
            println("Invalid option")
            true
        }
    }
}
fun addBook(service: LibraryService) {
    print("Enter ISBN: ")
    val isbn = readln()
    print("Enter Title: ")
    val title = readln()
    print("Enter Author: ")
    val author = readln()
    print("Enter Year: ")
    val year = readln().toIntOrNull() ?: 0

    if (service.addBook(Book(isbn, title, author, year))) {
        println("Book added successfully!")
    } else {
        println("Failed to add book (ISBN may already exist).")
    }
}

fun registerPatron(repository: LibraryRepository) {
    print("Enter Patron ID: ")
    val id = readln()
    print("Enter Name: ")
    val name = readln()

    if (repository.addPatron(Patron(id, name))) {
        println("Patron registered successfully!")
    } else {
        println("Failed to register patron (ID may already exist).")
    }
}

fun borrowBook(service: LibraryService) {
    print("Enter Patron ID: ")
    val patronId = readln()
    print("Enter Book ISBN: ")
    val isbn = readln()

    val result = service.borrowBook(patronId, isbn)
    println("Result: $result")
}

fun returnBook(service: LibraryService) {
    print("Enter Patron ID: ")
    val patronId = readln()
    print("Enter Book ISBN: ")
    val isbn = readln()

    if (service.returnBook(patronId, isbn)) {
        println("Book returned successfully!")
    } else {
        println("Return failed. Please check IDs.")
    }
}

fun search(service: LibraryService) {
    print("Enter title or author search term: ")
    val query = readln()
    val results = service.search(query)

    if (results.isEmpty()) {
        println("No books found.")
    } else {
        results.forEach { println("${it.title} by ${it.author} (${it.isbn}) - ${if (it.isAvailable) "Available" else "Borrowed"}") }
    }
}

fun listAllBooks(repository: LibraryRepository) {
    val books = repository.getAllBooks()
    if (books.isEmpty()) {
        println("Library is empty.")
    } else {
        books.forEach { println("${it.isbn}: ${it.title} | Available: ${it.isAvailable}") }
    }
}

