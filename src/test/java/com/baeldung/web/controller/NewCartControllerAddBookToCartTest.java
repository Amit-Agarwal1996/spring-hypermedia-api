// ********RoostGPT********
/*
Test generated by RoostGPT for test javaSpringtestCases using AI Type Open AI and AI Model gpt-4
ROOST_METHOD_HASH=addBookToCart_e320cd71e0
ROOST_METHOD_SIG_HASH=addBookToCart_802ed3fbd5
"""
Scenario 1: Test to verify if a book is added to the cart successfully when correct book details are provided
Details:
  TestName: testAddBookToCartWithValidBookDetails
  Description: This test is designed to verify that the method addBookToCart() adds a book to the cart when valid book details are provided. The book's ISBN is used to fetch the book from the repository, and it is then added to the cart.
  Execution:
    Arrange: Mock the BookResource, BookRepository, and the book object with valid details. Also, mock the findByIsbn() method of the BookRepository to return the book object when called.
    Act: Call the addBookToCart() method with the mock BookResource object.
    Assert: Assert that the returned NewCartResource object contains the book that was added.
  Validation:
    This assertion verifies that the book has been successfully added to the cart. The expected result is the addition of the book to the cart, which is confirmed by the presence of the book in the returned NewCartResource object.
Scenario 2: Test to verify if the method throws an error when a non-existent book is attempted to be added
Details:
  TestName: testAddBookToCartWithNonExistentBookDetails
  Description: This test is designed to verify that the method addBookToCart() throws an error when a non-existent book is attempted to be added to the cart.
  Execution:
    Arrange: Mock the BookResource, BookRepository, and book object with non-existent book details. Also, mock the findByIsbn() method of the BookRepository to return null when called.
    Act: Call the addBookToCart() method with the mock BookResource object.
    Assert: Assert that an exception is thrown.
  Validation:
    This assertion verifies that the method throws an error when a non-existent book is attempted to be added to the cart. The expected result is an error, confirming the method's functionality to check for the existence of a book before adding it to the cart.
Scenario 3: Test to verify if the method initializes a new cart when no cart exists
Details:
  TestName: testAddBookToCartWithNoExistingCart
  Description: This test is designed to verify that the method addBookToCart() initializes a new cart when no cart exists.
  Execution:
    Arrange: Mock the BookResource, BookRepository, and book object with valid details. Also, mock the findByIsbn() method of the BookRepository to return the book object when called. Ensure that no cart exists initially.
    Act: Call the addBookToCart() method with the mock BookResource object.
    Assert: Assert that a new cart is created and the book is added to this new cart.
  Validation:
    This assertion verifies that the method initializes a new cart when no cart exists and then adds the book to this new cart. The expected result is the creation of a new cart with the book in it.
"""
*/
// ********RoostGPT********
package com.baeldung.web.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import com.baeldung.model.Book;
import com.baeldung.persistence.BookRepository;
import com.baeldung.web.error.Checks;
import com.baeldung.web.resource.BookResource;
import com.baeldung.web.resource.NewCartResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.baeldung.web.resource.CartResource;
import jersey.repackaged.com.google.common.collect.Lists;
import org.junit.experimental.categories.Category;

@Category({ Categories.addBookToCart.class, Categories.findByIsbn.class, Categories.initializeBooksInCart.class })
@RunWith(MockitoJUnitRunner.class)
public class NewCartControllerAddBookToCartTest {

	@Mock
	private BookRepository bookRepo;

	private NewCartController newCartController;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		newCartController = new NewCartController();
		newCartController.bookRepo = bookRepo;
	}

	@Test
	@Category(Categories.valid.class)
	public void testAddBookToCartWithValidBookDetails() {
		Book book = new Book();
		book.setIsbn("12345");
		BookResource bookResource = new BookResource(book);
		when(bookRepo.findByIsbn(book.getIsbn())).thenReturn(book);
		NewCartResource newCartResource = newCartController.addBookToCart(bookResource);
		assertTrue(newCartResource.getBooks().contains(book));
	}

	@Test(expected = EntityNotFoundException.class)
	@Category(Categories.invalid.class)
	public void testAddBookToCartWithNonExistentBookDetails() {
		Book book = new Book();
		book.setIsbn("12345");
		BookResource bookResource = new BookResource(book);
		when(bookRepo.findByIsbn(book.getIsbn())).thenReturn(null);
		newCartController.addBookToCart(bookResource);
	}

	@Test
	@Category(Categories.valid.class)
	public void testAddBookToCartWithNoExistingCart() {
		Book book = new Book();
		book.setIsbn("12345");
		BookResource bookResource = new BookResource(book);
		when(bookRepo.findByIsbn(book.getIsbn())).thenReturn(book);
		newCartController.books = null;
		NewCartResource newCartResource = newCartController.addBookToCart(bookResource);
		assertTrue(newCartResource.getBooks().contains(book));
	}

}