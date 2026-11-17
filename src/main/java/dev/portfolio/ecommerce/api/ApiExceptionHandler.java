package dev.portfolio.ecommerce.api;
import java.util.*;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
@RestControllerAdvice public class ApiExceptionHandler {
 @ExceptionHandler(NoSuchElementException.class) ResponseEntity<ProblemDetail> missing(NoSuchElementException e){return problem(HttpStatus.NOT_FOUND,e.getMessage());}
 @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<ProblemDetail> invalid(MethodArgumentNotValidException e){return problem(HttpStatus.BAD_REQUEST,"Dados inválidos");}
 @ExceptionHandler({IllegalStateException.class,IllegalArgumentException.class,DataIntegrityViolationException.class,ObjectOptimisticLockingFailureException.class}) ResponseEntity<ProblemDetail> conflict(Exception e){return problem(HttpStatus.CONFLICT,"Operação não permitida ou conflito de dados");}
 private ResponseEntity<ProblemDetail> problem(HttpStatus s,String detail){return ResponseEntity.status(s).body(ProblemDetail.forStatusAndDetail(s,detail));}
}
