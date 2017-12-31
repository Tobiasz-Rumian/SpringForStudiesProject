package com.klima.projekt1.exception;
import com.klima.projekt1.configuration.ErrorCode;
import com.klima.projekt1.configuration.ErrorCode;
import lombok.Getter;

@Getter
public class DomainException extends RuntimeException {
   private ErrorCode errorCode;


   private DomainException(ErrorCode errorCode, Throwable cause) {
      super(cause);
      this.errorCode = errorCode;
   }

   private DomainException(ErrorCode errorCode) {
      this.errorCode = errorCode;
   }

   public static DomainException of(ErrorCode errorCode) {
      return new DomainException(errorCode);
   }

   public static DomainException of(ErrorCode errorCode, Throwable cause) {
      return new DomainException(errorCode, cause);
   }

   @Override
   public String getMessage() {
      return errorCode.name();
   }
}
