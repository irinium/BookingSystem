package com.bookingsystem.model.generated;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * BookingRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-03-01T04:21:28.827142+02:00[Europe/Kiev]", comments = "Generator version: 7.6.0")
public class BookingRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  private String userId;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate bookingStartDate;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate bookingEndDate;

  public BookingRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public BookingRequest(String userId) {
    this.userId = userId;
  }

  public BookingRequest userId(String userId) {
    this.userId = userId;
    return this;
  }

  /**
   * Get userId
   * @return userId
  */
  @NotNull 
  @Schema(name = "userId", example = "1001", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("userId")
  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public BookingRequest bookingStartDate(LocalDate bookingStartDate) {
    this.bookingStartDate = bookingStartDate;
    return this;
  }

  /**
   * Get bookingStartDate
   * @return bookingStartDate
  */
  @Valid 
  @Schema(name = "bookingStartDate", example = "Sat Mar 01 02:00:00 EET 2025", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("bookingStartDate")
  public LocalDate getBookingStartDate() {
    return bookingStartDate;
  }

  public void setBookingStartDate(LocalDate bookingStartDate) {
    this.bookingStartDate = bookingStartDate;
  }

  public BookingRequest bookingEndDate(LocalDate bookingEndDate) {
    this.bookingEndDate = bookingEndDate;
    return this;
  }

  /**
   * Get bookingEndDate
   * @return bookingEndDate
  */
  @Valid 
  @Schema(name = "bookingEndDate", example = "Sat Mar 15 02:00:00 EET 2025", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("bookingEndDate")
  public LocalDate getBookingEndDate() {
    return bookingEndDate;
  }

  public void setBookingEndDate(LocalDate bookingEndDate) {
    this.bookingEndDate = bookingEndDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BookingRequest bookingRequest = (BookingRequest) o;
    return Objects.equals(this.userId, bookingRequest.userId) &&
        Objects.equals(this.bookingStartDate, bookingRequest.bookingStartDate) &&
        Objects.equals(this.bookingEndDate, bookingRequest.bookingEndDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, bookingStartDate, bookingEndDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BookingRequest {\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    bookingStartDate: ").append(toIndentedString(bookingStartDate)).append("\n");
    sb.append("    bookingEndDate: ").append(toIndentedString(bookingEndDate)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

