// ********RoostGPT********
/*
Test generated by RoostGPT for test javaSpringtestCases using AI Type Open AI and AI Model gpt-4
ROOST_METHOD_HASH=addNewBookToCart_5d0f577e76
ROOST_METHOD_SIG_HASH=addNewBookToCart_7e9ecc9d93
"""
  Scenario 1: Test Adding a New Book to the Cart Successfully
  Details:
    TestName: testAddNewBookToCartSuccessfully.
    Description: This test is meant to check the successful addition of a new book to the cart. The target scenario is when a valid NewBookResource object is passed to the addNewBookToCart method.
  Execution:
    Arrange: Mock the NewBookResource and BookRepository objects. Set up the findByIsbn method of the BookRepository to return a valid Book object when called with the ISBN of the book.
    Act: Invoke the addNewBookToCart method with the mocked NewBookResource.
    Assert: Use JUnit assertions to check if the returned CartResource object contains the added book.
  Validation:
    The assertion aims to verify that the book has been successfully added to the cart. The expected result is the CartResource object containing the added book. This test is significant as it validates the core functionality of adding a new book to the cart.
  Scenario 2: Test Adding a New Book to the Cart with Nonexistent ISBN
  Details:
    TestName: testAddNewBookToCartWithNonexistentIsbn.
    Description: This test is meant to check the error handling when a new book with a nonexistent ISBN is added to the cart. The target scenario is when a NewBookResource object with a nonexistent ISBN is passed to the addNewBookToCart method.
  Execution:
    Arrange: Mock the NewBookResource and BookRepository objects. Set up the findByIsbn method of the BookRepository to return null when called with the ISBN of the book.
    Act: Invoke the addNewBookToCart method with the mocked NewBookResource.
    Assert: Use JUnit assertions to expect an exception.
  Validation:
    The assertion aims to verify that an exception is thrown when a book with a nonexistent ISBN is added to the cart. The expected result is an exception. This test is significant as it checks the error handling of the addNewBookToCart method.
  Scenario 3: Test Adding a New Book to the Cart with Null NewBookResource
  Details:
    TestName: testAddNewBookToCartWithNullNewBookResource.
    Description: This test is meant to check the error handling when a null NewBookResource object is passed to the addNewBookToCart method. The target scenario is when a null NewBookResource is passed to the addNewBookToCart method.
  Execution:
    Arrange: No need to mock any objects in this case.
    Act: Invoke the addNewBookToCart method with null.
    Assert: Use JUnit assertions to expect an exception.
  Validation:
    The assertion aims to verify that an exception is thrown when a null NewBookResource is passed to the addNewBookToCart method. The expected result is an exception. This test is significant as it checks the error handling of the addNewBookToCart method when it receives null input.
"""
*/
// ********RoostGPT********
package com.baeldung.web.controller;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import com.baeldung.model.Book;
import com.baeldung.persistence.BookRepository;
import javax.persistence.EntityNotFoundException;
import com.baeldung.web.resource.NewBookResource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.baeldung.model.Cart;
import com.baeldung.web.error.Checks;
import com.baeldung.web.resource.BookResource;
import com.baeldung.web.resource.CartResource;
import org.junit.experimental.categories.Category;

@Category({ Categories.addNewBookToCart.class, Categories.findByIsbn.class, Categories.toResource.class })
@SpringBootTest
public class CartControllerAddNewBookToCartTest {

	@InjectMocks
	private CartController cartController;

	@Mock
	private BookRepository bookRepo;

	@Mock
	private NewBookResource newBookResource;

	@Mock
	private Book book;

	@Test
    @Category(Categories.valid.class)
    public void testAddNewBookToCartSuccessfully() {
        // Arrange
        when(newBookResource.getBook()).thenReturn(book);
        when(book.getIsbn()).thenReturn("123456789");
        when(bookRepo.findByIsbn("123456789")).thenReturn(book);
        // Act
        CartResource result = cartController.addNewBookToCart(newBookResource);
        // Assert
        assertEquals("123456789", result.getBooks().get(0).getIsbn());
    }

	@Test
    @Category(Categories.invalid.class)
    public void testAddNewBookToCartWithNonexistentIsbn() {
        // Arrange
        when(newBookResource.getBook()).thenReturn(book);
        when(book.getIsbn()).thenReturn("123456789");
        when(bookRepo.findByIsbn("123456789")).thenReturn(null);
        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> cartController.addNewBookToCart(newBookResource));
    }

	@Test
	@Category(Categories.invalid.class)
	public void testAddNewBookToCartWithNullNewBookResource() {
		// Act and Assert
		assertThrows(NullPointerException.class, () -> cartController.addNewBookToCart(null));
	}

}