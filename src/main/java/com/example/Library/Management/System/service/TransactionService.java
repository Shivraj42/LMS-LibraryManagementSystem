package com.example.Library.Management.System.service;


import com.example.Library.Management.System.DTOs.responseDTOs.IssueBookResponse;
import com.example.Library.Management.System.DTOs.responseDTOs.ReturnBookResponse;
import com.example.Library.Management.System.Enums.CardStatus;
import com.example.Library.Management.System.Enums.TransactionStatus;
import com.example.Library.Management.System.Enums.TransactionType;
import com.example.Library.Management.System.exception.BookNotFoundException;
import com.example.Library.Management.System.exception.CardNotActiveException;
import com.example.Library.Management.System.exception.StudentNotFoundException;
import com.example.Library.Management.System.exception.TransactionNotFoundException;
import com.example.Library.Management.System.model.Book;
import com.example.Library.Management.System.model.LibraryCard;
import com.example.Library.Management.System.model.Student;
import com.example.Library.Management.System.model.Transaction;
import com.example.Library.Management.System.repository.BookRepository;
import com.example.Library.Management.System.repository.StudentRepository;
import com.example.Library.Management.System.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Service
public class TransactionService {

    final TransactionRepository transactionRepository;

    final BookRepository bookRepository;

    final StudentRepository studentRepository;

    final  JavaMailSender javaMailSender;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository,
                              BookRepository bookRepository,
                              StudentRepository studentRepository,
                              JavaMailSender javaMailSender) {

        this.transactionRepository = transactionRepository;
        this.bookRepository = bookRepository;
        this.studentRepository = studentRepository;
        this.javaMailSender = javaMailSender;
    }

    public static int bookLimit=6;
    public IssueBookResponse issueBook(int bookId, int studentId) {

        // check if Student present
        Optional<Student> studentOptional= studentRepository.findById(studentId);
        if(studentOptional.isEmpty()){
            throw new StudentNotFoundException("Invalid Student Id!");
        }
        Student student= studentOptional.get();

        // check if library Card is valid
        LibraryCard libraryCard= student.getLibraryCard();
        if(libraryCard.getCardStatus()!= CardStatus.ACTIVE){
            throw new CardNotActiveException("Card is not Active");
        }

        // check for book issue limit

        if(libraryCard.getIssuedBooks().size() > bookLimit){
            throw new CardNotActiveException("Card has reached to max limit!");
        }

        // check if book present
        Optional<Book> bookOptional= bookRepository.findById(bookId);
        if(bookOptional.isEmpty()){
            throw new BookNotFoundException("Invalid Book Id!");
        }

        Book book= bookOptional.get();

        // check if book available for issue
        if(book.isIssued()){
            throw new BookNotFoundException("Book already issued!");
        }

        // issue book

        // crate transaction
        Transaction transaction= Transaction.builder()
                .book(book)
                .transactionStatus(TransactionStatus.SUCCESS)
                .fine(0.00)
                .transactionType(TransactionType.ISSUE)
                .transactionId(UUID.randomUUID().toString())
                .libraryCard(student.getLibraryCard())
                .build();

        Transaction savedTransaction= transactionRepository.save(transaction);

        // update book
        book.setIssued(true);
        book.getTransactions().add(savedTransaction);

        // update Library card
        student.getLibraryCard().getIssuedBooks().add(bookId);
        student.getLibraryCard().getTransactions().add(savedTransaction);

        // save book and student
        Book savedBook= bookRepository.save(book);
        Student savedStudent= studentRepository.save(student);


        // Send mail to the student

        String studentName = student.getName();
        String bookTitle = book.getTitle();
        String authorName = book.getAuthor().getName();
        String pickupLocation = "The Library Cove";
        String TransactionTime= savedTransaction.getTransactionTime().toString();
        String DueDate=LocalDate.now().plusDays(15).toString();

        String emailBody = "Ahoy"+studentName+"!,\n\n" +
                "This be Captain Jack Sparrow of the Library, and I bring ye good tidings on this fine day. We have successfully processed your request for a book, and I'm delighted to provide ye with the transaction details.\nTransaction Id:"+ savedTransaction.getTransactionId()+" \n\n"+
                "**Book Details:**\n" +
                "- Title: \"" + bookTitle + "\"\n" +
                "- Author: " + authorName + "\n" +
                "- Pickup Location: " + pickupLocation + "\n" +
                "- Transaction Time " + TransactionTime + "\n" +
                "- Due Date  " + DueDate + "\n\n" +
                "Ye can retrieve your coveted literary treasure from " + pickupLocation + ". Be swift but steady, for the deadline be looming, and ye wouldn't want to cross paths with the late fee Kraken. Should ye require an extension or any other library-related assistance, don't hesitate to give us a shout.\n\n" +
                "With this transaction, remember, knowledge be a treasure worth its weight in gold. May the winds be at your back as you delve into the adventures held within the pages of this fine book.\n\n" +
                "Arrr, fair winds and happy reading!\n\n" +
                "Sincerely,\n" +
                "Captain Jack Sparrow\n" +
                "The Library\n" +
                "Contact: jacksparrow.library@gmail.com";



        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("jacksparrow.library@gmail.com");
        simpleMailMessage.setTo(student.getEmail());
        simpleMailMessage.setSubject("Congrats!! Book Issued");
        simpleMailMessage.setText(emailBody);
        javaMailSender.send(simpleMailMessage);


        // Crate the response

        IssueBookResponse response= IssueBookResponse.builder()
                .bookName(savedBook.getTitle())
                .authorName(savedBook.getAuthor().getName())
                .libraryCardNumber(savedStudent.getLibraryCard().getCardId())
                .studentName(savedStudent.getName())
                .transactionNumber(savedTransaction.getTransactionId())
                .transactionStatus(savedTransaction.getTransactionStatus())
                .transactionTime(savedTransaction.getTransactionTime())
                .build();


        return response;
    }


    public ReturnBookResponse returnBook(int bookId, int studentId) {
        // check if Student present
        Optional<Student> studentOptional= studentRepository.findById(studentId);
        if(studentOptional.isEmpty()){
            throw new StudentNotFoundException("Invalid Student Id!");
        }
        Student student= studentOptional.get();
        LibraryCard libraryCard= student.getLibraryCard();
        // check if book present
        Optional<Book> bookOptional= bookRepository.findById(bookId);
        if(bookOptional.isEmpty()){
            throw new BookNotFoundException("Invalid Book Id!");
        }
        Book book= bookOptional.get();

        // find particular transaction
        List<Transaction> transactions= transactionRepository.findByBookAndStudent(book, libraryCard, TransactionType.ISSUE);

        if(transactions.size()==0){
            throw new TransactionNotFoundException("That transaction not happens or book already returned");
        }

        // find the last transaction

        Transaction latestTransaction= transactions.get(transactions.size()-1);

        // calculate the fine

        // find the no of days that the student kept the book

        Date issueDate= latestTransaction.getTransactionTime();

        long milliSeconds= Math.abs(System.currentTimeMillis()-issueDate.getTime());  // calculate millisecond diff

        long daysLate = TimeUnit.DAYS.convert(milliSeconds,TimeUnit.MILLISECONDS)-15;

        double fineAmount=0.00;

        if(daysLate>0){
            fineAmount =(daysLate)*5.00;
        }

        // update transaction
        Transaction transaction= Transaction.builder()
                .book(book)
                .transactionStatus(TransactionStatus.SUCCESS)
                .fine(fineAmount)
                .transactionType(TransactionType.RETURN)
                .transactionId(UUID.randomUUID().toString())
                .libraryCard(student.getLibraryCard())
                .build();
        Transaction savedTransaction= transactionRepository.save(transaction);

        // update book
        book.setIssued(false);

        // update libraryCard // remove the book from account
        List<Integer> books= libraryCard.getIssuedBooks();
        for(int i=0; i<books.size(); i++){
            if(books.get(i)==bookId) {
                books.remove(i);
                break;
            }
        }
        libraryCard.setIssuedBooks(books);

        // save book and student
        Book savedBook= bookRepository.save(book);
        Student savedStudent= studentRepository.save(student);

        String studentName = student.getName();
        String bookTitle = book.getTitle();
        String authorName = book.getAuthor().getName();
        String pickupLocation = "The Library Cove";
        String TransactionTime= savedTransaction.getTransactionTime().toString();
        String returnDate=LocalDate.now().toString();
        String emailBody = "Ahoy there,\n\n" +
                "This be Captain Jack Sparrow of the Library, and I bring ye good tidings. We have received word that ye have returned a book, and I'm pleased to provide ye with the transaction details.\nTransaction Id: " + savedTransaction.getTransactionId() + "\n\n" +
                "**Book Details:**\n" +
                "- Title: \"" + bookTitle + "\"\n" +
                "- Author: " + authorName + "\n" +
                "- Pickup Location: " + pickupLocation + "\n" +
                "- Return Date: " + returnDate + "\n" +
                "- Days Late: " + daysLate + "\n" +
                "- Fine Amount: Rs. " + fineAmount + "\n\n" +
                "Ye have fulfilled yer duty in returning the book to " + pickupLocation + ". ";

        if (fineAmount > 0) {
            emailBody += "However, ye be a bit tardy, and there be a fine for " + daysLate + " extra days. Please be sure to settle this fine at yer earliest convenience.\n\n";
        } else {
            emailBody += "Ye returned the book on time, and we be grateful for yer timely return.\n\n";
        }

        emailBody += "If ye have any questions or need further assistance, feel free to reach out. With this transaction, the circle of knowledge continues to spin. May the winds be at your back as you embark on new literary adventures.\n\n" +
                "Arrr, fair winds and happy reading!\n\n" +
                "Sincerely,\n" +
                "Captain Jack Sparrow\n" +
                "The Library\n" +
                "Contact: jacksparrow.library@gmail.com";



        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("jacksparrow.library@gmail.com");
        simpleMailMessage.setTo(student.getEmail());
        simpleMailMessage.setSubject("Book Returned - Thank You!");
        simpleMailMessage.setText(emailBody);
        javaMailSender.send(simpleMailMessage);



        ReturnBookResponse response= ReturnBookResponse.builder()
                .bookName(savedBook.getTitle())
                .authorName(savedBook.getAuthor().getName())
                .libraryCardNumber(savedStudent.getLibraryCard().getCardId())
                .studentName(savedStudent.getName())
                .transactionNumber(savedTransaction.getTransactionId())
                .transactionStatus(savedTransaction.getTransactionStatus())
                .transactionTime(savedTransaction.getTransactionTime())
                .fine(fineAmount)
                .build();

        return response;

    }
}
