package com.bookingsystem.model.generated;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.math.BigDecimal;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Unit
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-02-28T03:32:03.521616+02:00[Europe/Kiev]", comments = "Generator version: 7.6.0")
public class Unit implements Serializable {

  private static final long serialVersionUID = 1L;

  private Integer id;

  private Integer numberOfRooms;

  /**
   * Gets or Sets accommodationType
   */
  public enum AccommodationTypeEnum {
    HOME("HOME"),
    
    FLAT("FLAT"),
    
    APARTMENTS("APARTMENTS");

    private String value;

    AccommodationTypeEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static AccommodationTypeEnum fromValue(String value) {
      for (AccommodationTypeEnum b : AccommodationTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private AccommodationTypeEnum accommodationType;

  private Integer floor;

  private BigDecimal cost;

  private String description;

  private Boolean available;

  public Unit id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  
  @Schema(name = "id", example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Unit numberOfRooms(Integer numberOfRooms) {
    this.numberOfRooms = numberOfRooms;
    return this;
  }

  /**
   * Get numberOfRooms
   * @return numberOfRooms
  */
  
  @Schema(name = "numberOfRooms", example = "3", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("numberOfRooms")
  public Integer getNumberOfRooms() {
    return numberOfRooms;
  }

  public void setNumberOfRooms(Integer numberOfRooms) {
    this.numberOfRooms = numberOfRooms;
  }

  public Unit accommodationType(AccommodationTypeEnum accommodationType) {
    this.accommodationType = accommodationType;
    return this;
  }

  /**
   * Get accommodationType
   * @return accommodationType
  */
  
  @Schema(name = "accommodationType", example = "FLAT", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("accommodationType")
  public AccommodationTypeEnum getAccommodationType() {
    return accommodationType;
  }

  public void setAccommodationType(AccommodationTypeEnum accommodationType) {
    this.accommodationType = accommodationType;
  }

  public Unit floor(Integer floor) {
    this.floor = floor;
    return this;
  }

  /**
   * Get floor
   * @return floor
  */
  
  @Schema(name = "floor", example = "2", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("floor")
  public Integer getFloor() {
    return floor;
  }

  public void setFloor(Integer floor) {
    this.floor = floor;
  }

  public Unit cost(BigDecimal cost) {
    this.cost = cost;
    return this;
  }

  /**
   * Get cost
   * @return cost
  */
  @Valid 
  @Schema(name = "cost", example = "120.0", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("cost")
  public BigDecimal getCost() {
    return cost;
  }

  public void setCost(BigDecimal cost) {
    this.cost = cost;
  }

  public Unit description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  */
  
  @Schema(name = "description", example = "Spacious apartment with sea view", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Unit available(Boolean available) {
    this.available = available;
    return this;
  }

  /**
   * Get available
   * @return available
  */
  
  @Schema(name = "available", example = "true", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("available")
  public Boolean getAvailable() {
    return available;
  }

  public void setAvailable(Boolean available) {
    this.available = available;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Unit unit = (Unit) o;
    return Objects.equals(this.id, unit.id) &&
        Objects.equals(this.numberOfRooms, unit.numberOfRooms) &&
        Objects.equals(this.accommodationType, unit.accommodationType) &&
        Objects.equals(this.floor, unit.floor) &&
        Objects.equals(this.cost, unit.cost) &&
        Objects.equals(this.description, unit.description) &&
        Objects.equals(this.available, unit.available);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, numberOfRooms, accommodationType, floor, cost, description, available);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Unit {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    numberOfRooms: ").append(toIndentedString(numberOfRooms)).append("\n");
    sb.append("    accommodationType: ").append(toIndentedString(accommodationType)).append("\n");
    sb.append("    floor: ").append(toIndentedString(floor)).append("\n");
    sb.append("    cost: ").append(toIndentedString(cost)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    available: ").append(toIndentedString(available)).append("\n");
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

